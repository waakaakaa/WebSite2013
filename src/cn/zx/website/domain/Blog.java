package cn.zx.website.domain;

import java.sql.Timestamp;

public class Blog {
	private int id;
	private Timestamp createDate;
	private String content;

	public Blog() {
	}

	public Blog(String content) {
		this.content = content;
	}

	public Blog(Timestamp createDate, String content) {
		this.createDate = createDate;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Blog [createDate=" + createDate + ", content=" + content + "]";
	}
}