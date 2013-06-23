package cn.zx.website.timertask;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.zx.website.db.DaoFactory;
import cn.zx.website.domain.ZJU88Thread;
import cn.zx.website.util.StringUtil;

public class Get88WorkTimerTask extends TimerTask {
	private static final String _BOARD_WORK = "http://www.zju88.org/agent/board.do?name=Work&mode=0&page=0";
	private final static Log log = LogFactory.getLog(Get88WorkTimerTask.class);

	@Override
	public void run() {
		String html = get();
		List<ZJU88Thread> list = parseHtml(html);
		for (ZJU88Thread thread : list) {
			log.info("ready to insert thread:" + thread);
			DaoFactory.getZJU88ThreadDao().createWorkThread(thread);
		}
	}

	private static String get() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(_BOARD_WORK);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	private static List<ZJU88Thread> parseHtml(String html) {
		List<ZJU88Thread> list = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByTag("tbody");
		for (Element element : elements) {
			Elements ets = element.getElementsByTag("tr");
			for (Element e : ets) {
				String title = parseTitle(e.getElementsByClass("title").text());
				if (!StringUtil.empty(title)) {
					String href = e.getElementsByTag("a").attr("href");
					Timestamp timestamp = parseTime(e
							.getElementsByClass("time").text());
					ZJU88Thread thread = new ZJU88Thread(title, href, timestamp);
					list.add(thread);
				}
			}
		}
		return list;
	}

	private static String parseTitle(String title) {
		String t = title.trim();
		if (!t.endsWith("]")) {
			return t;
		}
		return t.split("\\[")[0].trim();
	}

	private static Timestamp parseTime(String time) {
		return Timestamp.valueOf(time + ":00");
	}

	public static void main(String[] args) {
		new Get88WorkTimerTask().run();
	}
}