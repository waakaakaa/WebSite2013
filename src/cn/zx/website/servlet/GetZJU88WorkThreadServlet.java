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

import cn.zx.website.dao.ZJU88ThreadDao;
import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.Blog;
import cn.zx.website.domain.ZJU88Thread;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/get88workthread")
public class GetZJU88WorkThreadServlet extends HttpServlet {
	private final static Log log = LogFactory
			.getLog(GetZJU88WorkThreadServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int page = Integer.valueOf(req.getParameter("page"));
		log.info("page = " + page);
		ZJU88ThreadDao dao = DaoFactory.getZJU88ThreadDao();
		List<ZJU88Thread> list;
		if (page == -1) {
			list = dao.getAllWorkThreads();
		} else if (page == -2) {
			list = dao.getAllCollectedWorkThreads();
		} else if (page == -3) {
			list = dao.getAllDeletedWorkThreads();
		} else {
			list = dao.getWorkThreads(page);
		}
		JSONArray array = JSONArray.fromObject(list);
		PrintWriter out = resp.getWriter();
		out.println(array.toString());
		out.flush();
		out.close();
	}
}