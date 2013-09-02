package cn.zx.website.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.Work88;

public class Work88Action extends Action {
	private final static Log log = LogFactory.getLog(Work88Action.class);

	@ActionUrl(path = "/work88/get")
	public void get(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int page = Integer.valueOf(req.getParameter("page"));
		log.info("page = " + page);
		List<Work88> list;
		if (page == -1) {
			list = work88Dao.getAllWorkThreads();
		} else if (page == -2) {
			list = work88Dao.getAllCollectedWorkThreads();
		} else if (page == -3) {
			list = work88Dao.getAllDeletedWorkThreads();
		} else {
			list = work88Dao.getWorkThreads(page);
		}
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/work88/rtc")
	public void rtc(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int id = Integer.valueOf(req.getParameter("id"));
		log.info("id=" + id);
		Work88 thread = work88Dao.readById(id);
		boolean collected = thread.isCollected();
		if (collected) {
			work88Dao.updateUncollected(id);
		} else {
			work88Dao.updateCollected(id);
		}
	}

	@ActionUrl(path = "/work88/rtd")
	public void rtd(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int id = Integer.valueOf(req.getParameter("id"));
		log.info("id=" + id);
		Work88 thread = work88Dao.readById(id);
		boolean deleted = thread.isDeleted();
		if (deleted) {
			work88Dao.updateUndeleted(id);
		} else {
			work88Dao.updateDeleted(id);
		}
	}

	@ActionUrl(path = "/work88/search")
	public void search(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String keyword = req.getParameter("keyword");
		log.info("keyword = " + keyword);
		List<Work88> list = work88Dao.searchWork88(keyword);
		sendJSONArrayFromObject(list, resp);
	}
}