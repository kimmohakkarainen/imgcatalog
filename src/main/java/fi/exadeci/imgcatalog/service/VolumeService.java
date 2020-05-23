package fi.exadeci.imgcatalog.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.exadeci.imgcatalog.model.ImageFile;
import fi.exadeci.imgcatalog.model.Volume;
import fi.exadeci.imgcatalog.repository.ImageFileRepository;
import fi.exadeci.imgcatalog.repository.VolumeRepository;

@Service
public class VolumeService {

	@Autowired
	private VolumeRepository volRepo;

	@Autowired
	private ImageFileRepository fileRepo;

	private Logger logger = LoggerFactory.getLogger(VolumeService.class);
	
	@Transactional(readOnly = false)
	public Volume createVolume() throws IOException {
		
		File file = new File("c:/temp");
		
		Volume volume = Volume.create("c:/temp");
		
		volRepo.save(volume);

		List<ImageFile> files = recurseImageFiles(volume, new ArrayList<>(), file);
		
		fileRepo.saveAll(files);
		
		return volume;
	}
		
	public List<ImageFile> recurseImageFiles(Volume volume, List<ImageFile> list, File file) throws IOException {

		for(File f : file.listFiles()) {

			if(f.isDirectory()) {
				recurseImageFiles(volume,list, f);
			} else {
				list.add(ImageFile.create(volume, f.getCanonicalPath(), "<hash>"));
			}				
		}
		
		return list;
	}

}
