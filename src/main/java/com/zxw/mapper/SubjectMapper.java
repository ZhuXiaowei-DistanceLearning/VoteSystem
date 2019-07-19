package com.zxw.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxw.pojo.Option;
import com.zxw.pojo.Subject;
import com.zxw.pojo.User;
import com.zxw.pojo.User_Item;
import com.zxw.pojo.User_Subject;
import com.zxw.util.DBUtil;

public class SubjectMapper extends BaseMapper<Subject> {
	public User_Item queryUserIsVote(String id) {
		Connection connection = DBUtil.getConnection();
		List<Option> list = new ArrayList<>();
		String sql = "SELECT * FROM `user_item` WHERE userId=?";
		logger.info(sql);
		User_Item user = new User_Item();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				user.setNum(rs.getInt("num"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

	public List<User_Subject> querySubjectByUser(String id) {
		Connection connection = DBUtil.getConnection();
		List<User_Subject> list = new ArrayList<>();
		String sql = "SELECT DISTINCT u.`id`AS uid,u.`username`,u.`version`,s.`beginTime`,s.`endTime`,s.`title`,s.`id` AS sid,s.`type`,us.`status`,s.`total` FROM `user` u,`user_subject` us,`subject` s WHERE u.`id` = us.`userId` AND us.`subjectId` = s.`id`";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User user = new User();
				Subject subject = new Subject();
				User_Subject user_Subject = new User_Subject();
				// ------------
				user.setVersion(rs.getInt("version"));
				user.setUsername(rs.getString("username"));
				subject.setTitle(rs.getString("title"));
				subject.setType(rs.getInt("type"));
				subject.setBeginTime(rs.getString("beginTime"));
				subject.setEndTime(rs.getString("endTime"));
				subject.setTotal(rs.getInt("total"));
				user_Subject.setUser(user);
				user_Subject.setSubject(subject);
				// -------------
				user_Subject.setUserId(rs.getString("uid"));
				user_Subject.setSubjectId(rs.getString("sid"));
				user_Subject.setStatus(rs.getInt("status"));
				list.add(user_Subject);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

	public List<User_Subject> selectSubjectByUser(String id) {
		Connection connection = DBUtil.getConnection();
		List<User_Subject> list = new ArrayList<>();
		String sql = "SELECT * FROM `user_subject` us,`subject` s WHERE us.`subjectId` = s.`id` AND us.`userId` = ?";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Subject subject = new Subject();
				User_Subject user_Subject = new User_Subject();
				// ------------
				subject.setTitle(rs.getString("title"));
				subject.setType(rs.getInt("type"));
				subject.setBeginTime(rs.getString("beginTime"));
				subject.setEndTime(rs.getString("endTime"));
				subject.setTotal(rs.getInt("total"));
				user_Subject.setSubject(subject);
				// -------------
				user_Subject.setUserId(rs.getString("userId"));
				user_Subject.setSubjectId(rs.getString("subjectId"));
				user_Subject.setStatus(rs.getInt("status"));
				list.add(user_Subject);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

	public User_Subject queryVoteBySubjectAndOption(String userId, String subjectId) {
		Connection connection = DBUtil.getConnection();
		List<User_Subject> list = new ArrayList<>();
		String sql = "select distinct s.`id` sid,s.`beginTime`,s.`endTime`,s.`total`,s.`title`,s.`type`,op.`id` oid,op.`name`,op.`tp_order`,us.`status` from `user` u,`user_subject` us,`subject` s,`tb_option` op "
				+ "where us.`subjectId` = s.`id` " + "and us.`userId` = u.`id` " + "and s.`id`=op.`subjectId` "
				+ "and u.`id`=? and s.`id`=?;";
		logger.info(sql);
		User_Subject user_Subject = new User_Subject();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, subjectId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Subject subject = new Subject();
				Option option = new Option();
				// ------------
				subject.setTitle(rs.getString("title"));
				subject.setType(rs.getInt("type"));
				option.setName(rs.getString("name"));
				subject.setBeginTime(rs.getString("beginTime"));
				subject.setEndTime(rs.getString("endTime"));
				subject.setTotal(rs.getInt("total"));
				option.setId(rs.getString("oid"));
				option.setOrder(rs.getInt("tp_order"));
//				user_Subject.setOption(option);
				// -------------
				user_Subject.setSubject(subject);
				user_Subject.setSubjectId(rs.getString("sid"));
				user_Subject.setStatus(rs.getInt("status"));
				list.add(user_Subject);
			}
			return user_Subject;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

}
