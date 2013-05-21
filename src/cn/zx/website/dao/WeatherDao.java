package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.Weather;

public interface WeatherDao {
	public List<Weather> getWeathersInOneDay();
}