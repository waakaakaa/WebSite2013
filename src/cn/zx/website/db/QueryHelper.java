package cn.zx.website.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.ArrayUtils;

public class QueryHelper {
	private final static QueryRunner run = new QueryRunner();

	public static Connection getConnection() {
		try {
			return SimpleDBManager.getConnection();
			// return DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取bean
	 * 
	 * @param beanClass
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T read(Class<T> beanClass, String sql, Object... params) {
		try {
			return (T) run.query(getConnection(), sql, new BeanHandler(beanClass), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取一批bean
	 * 
	 * @param beanClass
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> query(Class<T> beanClass, String sql, Object... params) {
		try {
			return (List<T>) run.query(getConnection(), sql, new BeanListHandler(beanClass), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param beanClass
	 * @param sql
	 * @param page
	 * @param count
	 * @param params
	 * @return
	 */
	public static <T> List<T> query_slice(Class<T> beanClass, String sql, int page, int count, Object... params) {
		if (page < 0 || count < 0)
			throw new IllegalArgumentException("Illegal parameter of 'page' or 'count', Must be positive.");
		int from = (page - 1) * count;
		count = (count > 0) ? count : Integer.MAX_VALUE;
		return query(beanClass, sql + " LIMIT ?,?", ArrayUtils.addAll(params, new Integer[] { from, count }));
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		try {
			return run.update(getConnection(), sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 批量操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] batch(String sql, Object[][] params) {
		try {
			return run.batch(getConnection(), sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}