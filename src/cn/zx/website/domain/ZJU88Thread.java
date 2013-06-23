package cn.zx.website.domain;

import java.sql.Timestamp;

public class ZJU88Thread {
	private int id;
	private String title;
	private String href;
	private Timestamp createDate;

	public ZJU88Thread(String title, String href, Timestamp timestamp) {
		this.title = title;
		this.href = href;
		this.createDate = timestamp;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp timestamp) {
		this.createDate = timestamp;
	}

	@Override
	public String toString() {
		return "Thread [id=" + id + ", title=" + title + ", href=" + href
				+ ", createDate=" + createDate + "]";
	}
}