package com.zxw.mapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxw.annoation.ID;
import com.zxw.annoation.IgnoreField;
import com.zxw.annoation.Table;
import com.zxw.annoation.column;
import com.zxw.annoation.noIgnoreInsert;
import com.zxw.pojo.User;
import com.zxw.util.DBUtil;
import com.zxw.util.Logger;

import javafx.scene.control.Tab;

public abstract class BaseMapper<T> {
	Logger logger = new Logger(BaseMapper.class);

	public T selectById(Serializable serializable) {
		/**
		 * �ֶ�ӳ�䴦��
		 */
		// �̰߳�ȫ
		StringBuffer buffer = new StringBuffer();
		Class<T> tClass = TgetClass();
		Table table = tClass.getAnnotation(Table.class);
		buffer.append("select ");
		String idName = "";
		Field[] fields2 = tClass.getDeclaredFields();
		Map<String, String> map = new HashMap<>();
		for (Field field : fields2) {
			ID id = field.getAnnotation(ID.class);
			IgnoreField inIgnoreField = field.getAnnotation(IgnoreField.class);
			column column = field.getAnnotation(column.class);
			if (column != null) {
				map.put(column.value(), field.getName());
				buffer.append(field.getName() + ",");
			} else if (inIgnoreField != null) {
				continue;
			} else {
				if (id != null) {
					idName = id.value();
					String type = field.getType().getName();
					map.put("IDtype", type);
					map.put(idName, field.getName());
				}
				map.put(field.getName(), field.getName());
				buffer.append(field.getName() + ",");
			}
		}
		buffer.deleteCharAt(buffer.length() - 1);
		if (table != null) {
			buffer.append(" from " + table.value());
		}
		if (idName != null) {
			String type = map.get("IDtype");
			Object typeClass = fieldTypeClass(type, serializable);
			buffer.append(" where " + idName + " = " + typeClass + ";");
		}
		logger.info(buffer.toString());
		/**
		 * ���ݿ⴦��
		 */
		Connection con = DBUtil.getConnection();
		try {
			T instance = tClass.newInstance();
			PreparedStatement ps = con.prepareStatement(buffer.toString());
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			if (rs.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnLabel(i);
					Object val;
					int type = metaData.getColumnType(i);
					switch (type) {
					case Types.INTEGER:
						val = rs.getInt(i);
						break;
					case Types.BIGINT:
						val = rs.getInt(i);
						break;
					case Types.CHAR:
					case Types.VARCHAR:
						val = rs.getString(i);
						break;
					case Types.LONGNVARCHAR:
					case Types.LONGVARCHAR:
						val = rs.getString(i);
						break;
					case Types.DATE:
						val = rs.getString(i);
						break;
					case Types.TIME:
						val = rs.getTime(i);
						break;
					case Types.TIMESTAMP:
						val = rs.getTimestamp(i);
						break;
					default:
						val = rs.getObject(i);
						break;
					}
					String fieldName = map.get(columnName).substring(0, 1).toUpperCase()
							+ map.get(columnName).substring(1);
					Method method = tClass.getDeclaredMethod("set" + fieldName,
							tClass.getDeclaredField(map.get(columnName)).getType());
					method.invoke(instance, val);
				}
			}
			return instance;
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(con);
		}
		return null;
	}

	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		Class<T> tClass = TgetClass();
		T instance = null;
		Field[] fields = tClass.getDeclaredFields();
		Map<String, String> map = new HashMap<>();
		buffer.append("select *");
		for (Field field : fields) {
			column column = field.getAnnotation(column.class);
			IgnoreField inIgnoreField = field.getAnnotation(IgnoreField.class);
			if (column != null) {
				map.put(column.value(), field.getName());
			} else if (inIgnoreField != null) {
				continue;
			} else {
				map.put(field.getName(), field.getName());
			}
		}
//		buffer = handlerJoinField(buffer);
		Table table = tClass.getAnnotation(Table.class);
		if (table != null) {
			buffer.append(" from " + table.value());
		}
		logger.info(buffer.toString());
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(buffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				instance = tClass.newInstance();
				ResultSetMetaData data = rs.getMetaData();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					String label = data.getColumnLabel(i);
					int type = data.getColumnType(i);
					Object val;
					switch (type) {
					case Types.INTEGER:
						val = rs.getInt(i);
						break;
					case Types.VARCHAR:
						val = rs.getString(i);
						break;
					case Types.DATE:
						val = rs.getDate(i);
						break;
					case Types.DOUBLE:
						val = rs.getDouble(i);
						break;
					case Types.TIMESTAMP:
						val = rs.getTimestamp(i);
						break;
					case Types.CHAR:
						val = rs.getCharacterStream(i);
						break;
					case Types.TIME:
						val = rs.getTime(i);
						break;
					default:
						val = rs.getObject(i);
						break;
					}
					Field field = tClass.getDeclaredField(map.get(label));
					String name = handlerHeadUpper(field.getName());
					tClass.getMethod("set" + name, field.getType()).invoke(instance, val);
				}
				list.add(instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}

	public void insert(T t) {
		StringBuffer buffer = new StringBuffer();
		Class<? extends Object> tClass = t.getClass();
		Field[] fields = tClass.getDeclaredFields();
		Table table = tClass.getAnnotation(Table.class);
		buffer.append("insert into ");
		if (table != null) {
			buffer.append(table.value() + " (");
		}
		try {
			for (Field field : fields) {
				String name = field.getName();
				noIgnoreInsert noIgnoreInsert = field.getAnnotation(noIgnoreInsert.class);
				IgnoreField ignoreField = field.getAnnotation(IgnoreField.class);
				column column = field.getAnnotation(column.class);
				if (noIgnoreInsert != null) {
					buffer.append(name);
				}
				if (ignoreField != null) {
					continue;
				}
				if (column != null) {
					buffer.append(column.value() + ",");
				} else {
					buffer.append(name + ",");
				}
			}
			handlerJoinField(buffer);
			buffer.append(" ) values (");
			for (Field field : fields) {
				IgnoreField inIgnoreField = field.getAnnotation(IgnoreField.class);

				if (inIgnoreField != null) {
					continue;
				} else {
					String name = handlerHeadUpper(field.getName());
					Method method = tClass.getMethod("get" + name);
					Object object = method.invoke(t);
					Object typeClass = fieldTypeClass(field.getType().getName(), object.toString());
					buffer.append(typeClass.toString() + ",");
				}
			}
			handlerJoinField(buffer);
			buffer.append(")");
			logger.info(buffer.toString());
			Connection connection = DBUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(buffer.toString());
			statement.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int update(T t) {
		Class<? extends Object> tClass = t.getClass();
		StringBuffer buffer = new StringBuffer();
		Table table = tClass.getAnnotation(Table.class);
		Field[] fields = tClass.getDeclaredFields();
		buffer.append("update ");
		String idName = "";
		String idVal = "";
		try {
			if (table != null) {
				buffer.append(table.value() + " set ");
			}
			for (Field field : fields) {
				ID id = field.getAnnotation(ID.class);
				column column = field.getAnnotation(column.class);
				IgnoreField inIgnoreField = field.getAnnotation(IgnoreField.class);
				if (inIgnoreField != null) {
					continue;
				}
				String name = handlerHeadUpper(field.getName());
				Method method = tClass.getMethod("get" + name);
				Object object = method.invoke(t);
				Object typeClass = fieldTypeClass(field.getType().getName(), object.toString());
				if (id != null) {
					idName = id.value();
					idVal = typeClass.toString();
					continue;
				}
				if (column != null) {
					buffer.append(column.value() + "=" + typeClass.toString() + ",");
				} else {
					buffer.append(field.getName() + "=" + typeClass.toString() + ",");
				}
			}
			handlerJoinField(buffer);
			if (idName != null) {
				buffer.append(" where " + idName + " = " + idVal);
			}
			logger.info(buffer.toString());
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(buffer.toString());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Serializable serializable) {
		Class<T> tClass = TgetClass();
		Table table = tClass.getAnnotation(Table.class);
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ");
		if (table != null) {
			buffer.append(table.value() + " ");
		}
		Field[] fields = tClass.getDeclaredFields();
		for (Field field : fields) {
			ID id = field.getAnnotation(ID.class);
			if (id != null) {
				Object typeClass = fieldTypeClass(field.getType().getName(), serializable);
				buffer.append(" where " + id.value() + "=" + typeClass.toString());
			}
		}
		logger.info(buffer.toString());
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(buffer.toString());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
//			ResultSetMetaData setMetaData = rs.getMetaData();
//			if (rs.next()) {
//				Method[] methods = class1.getMethods();
//				Field[] fields = class1.getDeclaredFields();
//				ResultSetMetaData metaData = rs.getMetaData();
//				for (Field field : fields) {
//					String typeName = field.getType().getName();
//					String name = field.getName();
//					switch (typeName) {
//					case "java.lang.String":
//						field.setAccessible(true);
//						field.set(instance, rs.getString(field.getName()));
//						field.setAccessible(false);
//						break;
//					case "java.lang.Integer":
//						field.setAccessible(true);
//						field.set(instance, rs.getInt(field.getName()));
//						field.setAccessible(false);
//						break;
//					case "java.lang.Boolean":
//						field.setAccessible(true);
//						field.set(instance, rs.getBoolean(field.getName()));
//						field.setAccessible(false);
//						break;
//					case "java.lang.Float":
//						field.setAccessible(true);
//						field.set(instance, rs.getFloat(field.getName()));
//						field.setAccessible(false);
//						break;
//					case "java.lang.Long":
//						field.setAccessible(true);
//						field.set(instance, rs.getLong(field.getName()));
//						field.setAccessible(false);
//						break;
//					case "java.util.Date":
//						field.setAccessible(true);
//						field.set(instance, rs.getDate(field.getName()));
//						field.setAccessible(false);
//						break;
//					default:
//						break;
//					}
//				}

	// method.invoke(instance, "");
//			}
//				for (Method method : methods) {
//					if (method.getName().contains("set")) {
//						for (Field field : fields) {
//							String name = field.getName();
//						}
//						String typeName = method.getParameterTypes()[0].getName();
//						switch (typeName) {
//						case "java.lang.String":
////							rs.getString();
//							break;
//						default:
//							break;
//						}
//						//method.invoke(instance, "");
//					}
//				}
//			    	emp.setId(rs.getInt("id"));
//			    	emp.setName(rs.getString("name"));
//			    	emp.setSalary(rs.getDouble("salary"));
//			        emp.setSex(rs.getString("sex"));
//			    	emp.setAihao(rs.getString("aihao"));
//			    	return emp;

	public Class<T> TgetClass() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	public Object fieldTypeClass(String type, Serializable serializable) {
		switch (type) {
		case "java.lang.String":
			return "'" + serializable + "'";
		case "java.lang.Integer":
			return serializable;
		default:
			return "'" + serializable + "'";
		}
	}

	public StringBuffer handlerJoinField(StringBuffer buffer) {
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer;
	}

	public String handlerHeadUpper(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	public String handlerHeadLower(String name) {
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return name;
	}
}
