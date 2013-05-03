package cn.zx.website.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Blog;

public class BlogDaoImpl implements BlogDao {

	@Override
	public void create(Blog blog) {
		String sql = "INSERT INTO blog(createDate,title,content) VALUES(?,?,?)";
		QueryHelper.update(sql, new Timestamp(new Date().getTime()), blog.getTitle(), blog.getContent());
	}

	public List<Blog> findAllBlogs() {
		String sql = "SELECT * FROM blog";
		return QueryHelper.query(Blog.class, sql, null);
	}

}