package com.zxw.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxw.pojo.Option;
import com.zxw.pojo.Subject;
import com.zxw.pojo.User_Item;
import com.zxw.util.DBUtil;

public class User_ItemMapper extends BaseMapper<User_Item>{

	public List<User_Item> queryResult(String subjectId) {
		Connection connection = DBUtil.getConnection();
		List<User_Item> list = new ArrayList<>();
		String sql = "SELECT DISTINCT s.id sid,s.`total`,s.`title`,s.`type`,s.`beginTime`,s.`endTime`,tp.`id` oid,tp.`name`,tp.`tp_order`,it.`id` ,ui.`userId` uid,COUNT(tp.`name`) num FROM `user_item` ui,`subject` s,`tb_option` tp,`item` it \r\n" + 
				"WHERE tp.`subjectId` = s.`id` AND s.`id` = ? \r\n" + 
				"AND it.`subjectId`=s.`id` AND it.`optionId` = tp.`id` AND ui.`itemId` = it.`id` GROUP BY tp.`name`";
		logger.info(sql);
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, subjectId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User_Item user_Item = new User_Item();
				user_Item.setItemId(rs.getString("id"));
				user_Item.setUserId(rs.getString("uid"));
				user_Item.setOptionId(rs.getString("oid"));
				user_Item.setOrder(rs.getInt("tp_order"));
				user_Item.setSubjectId(rs.getString("sid"));
				user_Item.setTitle(rs.getString("title"));
				user_Item.setType(rs.getInt("type"));
				user_Item.setName(rs.getString("name"));
				user_Item.setNum(rs.getInt("num"));
				user_Item.setUser_num(rs.getInt("total"));
				list.add(user_Item);
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
