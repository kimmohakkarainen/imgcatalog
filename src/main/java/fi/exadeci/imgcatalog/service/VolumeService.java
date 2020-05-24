package fi.exadeci.imgcatalog.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
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
public class VolumeService {

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

	private Logger logger = LoggerFactory.getLogger(VolumeService.class);



	public VolumeService() {

	}



	@Transactional(readOnly = false)
	public Volume createVolume(String directory) throws IOException, ImageProcessingException {

		File file = new File(directory);

		Volume volume = Volume.create(directory);

		volRepo.save(volume);
		volRepo.flush();

		if(file.isDirectory()) {
			recurseImageFiles(volume, file);
		}

		return volume;
	}




	public void recurseImageFiles(Volume volume, File file) throws IOException, ImageProcessingException {

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
					byte [] bytes = Files.readAllBytes(f.toPath());
					FileType filetype = FileTypeDetector.detectFileType(new BufferedInputStream(new ByteArrayInputStream(bytes)));

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
					logger.error("Exception ",ex);
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


		for(File f : toBeRecursed) {
			recurseImageFiles(volume, f);
		}
	}

}
