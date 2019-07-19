	package com.zxw.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxw.pojo.User;
import com.zxw.util.DBUtil;
import com.zxw.util.Logger;

public class UserMapper extends BaseMapper<User> {
	Logger logger = new Logger(UserMapper.class);
	public User login(String username, String password) {
		Connection connection = DBUtil.getConnection();
		String sql = "select * from user where id='" + username + "' and password = '" + password+"'";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			User user = new User();
			if (rs.next()) {
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setStatus(rs.getInt("status"));
				user.setUsername(rs.getString("username"));
				user.setVersion(rs.getInt("version"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

}
