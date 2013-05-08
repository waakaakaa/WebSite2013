package cn.zx.website.dao.impl;

import cn.zx.website.dao.UserDao;
import cn.zx.website.db.QueryHelper;
import cn.zx.website.domain.User;

public class UserDaoImpl implements UserDao {

	@Override
	public void create(User user) {
		String sql = "INSERT INTO user(email,password) VALUES(?,?)";
		QueryHelper.update(sql, user.getEmail(), user.getPassword());
	}

	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM user WHERE email=? AND password=?";
		return QueryHelper.read(User.class, sql, email, password);
	}
}