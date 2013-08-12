package cn.zx.website.domain;

import java.sql.Timestamp;

public class Work88 {
	private int id;
	private String title;
	private String href;
	private Timestamp createDate;
	private boolean collected;
	private boolean deleted;

	public Work88() {

	}

	public Work88(String title, String href, Timestamp timestamp) {
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

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "ZJU88Thread [id=" + id + ", title=" + title + ", href=" + href
				+ ", createDate=" + createDate + ", collected=" + collected
				+ ", deleted=" + deleted + "]";
	}
}