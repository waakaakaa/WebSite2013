package cn.zx.website.servlet;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.db.DBManager;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/init", loadOnStartup = 3)
public class InitServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(InitServlet.class);

	@Override
	public void init() throws ServletException {
		log.info("================= website starts loading =================");
		try {
			DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("================= website finishes loading =================");
	}
}