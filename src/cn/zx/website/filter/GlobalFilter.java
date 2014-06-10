package cn.zx.website.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zx.website.util.Constants;

@WebFilter(urlPatterns = "/*")
public class GlobalFilter implements Filter {
	private static final Log log = LogFactory.getLog(GlobalFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER);
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		if (url.contains("localhost") || uri.contains("/login") || uri.equals("/img/ali.jpg") || obj != null) {
			filterChain.doFilter(req, resp);
		} else {
			log.info("response.sendRedirect('/login.html');");
			response.sendRedirect("/login.html");
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}