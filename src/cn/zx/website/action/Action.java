package cn.zx.website.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.WeatherDao;
import cn.zx.website.dao.Work88Dao;
import cn.zx.website.db.DaoFactory;

public abstract class Action {
	protected static BlogDao blogDao = DaoFactory.getBlogDao();
	protected static UserDao userDao = DaoFactory.getUserDao();
	protected static WeatherDao weatherDao = DaoFactory.getWeatherDao();
	protected static Work88Dao work88Dao = DaoFactory.getWork88Dao();

	protected void sendJSONArrayFromObject(Object o, HttpServletResponse resp)
			throws IOException {
		JSONArray array = JSONArray.fromObject(o);
		PrintWriter out = resp.getWriter();
		out.println(array.toString());
		out.flush();
		out.close();
	}
	
	protected void sendJSONFromObject(Object o, HttpServletResponse resp)
			throws IOException {
		JSONObject object = JSONObject.fromObject(o);
		PrintWriter out = resp.getWriter();
		out.println(object.toString());
		out.flush();
		out.close();
	}
}