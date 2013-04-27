package cn.zx.website.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.website.dao.UserDao;
import cn.zx.website.dao.impl.UserDaoImpl;
import cn.zx.website.domain.User;
import cn.zx.website.util.Constants;

/**
 * 登录Servlet，包含验证用户名密码和设置session中的username值
 * 
 * @author X.Zhang
 * 
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDao userDao = new UserDaoImpl();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		User user = userDao.findUserByEmailAndPassword(email, password);

		if (user != null) {
			req.getSession().setAttribute(Constants.SESSION_USER, user);
			resp.sendRedirect("/index.html");
		} else {
			req.setAttribute("error", "wrong password!");
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.forward(req, resp);
		}
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}