package fi.exadeci.imgcatalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QueueEntry {

	@Id @GeneratedValue
	private long qEntryId;
	
	@Column(nullable=false)
	private String path;
	
	@Column(nullable=false)
	private String description;

	public static QueueEntry create(String path, String description) {
		QueueEntry json = new QueueEntry();
		json.path = path;
		json.description = description;
		return json;
	}
	
	public long getqEntryId() {
		return qEntryId;
	}

	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}

	
}
