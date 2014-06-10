package cn.zx.website.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SimpleDBManager {
	private static String driverClass;
	private static String jdbcUrl;
	private static String user;
	private static String password;

	static {
		init(null);
	}

	private SimpleDBManager() {
		throw new UnsupportedOperationException("private constructor!");
	}

	private static void init(Properties dbProperties) {
		try {
			if (dbProperties == null) {
				dbProperties = new Properties();
				dbProperties.load(SimpleDBManager.class.getResourceAsStream("db.properties"));
			}
			Properties cp_props = new Properties();
			for (Object key : dbProperties.keySet()) {
				String skey = (String) key;
				if (skey.startsWith("jdbc.")) {
					String name = skey.substring(5);
					cp_props.put(name, dbProperties.getProperty(skey));
				}
			}
			driverClass = cp_props.getProperty("driverClass");
			jdbcUrl = cp_props.getProperty("jdbcUrl");
			user = cp_props.getProperty("user");
			password = cp_props.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}