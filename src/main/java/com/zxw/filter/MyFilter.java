package com.zxw.filter;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zxw.auth.entity.UserInfo;
import com.zxw.auth.utils.JwtUtils;
import com.zxw.auth.utils.RsaUtils;
import com.zxw.config.JwtProperties;
import com.zxw.util.CookieUtils;
import com.zxw.util.Logger;

public class MyFilter implements Filter {
	Logger logger = new Logger(MyFilter.class);
	JwtProperties properties = new JwtProperties();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String pathInfo = req.getPathInfo();
		if (pathInfo.equals("/login")||pathInfo.equals("/register")) {
			chain.doFilter(request, response);
		} else {
			String cookieValue = CookieUtils.getCookieValue(req, properties.getCookieName());
			try {
				UserInfo info = JwtUtils.getInfoFromToken(cookieValue, properties.getPublicKey());
				if (info != null) {
					chain.doFilter(request, response);
				} else {
					logger.info("用户认证失败！");
					throw new RuntimeException();
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
	}

	@Override
	public void destroy() {

	}

	@PostConstruct
	public void init() {
		try {
			logger.info("info");
			File pubKey = new File(properties.getPubKeyPath());
			File priKey = new File(properties.getPriKeyPath());
			if (!pubKey.exists() || !priKey.exists()) {
				RsaUtils.generateKey(pubKey.toString(), priKey.toString(), properties.getSecret());
			}
			properties.setPublicKey(RsaUtils.getPublicKey(properties.getPubKeyPath()));
			properties.setPrivateKey(RsaUtils.getPrivateKey(properties.getPriKeyPath()));
		} catch (Exception e) {
			logger.info("鍒濆鍖栧け璐�");
			throw new RuntimeException();
		}
	}
}
