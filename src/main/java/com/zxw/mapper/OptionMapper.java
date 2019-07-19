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
import com.zxw.util.DBUtil;
import com.zxw.vo.mainVo;

public class OptionMapper extends BaseMapper<Option> {

	public List<Option> findBySubjectId(String id) {
		Connection connection = DBUtil.getConnection();
		List<Option> list = new ArrayList<>();
		String sql = "SELECT DISTINCT op.`id`,s.`total`,op.`name`,op.`tp_order`,op.`subjectId`,s.`title`,s.`beginTime`,s.`endTime`,s.`type` FROM tb_option op,SUBJECT s WHERE subjectId = ? AND s.`id` = op.`subjectId`;";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Option user = new Option();
				Subject subject = new Subject();
				subject.setTitle(rs.getString("title"));
				subject.setType(rs.getInt("type"));
				subject.setBeginTime(rs.getString("beginTime"));
				subject.setEndTime(rs.getString("endTime"));
				subject.setTotal(rs.getInt("total"));
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setOrder(rs.getInt("tp_order"));
				user.setSubjectId(rs.getString("subjectId"));
				user.setSubject(subject);
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}
	
	public List<mainVo> queryMain(String id) {
		Connection connection = DBUtil.getConnection();
		List<mainVo> list = new ArrayList<>();
		String sql = "SELECT DISTINCT op.`id`,op.`name`,op.`tp_order`,op.`subjectId`,s.`title`,s.`type`,s.`beginTime`,s.`endTime` FROM tb_option op,SUBJECT s WHERE subjectId = ? AND s.`id` = op.`subjectId`;";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				mainVo vo = new mainVo();
				vo.setTitle(rs.getString("title"));
				vo.setType(rs.getInt("type"));
				vo.setOptionId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setOrder(rs.getInt("tp_order"));
				vo.setSubjectId(rs.getString("subjectId"));
				vo.setBeginTime(rs.getString("beginTime"));
				vo.setEndTime(rs.getString("endTime"));
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}

}
