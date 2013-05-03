package cn.zx.website.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.impl.BlogDaoImpl;
import cn.zx.website.domain.Blog;
import cn.zx.website.util.StringUtil;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/addblog")
public class AddBlogServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(AddBlogServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		Blog blog = new Blog(title, content);
		log.info("blog --> " + blog);

		if (!StringUtil.empty(title) && !StringUtil.empty(content)) {
			BlogDao dao = new BlogDaoImpl();
			dao.create(blog);
			log.info("blog inserted!");
		} else {
			log.info("blog NOT inserted!");
		}
		resp.sendRedirect("/blog.html");
	}
}