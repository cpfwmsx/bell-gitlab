package com.cha1024.bell.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cha1024.bell.model.WealthValueRecord;

@Repository
public class WealthValueDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 新增
	 * @param entity
	 */
	public void insertWealthValue(WealthValueRecord entity) {
		if(entity != null && entity.getId() == null) {
			mongoTemplate.insert(entity);
		}
	}
	/**
	 * 更新对象
	 * @param entity
	 */
	public void updateWealthValue(WealthValueRecord entity) {
		if(entity != null && entity.getId()!=null) {
			Query query = new Query();
			query.addCriteria(new Criteria("id").is(new ObjectId(entity.getId())));
			Update update = new Update();
			update.set("changeType", entity.getChangeType());
			update.set("channel", entity.getChannel());
			update.set("description", entity.getDescription());
			update.set("userId", entity.getUserId());
			update.set("gitUid", entity.getGitUid());
			update.set("logTimestamp", entity.getLogTimestamp());
			update.set("payResult", entity.getPayResult());
			update.set("resultTimestamp", entity.getResultTimestamp());
			update.set("result", entity.getResult());
			update.set("value", entity.getValue());
			mongoTemplate.updateFirst(query, update, WealthValueRecord.class);
		}
	}
	/**
	 * 根据主键查询对象
	 * @param id
	 * @return
	 */
	public WealthValueRecord getWealthValueRecordById(String id) {
		if(ObjectId.isValid(id)) {
			return mongoTemplate.findById(id, WealthValueRecord.class);
		}
		return null;
	}
	/**
	 * 根据recordNum查询对象
	 * @param recordNum
	 * @return
	 */
	public WealthValueRecord getWealthValueRecordByRecordNum(String recordNum) {
		if(recordNum != null) {
			Query query = new Query();
			query.addCriteria(new Criteria("recordNum").is(recordNum));
			return mongoTemplate.findOne(query, WealthValueRecord.class);
		}
		return null;
	}
	/**
	 * 查询分页信息
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	public List<WealthValueRecord> findByUserId(String userId, Integer page, Integer size){
		Query query =new Query();
		query.addCriteria(new Criteria("userId").is(userId));
		Pageable pageable = PageRequest.of(page, size);
		query.with(pageable);
		return mongoTemplate.find(query, WealthValueRecord.class);
	}
}
