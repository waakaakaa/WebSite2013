package cn.zx.website.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Blog;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/searchblog")
public class SearchBlogServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(SearchBlogServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		log.info("keyword = " + keyword);

		List<Blog> list = DaoFactory.getBlogDao().searchBlogs(keyword);
		JSONArray array = JSONArray.fromObject(list);
		PrintWriter out = resp.getWriter();
		out.println(array.toString());
		out.flush();
		out.close();
	}

}