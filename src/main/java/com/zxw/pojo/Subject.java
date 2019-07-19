package com.zxw.pojo;

import com.zxw.annoation.ID;
import com.zxw.annoation.IgnoreField;
import com.zxw.annoation.Table;
import com.zxw.annoation.noIgnoreInsert;

@Table("subject")
public class Subject {
	@ID("id")
	private String id;
	private String title;
	private Integer type; // 投票类型
	private String beginTime;
	private String endTime;
	@IgnoreField
	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
