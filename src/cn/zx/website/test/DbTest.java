package cn.zx.website.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.impl.UserDaoImpl;
import cn.zx.website.domain.User;

public class DbTest {
	UserDao dao = null;

	@Before
	public void init() {
		dao = new UserDaoImpl();
	}

	@After
	public void destroy() {
	}

	@Test
	public void createUser() {
		User user = new User("test@yahoo.com", "testpw");
		dao.create(user);
	}

	@Test
	public void findUser() {
		User user = dao.findUserByEmailAndPassword("test@yahoo.com", "testpw");
		System.out.println(user);
	}
}