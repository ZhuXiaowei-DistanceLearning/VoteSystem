package com.zxw.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zxw.auth.entity.UserInfo;
import com.zxw.auth.utils.JwtUtils;
import com.zxw.auth.utils.RsaUtils;
import com.zxw.config.JwtProperties;
import com.zxw.pojo.Option;
import com.zxw.pojo.Subject;
import com.zxw.pojo.User_Item;
import com.zxw.pojo.User_Subject;
import com.zxw.service.VoteService;
import com.zxw.util.CookieUtils;
import com.zxw.util.JsonUtils;
import com.zxw.util.Logger;
import com.zxw.vo.mainVo;
import com.zxw.vo.voteInfo;

public class VoteServlet extends HttpServlet {

	VoteService voteService = new VoteService();
	JwtProperties properties = new JwtProperties();
	Logger logger = new Logger(VoteServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter writer = resp.getWriter();
		String value = CookieUtils.getCookieValue(req, properties.getCookieName());
		try {
			UserInfo info = JwtUtils.getInfoFromToken(value, properties.getPublicKey());
			String path = req.getPathInfo();
			switch (path) {
			case "/findAll":
				List<User_Subject> list = voteService.findAll(info.getId());
				writer.print(JsonUtils.serialize(list));
				break;
			case "/voting":
				String id = req.getParameter("id");
				List<Option> options = voteService.findVoteInfo(id);
				writer.print(JsonUtils.serialize(options));
				break;
			case "/querySubjectByUser":
				List<User_Subject> userList = voteService.selectSubjectByUser(info.getId());
				writer.print(JsonUtils.serialize(userList));
				break;
			case "/mainVote":
				String userId = req.getParameter("userId");
				String subjectId = req.getParameter("subjectId");
				if (!userId.equals(String.valueOf(info.getId()))) {
					break;
				}
				List<mainVo> userList2 = voteService.mainVote(userId, subjectId);
				writer.print(JsonUtils.serialize(userList2));
				break;
			case "/updateOptionStatus":
				try {
					String subjectId2 = req.getParameter("subjectId");
					String status = req.getParameter("status");
					voteService.updateOptionStatus(subjectId2, String.valueOf(info.getId()), status);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "/queryResult":
				subjectId = req.getParameter("subjectId");
				List<User_Item> user_list = voteService.queryResult(subjectId);
				writer.print(JsonUtils.serialize(user_list));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.info("cookie获取失败");
			throw new RuntimeException();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String path = req.getPathInfo();
		Subject subject = new Subject();
		Option option = new Option();
		switch (path) {
		case "/insertVote":
			String cookieValue = CookieUtils.getCookieValue(req, properties.getCookieName());
			try {
				UserInfo userInfo = JwtUtils.getInfoFromToken(cookieValue, properties.getPublicKey());
				StringBuilder sb = new StringBuilder();
				try (BufferedReader reader = req.getReader()) {
					char[] buff = new char[1024];
					int len;
					while ((len = reader.read(buff)) != -1) {
						sb.append(buff, 0, len);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				voteInfo info = JsonUtils.parse(sb.toString(), voteInfo.class);
				voteService.insertVote(info,userInfo.getId());
				resp.getWriter().print("success");
			} catch (Exception e) {
				throw new RuntimeException();
			}
			break;
		case "/insertItem":
			String id = req.getParameter("radios");
			String subjectId = req.getParameter("subjectId");
			String cookieValue1 = CookieUtils.getCookieValue(req, properties.getCookieName());
			try {
				UserInfo userInfo = JwtUtils.getInfoFromToken(cookieValue1, properties.getPublicKey());
				voteService.insertItem(userInfo.getId(), subjectId, id);
				writer.print(JsonUtils.serialize("success"));
			} catch (Exception e) {
				logger.info("失败");
				throw new RuntimeException();
			}
			break;
		case "/updateVoteInfo":
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = req.getReader()) {
				char[] buff = new char[1024];
				int len;
				while ((len = reader.read(buff)) != -1) {
					sb.append(buff, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<mainVo> voList = new ArrayList<>();
			voList = JsonUtils.parseList(sb.toString(), mainVo.class);
			voteService.updateVoteInfo(voList);
			break;
		default:
			break;
		}
	}

//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		PrintWriter writer = resp.getWriter();
//		req.setCharacterEncoding("utf-8");
//		resp.setContentType("text/html;charset=utf-8");
//		String path = req.getPathInfo();
//		String cookieValue = CookieUtils.getCookieValue(req, properties.getCookieName());
//		switch (path) {
//		case "/updateOptionStatus":
//			try {
//				UserInfo info = JwtUtils.getInfoFromToken(cookieValue, properties.getPublicKey());
//				String subjectId = req.getParameter("subjectId");
//				String status = req.getParameter("status");
//				voteService.updateOptionStatus(subjectId, String.valueOf(info.getId()), status);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info("鐢ㄦ埛璁よ瘉澶辫触");
//				throw new RuntimeException();
//			}
//
//			break;
//		default:
//			break;
//		}
//	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
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
			throw new RuntimeException();
		}
	}

}
