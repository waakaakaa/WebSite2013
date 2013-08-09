package cn.zx.website.dao.impl;

import java.util.List;

import cn.zx.website.dao.WeatherDao;
import cn.zx.website.db.QueryHelper;
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
	public List<Weather> getWeathersOfChengduInOneDay() {
		String sql = "select * from weather_cd where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, null);
	}

	@Override
	public List<Weather> getWeathersOfShenzhenInOneDay() {
		String sql = "select * from weather_sz where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, null);
	}

	@Override
	public List<Weather> getWeathersOfWuhanInOneDay() {
		String sql = "select * from weather_wh where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, null);
	}

}