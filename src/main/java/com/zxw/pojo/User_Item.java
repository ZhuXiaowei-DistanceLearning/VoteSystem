package com.zxw.pojo;

import com.zxw.annoation.ID;
import com.zxw.annoation.IgnoreField;
import com.zxw.annoation.Table;
import com.zxw.annoation.column;

@Table("user_item")
public class User_Item {
	@ID("userId")
	private String userId;
	@ID("itemId")
	private String itemId;

	@column("num")
	private Integer user_num;

	@IgnoreField
	private String subjectId;

	@IgnoreField
	private String title;

	@IgnoreField
	private Integer type;

	@IgnoreField
	private String optionId;

	@IgnoreField
	private String name;

	@IgnoreField
	private Integer order;

	@IgnoreField
	private Integer num;

	@IgnoreField
	private String beginTime;
	@IgnoreField
	private String endTime;

	public Integer getUser_num() {
		return user_num;
	}

	public void setUser_num(Integer user_num) {
		this.user_num = user_num;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "User_Item [userId=" + userId + ", itemId=" + itemId + "]";
	}

}
