package fi.exadeci.imgcatalog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ImageDirectory {

	@Id @GeneratedValue
	private long directoryId;
	
	@Column(nullable=false)
	private String path;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="volumeId", nullable=false)
	private Volume volume;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="directory")
	private List<ImageFile> imageFiles = new ArrayList<>();
	
	public static ImageDirectory create(Volume volume, String path) {
		ImageDirectory file = new ImageDirectory();
		file.volume = volume;
		file.path = path;

		return file;
	}

	public long getDirectoryId() {
		return directoryId;
	}

	public String getPath() {
		return path;
	}

	public Volume getVolume() {
		return volume;
	}

	public List<ImageFile> getImageFiles() {
		return imageFiles;
	}

}
