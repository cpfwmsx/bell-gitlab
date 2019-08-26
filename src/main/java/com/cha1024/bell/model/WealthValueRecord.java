package com.cha1024.bell.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 财富值变动记录
 * @author nicolas
 *
 */
@Data
@Document(collection = "wealth_value_record")
public class WealthValueRecord {

	@Id
	private String id;
	/**
	 * 记录编号
	 */
	private String recordNum;
	/**
	 * 记录事件
	 */
	private Long logTimestamp;
	/**
	 * 用户信息的id
	 */
	private String userId;
	/**
	 * git的用户id
	 */
	private Integer gitUid;
	/**
	 * 更改类型：1/-1
	 */
	private Integer changeType;
	/**
	 * 数值
	 */
	private Integer value;
	/**
	 * 场景渠道
	 */
	private String channel;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 支付结果:0待支付、-1支付失败、1支付成功
	 */
	private Integer payResult;
	/**
	 * 更改结果的时间
	 */
	private Long resultTimestamp;
	/**
	 * 结果
	 */
	private String result;
}
