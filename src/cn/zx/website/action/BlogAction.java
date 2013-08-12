package cn.zx.website.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.Blog;

public class BlogAction extends Action {
	private final static Log log = LogFactory.getLog(BlogAction.class);

	@ActionUrl(path = "/blog/add")
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String content = req.getParameter("content");
		Blog blog = new Blog(content);

		if (StringUtils.isNotEmpty(content)) {
			blogDao.create(blog);
			log.info("blog inserted!");
		} else {
			log.info("blog NOT inserted!");
		}
		resp.sendRedirect("/blog.html");
	}

	@ActionUrl(path = "/blog/get")
	public void get(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String page = req.getParameter("page");
		log.info("page = " + page);
		List<Blog> list = blogDao.findBlogs(Integer.valueOf(page));
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/blog/search")
	public void search(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String keyword = req.getParameter("keyword");
		log.info("keyword = " + keyword);
		List<Blog> list = blogDao.searchBlogs(keyword);
		sendJSONArrayFromObject(list, resp);
	}
}