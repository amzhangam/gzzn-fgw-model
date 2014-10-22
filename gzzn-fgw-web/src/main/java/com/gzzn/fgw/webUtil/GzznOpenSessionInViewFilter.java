package com.gzzn.fgw.webUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;


/**
 * <p>Title: GzznOpenSessionInViewFilter</p>
 * <p>Description: OpenSession过滤器</p>
 * <p>Copyright: Copyright (c) 2013 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author ChengZhi
 * @version 1.0
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-8-20下午4:54:47  ChengZhi    new
 */
public class GzznOpenSessionInViewFilter extends OpenSessionInViewFilter{
	private static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css", ".jpg", ".gif", ".png" };

	private String[] excludeSuffixsValues;

	@Override
	protected void initFilterBean() throws ServletException {
		String value = getFilterConfig().getInitParameter(EXCLUDE_SUFFIXS_NAME);
		if (StringUtils.isNotEmpty(value)) {
			excludeSuffixsValues = value.split(",");
		} else {
			excludeSuffixsValues = DEFAULT_EXCLUDE_SUFFIXS;
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();

		for (String suffix : excludeSuffixsValues) {
			if (path.endsWith(suffix))
				return true;
		}

		return false;
	}
}
