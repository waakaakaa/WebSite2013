package cn.zx.website.servlet;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import cn.zx.website.db.DBManager;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/init", loadOnStartup = 3)
public class InitServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		try {
			DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}