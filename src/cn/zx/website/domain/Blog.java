package cn.zx.website.domain;

import java.sql.Timestamp;

public class Blog {
	private int id;
	private Timestamp createDate;
	private String title;
	private String content;

	public Blog() {
	}

	public Blog(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Blog [createDate=" + createDate + ", title=" + title + ", content=" + content + "]";
	}
}