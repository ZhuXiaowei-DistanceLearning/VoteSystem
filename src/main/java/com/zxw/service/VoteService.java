package com.zxw.service;

import java.util.List;
import java.util.UUID;


import com.zxw.auth.entity.UserInfo;
import com.zxw.mapper.ItemMapper;
import com.zxw.mapper.OptionMapper;
import com.zxw.mapper.SubjectMapper;
import com.zxw.mapper.User_ItemMapper;
import com.zxw.mapper.User_SubjectMapper;
import com.zxw.pojo.Item;
import com.zxw.pojo.Option;
import com.zxw.pojo.Subject;
import com.zxw.pojo.User_Item;
import com.zxw.pojo.User_Subject;
import com.zxw.util.Logger;
import com.zxw.vo.mainVo;
import com.zxw.vo.voteInfo;

public class VoteService {

	SubjectMapper subjectMapper = new SubjectMapper();
	OptionMapper optionMapper = new OptionMapper();
	User_ItemMapper UitemMapper = new User_ItemMapper();
	ItemMapper itemMapper = new ItemMapper();
	Logger logger = new Logger(VoteService.class);
	User_Subject user_Subject = new User_Subject();
	User_SubjectMapper user_SubjectMapper = new User_SubjectMapper();

	/**
	 * 已废弃
	 * 
	 * @param optionName1
	 * @param optionName2
	 * @param optionName3
	 * @param select
	 * @param subjectName
	 * @param long1
	 */
	public void insertVote(String optionName1, String optionName2, String optionName3, String select,
			String subjectName, Long long1) {
		UUID subjectId = UUID.randomUUID();
		Subject subject = new Subject();
		subject.setId(subjectId.toString());
		subject.setTitle(subjectName);
		subject.setType(Integer.valueOf(select));

		Option option = new Option();
		option.setId(UUID.randomUUID().toString());
		option.setName(optionName1);
		option.setOrder(0);
		option.setSubjectId(subjectId.toString());

		Option option1 = new Option();
		option1.setId(UUID.randomUUID().toString());
		option1.setName(optionName2);
		option1.setOrder(0);
		option1.setSubjectId(subjectId.toString());

		Option option2 = new Option();
		option2.setId(UUID.randomUUID().toString());
		option2.setName(optionName3);
		option2.setOrder(0);
		option2.setSubjectId(subjectId.toString());
		subjectMapper.insert(subject);
		optionMapper.insert(option);
		optionMapper.insert(option1);
		optionMapper.insert(option2);
		user_Subject = new User_Subject();
		user_Subject.setStatus(0);
		user_Subject.setUserId(String.valueOf(long1));
		user_Subject.setSubjectId(subjectId.toString());
		user_SubjectMapper.insert(user_Subject);
	}

	public List<User_Subject> findAll(Long id) {
		List<User_Subject> list = subjectMapper.querySubjectByUser(String.valueOf(id));
		logger.info(list.toString());
		return list;
	}

	public List<Option> findVoteInfo(String id) {
		Subject subject = subjectMapper.selectById(id);
		List<Option> list = optionMapper.findBySubjectId(id);
		return list;
	}

	public void insertItem(Long userId, String optionId, String subjectId) {
		// ---------
		User_Item user_Item = new User_Item();
		String itemId = UUID.randomUUID().toString();
		user_Item.setItemId(itemId);
		user_Item.setUserId(String.valueOf(userId));
		user_Item.setUser_num(1);
		// ----------
		UitemMapper.insert(user_Item);
		Item item = new Item();
		item.setId(itemId);
		item.setSubjectId(optionId);
		item.setOptionId(subjectId);
		itemMapper.insert(item);
	}

	public List<User_Subject> selectSubjectByUser(Long id) {
		List<User_Subject> list = subjectMapper.selectSubjectByUser(String.valueOf(id));
		return list;
	}

	public List<mainVo> mainVote(String userId, String subjectId) {
		List<mainVo> list = optionMapper.queryMain(subjectId);
		return list;
	}

	public void updateOptionStatus(String subjectId, String userId, String status) {
		User_Subject t = new User_Subject();
		t.setSubjectId(subjectId);
		t.setUserId(userId);
		Integer status1 = Integer.valueOf(status);
		if (status1 == 0) {
			t.setStatus(status1);
		} else if (status1 == 1) {
			t.setStatus(status1);
		}
		user_SubjectMapper.update(t);
	}

	public void updateVoteInfo(String optionId, String u_subjectId) {
		Option option = optionMapper.selectById(optionId);
		Subject subject = subjectMapper.selectById(u_subjectId);

	}

	public void updateVoteInfo(List<mainVo> voList) {
		for (mainVo mainVo : voList) {
			Option option = new Option();
			option.setId(mainVo.getOptionId());
			option.setOrder(mainVo.getOrder());
			option.setSubjectId(mainVo.getSubjectId());
			option.setName(mainVo.getName());
			optionMapper.update(option);
		}

		mainVo vo = voList.get(0);
		Subject subject = new Subject();
		subject.setId(vo.getSubjectId());
		subject.setTitle(vo.getTitle());
		subject.setType(vo.getType());
		subject.setBeginTime(vo.getBeginTime());
		subject.setEndTime(vo.getEndTime());
		subjectMapper.update(subject);
	}

	public List<User_Item> queryResult(String subjectId) {
		List<User_Item> list = UitemMapper.queryResult(subjectId);
		return list;
	}

	public void insertVote(voteInfo info, Long long1) {
		UUID subjectId = UUID.randomUUID();
		Subject subject = new Subject();
		subject.setId(subjectId.toString());
		subject.setTitle(info.getSubjectName());
		subject.setType(info.getType());
		subject.setBeginTime(info.getBeginTime());
		subject.setEndTime(info.getEndTime());

		subjectMapper.insert(subject);
		String[] optionNames = info.getOptionNames();
		Option option = null;
		for (int i = 0; i < optionNames.length; i++) {
			option = new Option();
			option.setId(UUID.randomUUID().toString());
			option.setName(optionNames[i]);
			option.setOrder(0);
			option.setSubjectId(subjectId.toString());
			optionMapper.insert(option);
		}
		user_Subject = new User_Subject();
		user_Subject.setStatus(0);
		user_Subject.setUserId(String.valueOf(long1));
		user_Subject.setSubjectId(subjectId.toString());
		user_SubjectMapper.insert(user_Subject);
	}
}
