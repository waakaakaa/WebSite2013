package cn.zx.website.db;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.WeatherDao;
import cn.zx.website.dao.Work88Dao;
import cn.zx.website.dao.impl.BlogDaoImpl;
import cn.zx.website.dao.impl.UserDaoImpl;
import cn.zx.website.dao.impl.WeatherDaoImpl;
import cn.zx.website.dao.impl.Work88DaoImpl;

public class DaoFactory {
	private DaoFactory() {
		throw new RuntimeException("private constructor!");
	}

	public static BlogDao getBlogDao() {
		return new BlogDaoImpl();
	}

	public static UserDao getUserDao() {
		return new UserDaoImpl();
	}

	public static WeatherDao getWeatherDao() {
		return new WeatherDaoImpl();
	}
	
	public static Work88Dao getWork88Dao(){
		return new Work88DaoImpl();
	}
}