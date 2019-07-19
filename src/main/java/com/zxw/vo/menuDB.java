package com.zxw.vo;

import java.util.ArrayList;
import java.util.List;

public class menuDB {
	public static List<MenuList> adminMenu() {
		List<MenuList> list = new ArrayList<MenuList>();
		List<MenuList> childrenList = new ArrayList<MenuList>();
		childrenList.add(new MenuList("主页", "/dashboard", "home", "false", null));
		List<MenuList> childrenList2 = new ArrayList<MenuList>();
		childrenList2.add(new MenuList("发布新投票", "/pubVote", "chat_bubble", "false", null));
		childrenList2.add(new MenuList("维护投票", "/maintainVote", "chat_bubble", "false", null));
		childrenList2.add(new MenuList("投票查询", "/findAll", "chat_bubble", "false", null));
		list.add(new MenuList("首页", "/index", "home", "true", childrenList));
		list.add(new MenuList("投票标题管理", "/vote", "home", "true", childrenList2));
		return list;
	}
	
	public static List<MenuList> UserMenu() {
		List<MenuList> list = new ArrayList<MenuList>();
		List<MenuList> childrenList = new ArrayList<MenuList>();
		childrenList.add(new MenuList("主页", "/dashboard", "home", "false", null));
		List<MenuList> childrenList2 = new ArrayList<MenuList>();
		childrenList2.add(new MenuList("投票查询", "/findAll", "chat_bubble", "false", null));
		list.add(new MenuList("首页", "/index", "home", "true", childrenList));
		list.add(new MenuList("投票标题管理", "/vote", "home", "true", childrenList2));
		return list;
	}
}
