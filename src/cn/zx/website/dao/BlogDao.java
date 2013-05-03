package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.Blog;

public interface BlogDao {
	public void create(Blog blog);

	public List<Blog> findAllBlogs();
}