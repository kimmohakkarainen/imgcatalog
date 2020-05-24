package fi.exadeci.imgcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import fi.exadeci.imgcatalog.model.Volume;

public interface VolumeRepository extends CrudRepository<Volume, Long> {
	
	public void flush();

}
