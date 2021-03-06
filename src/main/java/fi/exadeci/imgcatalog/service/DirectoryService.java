package fi.exadeci.imgcatalog.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import fi.exadeci.imgcatalog.model.ImageDirectory;
import fi.exadeci.imgcatalog.model.ImageFile;
import fi.exadeci.imgcatalog.model.ImageTag;
import fi.exadeci.imgcatalog.model.Volume;
import fi.exadeci.imgcatalog.repository.ImageDirectoryRepository;
import fi.exadeci.imgcatalog.repository.ImageFileRepository;
import fi.exadeci.imgcatalog.repository.ImageTagRepository;
import fi.exadeci.imgcatalog.repository.VolumeRepository;

@Service
public class DirectoryService {

	@Autowired
	private VolumeRepository volRepo;

	@Autowired
	private ImageFileRepository fileRepo;

	@Autowired
	private ImageDirectoryRepository dirRepo;

	@Autowired
	private ImageTagRepository tagRepo;

	@Autowired
	private MessageDigest digest;

	private Logger logger = LoggerFactory.getLogger(DirectoryService.class);



	public DirectoryService() {

	}



	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)	
	public Volume createVolume(String path, String description) {
		Volume volume = Volume.create(path, description);

		volRepo.save(volume);
		volRepo.flush();

		return volume;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)	
	public List<File> recurseImageFiles(Volume volume, File file) throws IOException, ImageProcessingException {

		List<ImageFile> toBeSaved = new ArrayList<>();
		List<ImageTag> tagsToBeSaved = new ArrayList<>();
		List<File> toBeRecursed = new ArrayList<>();

		ImageDirectory directory = ImageDirectory.create(volume, file.getCanonicalPath());

		for(File f : file.listFiles()) {

			if(f.isDirectory()) {
				toBeRecursed.add(f);
				//				recurseImageFiles(volume,f);
			} else {

				try {
					
					byte [] partialBytes = new byte[1000];
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
					bis.read(partialBytes,0,1000);
					bis.close();

					FileType filetype = FileTypeDetector.detectFileType(new BufferedInputStream(new ByteArrayInputStream(partialBytes)));

					
//					byte [] bytes = Files.readAllBytes(f.toPath());
//					FileType filetype = FileTypeDetector.detectFileType(new BufferedInputStream(new ByteArrayInputStream(bytes)));

					switch(filetype) {
					case Jpeg:
					case Tiff:
					case Psd:
					case Png:
					case Bmp:
					case Gif:
					case Ico:
					case Pcx:
					case WebP:
					case Heif:
					case Eps:
					case Arw:
					case Crw:
					case Cr2:
					case Nef:
					case Orf:
					case Raf:
					case Rw2:
						byte [] bytes = Files.readAllBytes(f.toPath());
						digest.update(bytes);
						String hash = DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
						String mimeType = filetype.getMimeType() == null ? filetype.getName() : filetype.getMimeType();
						ImageFile imagefile = ImageFile.create(volume, directory, f.getName(), mimeType, hash);
						toBeSaved.add(imagefile);
						Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytes));
						for(Directory metadatadir : metadata.getDirectories()) {
							for(Tag tag : metadatadir.getTags()) {
								tagsToBeSaved.add(ImageTag.create(imagefile,tag.toString()));
							}
						}
						break;
					default:
						//ignore
					}
				} catch(Exception ex) {
					logger.error(f.toString() + " " + ex.toString());
				}
			}				
		}

		if(toBeSaved.isEmpty()) {
			// do not save anything;
		} else {
			dirRepo.save(directory);
			fileRepo.saveAll(toBeSaved);
			tagRepo.saveAll(tagsToBeSaved);
			dirRepo.flush();
			fileRepo.flush();
			tagRepo.flush();
		}

		return toBeRecursed;
	}

	

}
