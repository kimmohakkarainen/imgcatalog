package fi.exadeci.imgcatalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImageTag {

	@Id @GeneratedValue
	private long imageTagId;

	@Column(columnDefinition="TEXT")
	private String text;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="imageFileId", nullable=false)
	private ImageFile file;
	
	public static ImageTag create(ImageFile file, String text) {
		ImageTag tag = new ImageTag();
		tag.text = text;
		tag.file = file;
		return tag;
	}
}
