package com.zxw.service;

import javax.annotation.PostConstruct;

import org.smart4j.framework.annotaion.Service;
import org.smart4j.framework.annotaion.Transaction;

import com.zxw.auth.entity.UserInfo;
import com.zxw.auth.utils.JwtUtils;
import com.zxw.config.JwtProperties;
import com.zxw.mapper.BaseMapper;
import com.zxw.mapper.UserMapper;
import com.zxw.pojo.User;

@Service
public class UserService {
	UserMapper userMapper = new UserMapper();

	public String login(String username, String password, JwtProperties properties) {
		User user = userMapper.login(username, password);
		UserInfo info = new UserInfo();
		info.setId(Long.valueOf(user.getId()));
		info.setUsername(user.getUsername());
		info.setQx(String.valueOf(user.getStatus()));
		try {
			String token = JwtUtils.generateToken(info, properties.getPrivateKey(), properties.getExpire());
			return token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transaction
	public User loginUser(String username, String password) {
		User user = userMapper.login(username, password);
		return user;
	}

	public void register(User user) {
		userMapper.insert(user);
	}

	public User findById(Long id) {
		User user = userMapper.selectById(String.valueOf(id));
		return user;
	}
	
}
