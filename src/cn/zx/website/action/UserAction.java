package cn.zx.website.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.domain.User;
import cn.zx.website.util.Constants;

public class UserAction extends Action {
	private final static Log log = LogFactory.getLog(UserAction.class);

	@ActionUrl(path = "/user/login")
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		User user = userDao.findUserByEmailAndPassword(email, password);
		log.info("user = " + user);
		if (user != null) {
			req.getSession().setAttribute(Constants.SESSION_USER, user);
			resp.sendRedirect("/index.html");
		} else {
			req.setAttribute("error", "wrong password!");
			RequestDispatcher rd = req.getRequestDispatcher("/login.html");
			rd.forward(req, resp);
		}
	}

	@ActionUrl(path = "/user/logout")
	public void get(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.getSession().removeAttribute(Constants.SESSION_USER);
		resp.sendRedirect("/login.html");
	}

}