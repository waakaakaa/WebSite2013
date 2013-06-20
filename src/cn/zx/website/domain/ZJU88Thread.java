package cn.zx.website.domain;

import java.sql.Timestamp;

public class ZJU88Thread {
	private int id;
	private String title;
	private String href;
	private Timestamp timestamp;

	public ZJU88Thread(String title, String href, Timestamp timestamp) {
		this.title = title;
		this.href = href;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Thread [id=" + id + ", title=" + title + ", href=" + href
				+ ", timestamp=" + timestamp + "]";
	}
}