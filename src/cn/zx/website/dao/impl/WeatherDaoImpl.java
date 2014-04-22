package cn.zx.website.dao.impl;

import java.util.List;

import cn.zx.website.dao.WeatherDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.Weather;

public class WeatherDaoImpl implements WeatherDao {

	@Override
	public List<Weather> getWeathersInOneDay() {
		String sql = "select * from weather where createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public List<Weather> getWeathersOfHangzhouInOneDay() {
		String sql = "select * from weather_china where city='hangzhou' and createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public List<Weather> getWeathersOfChengduInOneDay() {
		String sql = "select * from weather_china where city='chengdu' and createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public List<Weather> getWeathersOfShenzhenInOneDay() {
		String sql = "select * from weather_china where city='shenzhen' and createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public List<Weather> getWeathersOfWuhanInOneDay() {
		String sql = "select * from weather_china where city='wuhan' and createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public List<Weather> getWeather(String city) {
		String sql = "select * from weather_china where city='" + city + "' and createDate >= now()- interval 1 day";
		return QueryHelper.query(Weather.class, sql, (Object[]) null);
	}

	@Override
	public void createCityWeather(Weather weather, String city) {
		String sql = "INSERT INTO weather_china (createDate,humidity,temperature,city) VALUES(?,?,?,?)";
		QueryHelper.update(sql, weather.getCreateDate(), weather.getHumidity(), weather.getTemperature(), city);
	}

}