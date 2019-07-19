package com.zxw.pojo;

import com.zxw.annoation.ID;
import com.zxw.annoation.Table;

@Table("item")
public class Item {
	@ID("id")
	private String id;
	private String subjectId;
	private String optionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

}
