package cn.zx.website.dao;

import cn.zx.website.domain.User;

public interface UserDao {
	public void create(User user);

	public User findUserByEmailAndPassword(String email, String password);
}