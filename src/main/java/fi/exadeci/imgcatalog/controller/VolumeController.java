package fi.exadeci.imgcatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.exadeci.imgcatalog.model.QueueEntry;
import fi.exadeci.imgcatalog.model.Volume;
import fi.exadeci.imgcatalog.service.QueueService;
import fi.exadeci.imgcatalog.service.VolumeService;

@CrossOrigin
@Controller
public class VolumeController {

	@Autowired
	private VolumeService service;
	
	@Autowired
	private QueueService qService;
	
//	@RequestMapping(value = "/rest/volume", method = RequestMethod.GET)
	public @ResponseBody Volume createVolume() throws Exception {
		
		return service.createVolume("D:/", "Example");
//		service.createVolume("c:/users/kimmo/Pictures");
		
	}

	

	@RequestMapping(value = "/rest/queue", method = RequestMethod.GET)
	public @ResponseBody List<QueueEntry> getQueue() throws Exception {
		return qService.getEntries();
	}

	@RequestMapping(value = "/rest/queue", method = RequestMethod.POST)
	public @ResponseBody List<QueueEntry> postQueue(@RequestBody QueueEntry entry) throws Exception {
		return qService.saveEntry(entry);
	}

	

}
