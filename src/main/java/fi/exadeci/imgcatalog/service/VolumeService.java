package fi.exadeci.imgcatalog.service;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.imaging.ImageProcessingException;

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
	private DirectoryService dirService;

	
	@Autowired
	private MessageDigest digest;

	private Logger logger = LoggerFactory.getLogger(VolumeService.class);



	public VolumeService() {

	}



	@Transactional(readOnly = false)
	public Volume createVolume(String directory) throws IOException, ImageProcessingException {

		File file = new File(directory);

		Volume volume = dirService.createVolume(directory);

		if(file.isDirectory()) {
			recurseImageDirectory(volume, file);
		}

		
		
		return volume;
	}


	@Transactional(readOnly = false)
	public void recurseImageDirectory(Volume volume, File file) throws IOException, ImageProcessingException {

		List<File> toBeRecursed = dirService.recurseImageFiles(volume, file);

		for(File f : toBeRecursed) {
			try {
			recurseImageDirectory(volume, f);
			} catch(Exception ex) {
				logger.error(f.toString() + " " + ex.toString());
			}
		}
	}

}
