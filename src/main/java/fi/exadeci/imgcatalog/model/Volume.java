package fi.exadeci.imgcatalog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Volume {

	@Id @GeneratedValue
	private long volumeId;
	
	@Column(nullable=false)
	private String path;

	@Column(nullable=false)
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="volume")
	private List<ImageFile> imageFiles = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy="volume")
	private List<ImageDirectory> imageDirectories = new ArrayList<>();

	public static Volume create(String path, String description) {
		Volume volume = new Volume();
		volume.path = path;
		volume.description = description;
		return volume;
	}

	public long getVolumeId() {
		return volumeId;
	}

	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}

	public List<ImageDirectory> getImageDirectories() {
		return imageDirectories;
	}

	public List<ImageFile> getImageFiles() {
		return imageFiles;
	}
	
	
}
