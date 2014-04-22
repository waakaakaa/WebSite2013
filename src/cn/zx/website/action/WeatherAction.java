package cn.zx.website.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.Weather;
import cn.zx.website.util.HttpUtils;

public class WeatherAction extends Action {
	private static final String WEATHER_URL = "www.weather.com.cn";
	private static Map<String, String> cityMap = new HashMap<>();
	static {
		Properties props = new Properties();
		try {
			props.load(WeatherAction.class.getResourceAsStream("citycode.properties"));
			Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				cityMap.put((String) entry.getKey(), (String) entry.getValue());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ActionUrl(path = "/weather/add")
	public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Iterator<String> it = cityMap.keySet().iterator();
		while (it.hasNext()) {
			String city = it.next();
			String code = cityMap.get(city);
			String json = HttpUtils.get(WEATHER_URL, "/data/sk/" + code + ".html");
			Weather weather = new Weather(parseTime(json), parseHumidity(json), parseTemperature(json));
			System.out.println(weather);
			weatherDao.createCityWeather(weather, city);
		}
	}

	private int parseTemperature(String json) {
		return Integer.parseInt(json.split("\"temp\":\"")[1].split("\"")[0]);
	}

	private int parseHumidity(String json) {
		return Integer.parseInt(json.split("\"SD\":\"")[1].split("%")[0]);
	}

	private Timestamp parseTime(String json) {
		String time = json.split("\"time\":\"")[1].split("\"")[0];
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.split(":")[0]));
		c.set(Calendar.MINUTE, Integer.valueOf(time.split(":")[1]));
		c.set(Calendar.SECOND, 0);
		if (c.after(Calendar.getInstance())) {
			c.add(Calendar.DATE, -1);
		}
		String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return Timestamp.valueOf(t);
	}

	@ActionUrl(path = "/weather/get")
	public void get(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String city = req.getParameter("city");
		List<Weather> list = weatherDao.getWeather(city);
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/cd")
	public void getcd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfChengduInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/hz")
	public void gethz(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfHangzhouInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/sz")
	public void getsz(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfShenzhenInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/wh")
	public void getwh(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersOfWuhanInOneDay();
		sendJSONArrayFromObject(list, resp);
	}

	@ActionUrl(path = "/weather/d")
	public void getd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Weather> list = weatherDao.getWeathersInOneDay();
		sendJSONArrayFromObject(list, resp);
	}
}