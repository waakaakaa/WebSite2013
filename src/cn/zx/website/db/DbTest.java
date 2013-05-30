package cn.zx.website.db;

import cn.zx.website.dao.impl.UserDaoImpl;
import cn.zx.website.domain.User;

public class DbTest {
	public static void main(String[] args) {
		User user = new UserDaoImpl().findUserByEmailAndPassword("zx", "zx");
		System.out.println(user);
	}
}