package cn.zx.website.servlet;

import java.sql.SQLException;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.db.DBManager;
import cn.zx.website.timertask.Get88WorkTimerTask;
import cn.zx.website.timertask.GetHzWeatherTimerTask;

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
		// new Timer().schedule(new BlogIndexTimerTask(), 0, 1000 * 60 * 15);
		// new Timer().schedule(new GetHzWeatherTimerTask(), 0, 1000 * 60 * 30);
		new Timer().schedule(new Get88WorkTimerTask(), 1000 * 60,
				1000 * 60 * 10);
		log.info("================= website finishes loading =================");
	}
}