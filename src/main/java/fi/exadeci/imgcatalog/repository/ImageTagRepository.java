package fi.exadeci.imgcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import fi.exadeci.imgcatalog.model.ImageTag;

public interface ImageTagRepository extends CrudRepository<ImageTag, Long> {
	
	public void flush();

}
