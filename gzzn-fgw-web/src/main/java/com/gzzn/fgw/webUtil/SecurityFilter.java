package com.gzzn.fgw.webUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 请求允许过滤器
 * 
 * @author yjfeng
 * @date 2011-8-9
 * @version v1.0
 */
public class SecurityFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	private static final String EXCLUDE_PATH_NAME = "excludePath";
	
	private String excludePath;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String path = request.getServletPath();
		logger.info("request servlet path:" + path);

		if (excludePath.indexOf(path) != -1 || path.startsWith("/resources/")) {
			arg2.doFilter(arg0, arg1);
		} else {
			if (request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER) != null) {
				arg2.doFilter(arg0, arg1);
			} else {
				redirectToLoginPage(request, response, path);
			}
		}
	}

	private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		logger.info("非法请求被拦截:['" + path + "'].");
		response.sendRedirect(getLoginUrl(request));
	}

	private String getLoginUrl(HttpServletRequest request) {
		return request.getContextPath();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		excludePath = arg0.getInitParameter(EXCLUDE_PATH_NAME);
	}

}
