package com.zxw.mapper;

import java.sql.Connection;
import java.sql.SQLException;

import com.zxw.util.DBUtil;

/**
 * 数据库操作助手类
 * 
 * @author zxw
 *
 */
public abstract class DatabaseHelper {
	/**
	 * 开启事务
	 */
	public static void beginTransaction() {
		Connection conn = DBUtil.getConnection();
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			} finally {
				
			}
		}
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		Connection conn = DBUtil.getConnection();
		if(conn != null) {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction() {
		Connection conn = DBUtil.getConnection();
		if(conn !=null) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
