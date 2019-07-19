package com.zxw.vo;

public class mainVo {
	private String optionId;
	private String name;
	private Integer order;
	private String subjectId;
	private String title;
	private Integer type;
	private String beginTime;
	private String endTime;

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

	@Override
	public String toString() {
		return "mainVo [optionId=" + optionId + ", name=" + name + ", order=" + order + ", subjectId=" + subjectId
				+ ", title=" + title + ", type=" + type + ", getOptionId()=" + getOptionId() + ", getName()="
				+ getName() + ", getOrder()=" + getOrder() + ", getSubjectId()=" + getSubjectId() + ", getTitle()="
				+ getTitle() + ", getType()=" + getType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
