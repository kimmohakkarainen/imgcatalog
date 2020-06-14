package fi.exadeci.imgcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.exadeci.imgcatalog.model.QueueEntry;
import fi.exadeci.imgcatalog.repository.QueueEntryRepository;

@Service
public class QueueService {

	@Autowired
	private VolumeService vService;
	
	@Autowired
	private QueueEntryRepository qRepo;
	
	private Logger logger = LoggerFactory.getLogger(VolumeService.class);

	@Transactional(readOnly = true) 
	public List<QueueEntry> getEntries() {
		return qRepo.findAll();
	}
	
	@Transactional(readOnly = false) 
	public List<QueueEntry> saveEntry(QueueEntry entry) {
		
		qRepo.save(entry);
		
		return qRepo.findAll();
	}
	
	@Scheduled(cron = "0 */1 * * * *") 
	public synchronized void handleQueue() throws Exception {
		for(QueueEntry entry : getEntries()) {
			vService.createVolume(entry.getPath(), entry.getDescription());
			qRepo.delete(entry);
		}
	}

	
	
}
