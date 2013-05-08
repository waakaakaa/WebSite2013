package cn.zx.website.db;

import cn.zx.website.dao.BlogDao;
import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.impl.BlogDaoImpl;
import cn.zx.website.dao.impl.UserDaoImpl;

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
}