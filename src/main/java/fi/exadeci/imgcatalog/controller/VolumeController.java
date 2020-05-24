package fi.exadeci.imgcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.exadeci.imgcatalog.service.VolumeService;

@CrossOrigin
@Controller
public class VolumeController {

	@Autowired
	private VolumeService service;
	
	@RequestMapping(value = "/rest/volume", method = RequestMethod.GET)
	public void createVolume() throws Exception {
		
		service.createVolume("D:/");
//		service.createVolume("c:/users/kimmo/Pictures");
		
	}

}
