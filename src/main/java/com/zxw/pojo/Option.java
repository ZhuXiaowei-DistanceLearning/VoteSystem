package com.zxw.pojo;

import java.util.List;

import com.zxw.annoation.ID;
import com.zxw.annoation.IgnoreField;
import com.zxw.annoation.Table;
import com.zxw.annoation.column;

@Table("tb_option")
public class Option {
	@ID("id")
	private String id;
	private String name;
	@column("tp_order")
	private Integer order; // 排序选项
	private String subjectId;
	@IgnoreField
	private Subject subject;
	

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "option [id=" + id + ", name=" + name + ", order=" + order + ", subjectId=" + subjectId + "]";
	}

}
