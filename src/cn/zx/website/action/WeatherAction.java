package cn.zx.website.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.Weather;

public class WeatherAction extends Action {

	@ActionUrl(path = "/weather/cd")
	public void getcd(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfChengduInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/hz")
	public void gethz(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfHangzhouInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/sz")
	public void getsz(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfShenzhenInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/wh")
	public void getwh(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfWuhanInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/d")
	public void getd(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersInOneDay();
		sendJSONArrayFromObject(list, resp);
	}
}