package com.pac4j.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 单点登录后回调使用的过滤器
 **/
public class MyCallbackFilter extends io.buji.pac4j.filter.CallbackFilter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		super.doFilter(servletRequest, servletResponse, filterChain);
	}
}