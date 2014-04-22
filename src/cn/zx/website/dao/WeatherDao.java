package cn.zx.website.dao;

import java.util.List;

import cn.zx.website.domain.Weather;

public interface WeatherDao {
	public List<Weather> getWeathersInOneDay();

	public List<Weather> getWeathersOfHangzhouInOneDay();

	public List<Weather> getWeathersOfChengduInOneDay();

	public List<Weather> getWeathersOfShenzhenInOneDay();

	public List<Weather> getWeathersOfWuhanInOneDay();

	public List<Weather> getWeather(String city);

	public void createCityWeather(Weather weather, String city);
}