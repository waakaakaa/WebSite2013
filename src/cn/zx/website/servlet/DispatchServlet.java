package cn.zx.website.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zx.website.annotation.ActionUrl;
import cn.zx.website.db.DBManager;
import cn.zx.website.util.ReflectionTool;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/blog/*", "/user/*", "/weather/*", "/work88/*",
		"/sys/*" }, loadOnStartup = 3)
public class DispatchServlet extends HttpServlet {
	private static final String BASE_PACKAGE = "cn.zx.website.action";
	private static Map<String, String> classMap = new HashMap<>();
	private static Map<String, String> methodMap = new HashMap<>();

	@Override
	public void init() throws ServletException {
		System.out.println("----------------- init starts -----------------");
		Set<Class<?>> classes = ReflectionTool.getClasses(BASE_PACKAGE);
		for (Class<?> c : classes) {
			Method[] methods = c.getDeclaredMethods();
			for (Method m : methods) {
				ActionUrl url = m.getAnnotation(ActionUrl.class);
				if (url != null) {
					classMap.put(url.path(), c.getName());
					methodMap.put(url.path(), m.getName());
				}
			}
		}
		System.out.println(classMap);
		System.out.println(methodMap);
		try {
			DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("----------------- init ends -----------------");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (classMap.containsKey(uri)) {
			try {
				Class<?> c = Class.forName(classMap.get(uri));
				Object object = c.newInstance();
				Method m = c.getMethod(methodMap.get(uri),
						HttpServletRequest.class, HttpServletResponse.class);
				m.invoke(object, req, resp);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		} else {
			PrintWriter out = resp.getWriter();
			out.println("404!");
			out.flush();
			out.close();
		}
	}

}