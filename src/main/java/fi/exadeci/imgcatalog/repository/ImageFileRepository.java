package fi.exadeci.imgcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import fi.exadeci.imgcatalog.model.ImageFile;

public interface ImageFileRepository extends CrudRepository<ImageFile, Long> {

}
