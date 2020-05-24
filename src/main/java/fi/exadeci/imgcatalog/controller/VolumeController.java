package fi.exadeci.imgcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.exadeci.imgcatalog.model.Volume;
import fi.exadeci.imgcatalog.service.VolumeService;

@CrossOrigin
@Controller
public class VolumeController {

	@Autowired
	private VolumeService service;
	
	@RequestMapping(value = "/rest/volume", method = RequestMethod.GET)
	public @ResponseBody Volume createVolume() throws Exception {
		
		return service.createVolume();
		
	}

}
