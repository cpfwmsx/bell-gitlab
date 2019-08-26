package com.cha1024.bell.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user_info")
public class UserInfo {

	@Id
	private String id;
	
	private Integer gitUid;
	/**
	 * 姓名
	 */
	private String name;
	
	private String username;
	/**
	 * 激活状态
	 */
	private String state;
	/**
	 * 头像地址
	 */
	private String avatar_url;
	/**
	 * 个人中心地址
	 */
	private String web_url;
	/**
	 * 账号创建时间
	 */
	private String created_at;
	/**
	 * 邮箱地址
	 */
	private String email;
	
	/**
	 * 会员头像
	 */
	private String vipAvatar;
	/**
	 * 会员座驾
	 */
	private String vipCar;
	/**
	 * vip进入场景
	 */
	private String vipScence;
	/**
	 * vip事件音效
	 */
	private String eventVoice;
	/**
	 * VIP等级
	 */
	private Integer vipLevel;
	/**
	 * vip过期时间
	 */
	private Date vipExpirationTime;
	/**
	 * VIP财富值
	 */
	private Integer wealthValue;
	/**
	 * 消耗的财富值
	 */
	private Integer custWealthValue;
}
