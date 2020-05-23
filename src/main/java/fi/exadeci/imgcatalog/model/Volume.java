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
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="volume")
	private List<ImageFile> imageFiles = new ArrayList<>();
	
	public static Volume create(String description) {
		Volume volume = new Volume();
		volume.description = description;
		return volume;
	}

	public long getVolumeId() {
		return volumeId;
	}

	public String getDescription() {
		return description;
	}

	public List<ImageFile> getImageFiles() {
		return imageFiles;
	}
	
	
}
