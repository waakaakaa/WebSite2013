package cn.zx.website.db;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.WeatherDao;
import cn.zx.website.dao.ZJU88ThreadDao;
import cn.zx.website.dao.impl.BlogDaoImpl;
import cn.zx.website.dao.impl.UserDaoImpl;
import cn.zx.website.dao.impl.WeatherDaoImpl;
import cn.zx.website.dao.impl.ZJU88ThreadDaoImpl;

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
	
	public static ZJU88ThreadDao getZJU88ThreadDao(){
		return new ZJU88ThreadDaoImpl();
	}
}