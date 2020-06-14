package fi.exadeci.imgcatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.exadeci.imgcatalog.model.QueueEntry;

public interface QueueEntryRepository extends CrudRepository<QueueEntry, Long> {

	
	public List<QueueEntry> findAll();
	
	public void flush();

}
