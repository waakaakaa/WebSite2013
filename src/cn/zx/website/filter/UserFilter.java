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

import cn.zx.website.util.Constants;

@WebFilter(urlPatterns = "/*")
public class UserFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER);
		String uri = request.getRequestURI();
		if (uri.contains("/login") || uri.equals("/img/ali.jpg") || obj != null) {
			filterChain.doFilter(req, resp);
		} else {
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