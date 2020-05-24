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
public class ImageFile {

	@Id @GeneratedValue
	private long imageFileId;
	
	@Column
	private String name;

	@Column
	private String mimeType;

	@Column
	private String hash;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="volumeId", nullable=false)
	private Volume volume;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="directoryId", nullable=false)
	private ImageDirectory directory;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="file")
	private List<ImageTag> tags = new ArrayList<>();

	public static ImageFile create(Volume volume, ImageDirectory directory, String name, String mimeType, String hash) {
		ImageFile file = new ImageFile();
		file.volume = volume;
		file.directory = directory;
		file.name = name;
		file.mimeType = mimeType;
		file.hash = hash;
		
		return file;
	}


	public long getImageFileId() {
		return imageFileId;
	}


	public String getName() {
		return name;
	}


	public String getHash() {
		return hash;
	}


	public Volume getVolume() {
		return volume;
	}


	public ImageDirectory getDirectory() {
		return directory;
	}


	public String getMimeType() {
		return mimeType;
	}


	public List<ImageTag> getTags() {
		return tags;
	}
	
	

}
