package com.zxw.vo;

import java.util.List;

public class MenuList {
	private String title;
	private String path;
	private String icon;
	private String expand;
	private List<MenuList> children;

	public MenuList(String string, String string2, String string3, String string4, List<MenuList> list) {
		this.title = string;
		this.path = string2;
		this.icon = string3;
		this.expand = string4;
		this.children = list;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public List<MenuList> getChildren() {
		return children;
	}

	public void setChildren(List<MenuList> children) {
		this.children = children;
	}

}
