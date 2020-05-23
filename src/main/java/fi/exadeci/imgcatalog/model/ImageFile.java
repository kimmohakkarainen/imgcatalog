package fi.exadeci.imgcatalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImageFile {

	@Id @GeneratedValue
	private long imageFileId;
	
	@Column
	private String path;
	
	@Column
	private String hash;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="volumeId", nullable=false)
	private Volume volume;
	
	public static ImageFile create(Volume volume, String path, String hash) {
		ImageFile file = new ImageFile();
		file.volume = volume;
		file.path = path;
		file.hash = hash;
		
		return file;
	}

	public long getImageFileId() {
		return imageFileId;
	}

	public String getPath() {
		return path;
	}

	public String getHash() {
		return hash;
	}

	public Volume getVolume() {
		return volume;
	}
	
}
