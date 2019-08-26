package com.cha1024.bell.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cha1024.bell.model.UserInfo;

@Repository
public class UserInfoDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 插入数据
	 * @param userInfo
	 */
	public void insert(UserInfo userInfo) {
		if(userInfo != null && userInfo.getId() == null) {
			mongoTemplate.insert(userInfo);
		}
	}
	/**
	 * 根据主键查询对象
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfoById(String id) {
		if(ObjectId.isValid(id)) {
			return mongoTemplate.findById(id, UserInfo.class);
		}
		return null;
	}
	/**
	 * 根据gitlab的用户id查询对象
	 * @param gitUid
	 * @return
	 */
	public UserInfo getByGitUid(Integer gitUid) {
		if(gitUid != null && gitUid > 0) {
			Query query = new Query();
			query.addCriteria(new Criteria("gitUid").is(gitUid));
			return mongoTemplate.findOne(query, UserInfo.class);
		}
		return null;
	}
	/**
	 * 根据主键删除对象
	 * @param id
	 */
	public void removeById(String id) {
		if(ObjectId.isValid(id)) {
			Query query = new Query();
			query.addCriteria(new Criteria("id").is(new ObjectId(id)));
			mongoTemplate.remove(query, UserInfo.class);
		}
	}
	/**
	 * 更新对象
	 * @param userInfo
	 */
	public void updateById(UserInfo userInfo) {
		if(userInfo!=null && userInfo.getId() != null) {
			UserInfo dbUserInfo = mongoTemplate.findById(userInfo.getId(), UserInfo.class);
			if(dbUserInfo != null) {
//				dbUserInfo.setAvatar_url(userInfo.getAvatar_url());
//				dbUserInfo.setCreated_at(userInfo.getCreated_at());
//				dbUserInfo.setEmail(userInfo.getEmail());
//				dbUserInfo.setEventVoice(userInfo.getEventVoice());
//				dbUserInfo.setGitUid(userInfo.getGitUid());
//				dbUserInfo.setName(userInfo.getName());
//				dbUserInfo.setState(userInfo.getState());
//				dbUserInfo.setUsername(userInfo.getUsername());
//				dbUserInfo.setVipAvatar(userInfo.getVipAvatar());
//				dbUserInfo.setVipCar(userInfo.getVipCar());
//				dbUserInfo.setVipExpirationTime(userInfo.getVipExpirationTime());
//				dbUserInfo.setVipLevel(userInfo.getVipLevel());
//				dbUserInfo.setVipScence(userInfo.getVipScence());
//				dbUserInfo.setWealthValue(userInfo.getWealthValue());
//				dbUserInfo.setWeb_url(userInfo.getWeb_url());
				Query query = new Query();
				query.addCriteria(new Criteria("id").is(new ObjectId(dbUserInfo.getId())));
				Update update = new Update();
				update.set("avatar_url", userInfo.getAvatar_url());
				update.set("created_at", userInfo.getCreated_at());
				update.set("email", userInfo.getEmail());
				update.set("eventVoice", userInfo.getEventVoice());
				update.set("gitUid", userInfo.getGitUid());
				update.set("name", userInfo.getName());
				update.set("state", userInfo.getState());
				update.set("username", userInfo.getUsername());
				update.set("vipAvatar", userInfo.getVipAvatar());
				update.set("vipCar", userInfo.getVipCar());
				update.set("vipExpirationTime", userInfo.getVipExpirationTime());
				update.set("vipLevel", userInfo.getVipLevel());
				update.set("vipScence", userInfo.getVipScence());
				update.set("wealthValue", userInfo.getWealthValue());
				update.set("custWealthValue", userInfo.getCustWealthValue());
				update.set("web_url", userInfo.getWeb_url());
				mongoTemplate.updateFirst(query, update, UserInfo.class);
			}
		}
	}
	
}
