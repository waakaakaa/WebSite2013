package cn.zx.website.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.Work88;
import cn.zx.website.util.HttpUtils;

public class Work88Action extends Action {
	private static final Log log = LogFactory.getLog(Work88Action.class);
	private static final String _BOARD_WORK = "http://proxy3.zju88.net/agent/board.do?name=Work&mode=0&page=0";

	@ActionUrl(path = "/work88/add")
	public void autoAdd(HttpServletRequest req, HttpServletResponse resp) {
		String html = HttpUtils.get(_BOARD_WORK);
		List<Work88> list = parseHtml(html);
		for (Work88 thread : list) {
			work88Dao.createWorkThread(thread);
		}
	}

	private List<Work88> parseHtml(String html) {
		List<Work88> list = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByTag("tbody");
		for (Element element : elements) {
			Elements ets = element.getElementsByTag("tr");
			for (Element e : ets) {
				String title = parseTitle(e.getElementsByClass("title").text());
				if (!(title == null || "".equals(title))) {
					String href = e.getElementsByTag("a").attr("href");
					Timestamp timestamp = parseTime(e.getElementsByClass("time").text());
					Work88 thread = new Work88(title, href, timestamp);
					list.add(thread);
				}
			}
		}
		return list;
	}

	private String parseTitle(String title) {
		String t = title.trim();
		if (!t.endsWith("]")) {
			return t;
		}
		return t.split("\\[")[0].trim();
	}

	private Timestamp parseTime(String time) {
		return Timestamp.valueOf(time + ":00");
	}

	@ActionUrl(path = "/work88/get")
	public void get(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
	public void rtc(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
	public void rtd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
	public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String keyword = req.getParameter("keyword");
		log.info("keyword = " + keyword);
		List<Work88> list = work88Dao.searchWork88(keyword);
		sendJSONArrayFromObject(list, resp);
	}
}