package com.zxw.pojo;

import java.util.List;

import com.zxw.annoation.ID;
import com.zxw.annoation.IgnoreField;
import com.zxw.annoation.Table;

@Table("user_subject")
public class User_Subject {
	private String userId;
	@ID("subjectId")
	private String subjectId;
	private Integer status;
	@IgnoreField
	private User user;
	@IgnoreField
	private Subject subject;
	@IgnoreField
	private List<Option> options;
	@IgnoreField
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
