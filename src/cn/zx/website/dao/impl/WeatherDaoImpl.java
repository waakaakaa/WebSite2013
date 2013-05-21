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

}