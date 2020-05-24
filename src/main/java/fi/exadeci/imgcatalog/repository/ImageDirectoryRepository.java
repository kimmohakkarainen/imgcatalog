package fi.exadeci.imgcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import fi.exadeci.imgcatalog.model.ImageDirectory;

public interface ImageDirectoryRepository extends CrudRepository<ImageDirectory, Long> {

}
