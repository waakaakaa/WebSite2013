package cn.zx.website.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import cn.zx.website.dao.WeatherDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Blog;
import cn.zx.website.domain.Weather;

public class WeatherDaoImpl implements WeatherDao {

	@Override
	public List<Weather> getWeathersInOneDay() {
		String sql = "select * from weather where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, null);
	}

	@Override
	public List<Weather> getWeathersOfHangzhouInOneDay() {
		String sql = "select * from weather_hz where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, null);
	}

	@Override
	public void createHz(Weather weather) {
		String sql = "INSERT INTO weather_hz(createDate,humidity,temperature) VALUES(?,?,?)";
		QueryHelper.update(sql, weather.getCreateDate(), weather.getHumidity(),
				weather.getTemperature());
	}
}