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

import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.impl.BlogDaoImpl;
import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Blog;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/getblog")
public class GetBlogServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(GetBlogServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Blog> list = DaoFactory.getBlogDao().findAllBlogs();

		JSONArray array = JSONArray.fromObject(list);
		PrintWriter out = resp.getWriter();
		out.println(array.toString());
		out.flush();
		out.close();
	}
}