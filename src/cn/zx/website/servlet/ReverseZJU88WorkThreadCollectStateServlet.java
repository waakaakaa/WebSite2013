package cn.zx.website.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.dao.ZJU88ThreadDao;
import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.ZJU88Thread;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/rtc")
public class ReverseZJU88WorkThreadCollectStateServlet extends HttpServlet {
	private final static Log log = LogFactory
			.getLog(ReverseZJU88WorkThreadCollectStateServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.valueOf(req.getParameter("id"));
		log.info("id=" + id);
		ZJU88ThreadDao dao = DaoFactory.getZJU88ThreadDao();
		ZJU88Thread thread = dao.readById(id);
		boolean collected = thread.isCollected();
		if (collected) {
			dao.updateUncollected(id);
		} else {
			dao.updateCollected(id);
		}
	}
}