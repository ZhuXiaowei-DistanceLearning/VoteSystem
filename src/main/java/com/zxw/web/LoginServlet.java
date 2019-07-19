package com.zxw.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.framework.annotaion.Action;
import org.smart4j.framework.annotaion.Controller;
import org.smart4j.framework.annotaion.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.ServletHelper;

import com.zxw.auth.entity.UserInfo;
import com.zxw.auth.utils.JwtUtils;
import com.zxw.auth.utils.RsaUtils;
import com.zxw.config.JwtProperties;
import com.zxw.pojo.User;
import com.zxw.service.UserService;
import com.zxw.util.CookieUtils;
import com.zxw.util.JsonUtils;
import com.zxw.util.Logger;
import com.zxw.vo.MenuList;
import com.zxw.vo.menuDB;

@Controller
public class LoginServlet {
//	UserService userService = new UserService();
	Logger logger = new Logger(LoginServlet.class);
//	private JwtProperties properties = new JwtProperties();
	@Inject
	private JwtProperties properties;
	@Inject
	private UserService userService;

	@Action("post:/login")
	public Data login(Param param) {
		try {
			properties.setPublicKey(RsaUtils.getPublicKey(properties.getPubKeyPath()));
			properties.setPrivateKey(RsaUtils.getPrivateKey(properties.getPriKeyPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = param.getMap();
		String token = userService.login((String) map.get("username"), (String) map.get("password"), properties);
		CookieUtils.setCookie(ServletHelper.getRequest(), ServletHelper.getResponse(), properties.getCookieName(), token, properties.getCookieMaxAge());
		return new Data("success");
	}
	
	@Action("post:/loginUser")
	public Data loginUser(Param param) {
		List<FormParam> map = param.getFormParamList();
		User token = userService.loginUser((String)map.get(0).getFieldValue(),(String)map.get(1).getFieldValue());
		System.out.println(token);
		return new Data("success");
	}

	@Action("get:/findMenu")
	public Data findMenu(Param param, HttpServletRequest req) {
		String cookieValue = CookieUtils.getCookieValue(req, properties.getCookieName());
		try {
			UserInfo userInfo = JwtUtils.getInfoFromToken(cookieValue, properties.getPublicKey());
			User user2 = userService.findById(userInfo.getId());
			if (user2 != null) {
				if (user2.getVersion() == 1) {
					List<MenuList> list = menuDB.adminMenu();
					if (list != null) {
						return new Data(list);
					}
				} else {
					List<MenuList> list = menuDB.UserMenu();
					if (list != null) {
						return new Data(list);
					}
				}
			} else {
				logger.info("非法用户，无法获取菜单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("菜单生成失败");
			throw new RuntimeException();
		}
		return null;
	}

	@Action("get:/verify")
	public Data verify(Param param,HttpServletRequest req) {
		String value = CookieUtils.getCookieValue(req, properties.getCookieName());
		try {
			UserInfo info = JwtUtils.getInfoFromToken(value, properties.getPublicKey());
			User user = userService.findById(info.getId());
			if (info != null) {
				if (user.getVersion() == 1) {
					info.setQx("1");
				} else {
					info.setQx("2");
				}
				return new Data(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Data("error");
	}
	
	@Action("post:/register")
	public Data register(Param param) {
		Map<String, Object> map = param.getMap();
		User user = new User();
		user.setUsername((String)map.get("username"));
		user.setGender((String)map.get("gender"));
		user.setPassword((String)map.get("password"));
		user.setAge((String)map.get("age"));
		user.setBirthday((String)map.get("birthday"));
		user.setRegion((String)map.get("region"));
		user.setVersion(0);
		user.setStatus(1);
		user.setId(UUID.randomUUID().toString());
		userService.register(user);
		return new Data("success");	
	}
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("utf-8");
//		resp.setContentType("text/html;charset=utf-8");
//		String path = req.getPathInfo();
//		switch (path) {
//		case "/verify":
//			UserInfo info = verify(req);
//			User user =  userService.findById(info.getId());
//			if (info != null) {
//				if(user.getVersion()==1) {
//					info.setQx("1");
//				}else {
//					info.setQx("2");
//				}
//				resp.getWriter().print(JsonUtils.serialize(info));
//			} else {
//				resp.getWriter().print(JsonUtils.serialize("error"));
//				throw new RuntimeException();
//			}
//			break;
//		case "/findMenu":
//			String cookieValue = CookieUtils.getCookieValue(req, properties.getCookieName());
//			try {
//				UserInfo userInfo = JwtUtils.getInfoFromToken(cookieValue, properties.getPublicKey());
//				User user2 =  userService.findById(userInfo.getId());
//				if(user2 !=null) {
//					if(user2.getVersion()==1) {
//						List<MenuList> list = menuDB.adminMenu();
//						if (list != null) {
//							resp.getWriter().print(JsonUtils.serialize(list));
//						}
//					}else {
//						List<MenuList> list = menuDB.UserMenu();
//						if (list != null) {
//							resp.getWriter().print(JsonUtils.serialize(list));
//						}
//					}
//				}else {
//					logger.info("用户查找失败");
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		default:
//			break;
//		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("utf-8");
//		resp.setContentType("text/html;charset=utf-8");
//		String path = req.getPathInfo();
//		switch (path) {
//		case "/login":
//			String username = req.getParameter("username");
//			String password = req.getParameter("password");
//			String token = userService.login(username, password, properties);
//			CookieUtils.setCookie(req, resp, properties.getCookieName(), token, properties.getCookieMaxAge(), null,
//					true);
//			break;
//		case "/register":
//			BufferedReader reader = req.getReader();
//			StringBuilder sb = new StringBuilder();
//			char[] arr = new char[1024];
//			int len;
//			while((len = reader.read(arr))!=-1) {
//				sb.append(arr,0,len);
//			}
//			User user = JsonUtils.parse(sb.toString(), User.class);
//			user.setVersion(0);
//			user.setStatus(1);
//			user.setId(UUID.randomUUID().toString());
//			userService.register(user);	
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doPut(req, resp);
//	}
//
//	@Override
//	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doDelete(req, resp);
//	}
//
//	@PostConstruct
//	public void init() {
//		try {
//			logger.info("info");
//			File pubKey = new File(properties.getPubKeyPath());
//			File priKey = new File(properties.getPriKeyPath());
//			if (!pubKey.exists() || !priKey.exists()) {
//				RsaUtils.generateKey(pubKey.toString(), priKey.toString(), properties.getSecret());
//			}
//			properties.setPublicKey(RsaUtils.getPublicKey(properties.getPubKeyPath()));
//			properties.setPrivateKey(RsaUtils.getPrivateKey(properties.getPriKeyPath()));
//		} catch (Exception e) {
//			throw new RuntimeException();
//		}
//	}
//
//	public UserInfo verify(HttpServletRequest request) {
//		String value = CookieUtils.getCookieValue(request, properties.getCookieName());
//		try {
//			UserInfo info = JwtUtils.getInfoFromToken(value, properties.getPublicKey());
//			return info;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
