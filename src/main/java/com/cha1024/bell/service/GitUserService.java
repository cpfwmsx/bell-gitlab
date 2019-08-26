package com.cha1024.bell.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cha1024.bell.model.UserInfo;
import com.cha1024.bell.model.WealthValueRecord;
import com.cha1024.bell.repository.UserInfoDao;
import com.cha1024.bell.repository.WealthValueDao;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

@Service
public class GitUserService {
	String clientId = "de6ce917e46c62aad562f6592a02e4785927d6730264ab160d8b80cf82856ab2";
	String secret = "14b1ef02c6408d2fe52f3c853868da0fd1bf6cd2d6322a5f4a50c6b8bcd6b9cf";
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private WealthValueDao wealthValueDao;
	/**
	 * 根据用户id查询对象
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfoById(String id) {
		return userInfoDao.getUserInfoById(id);
	}
	/**
	 * 根据用户授权后的字符串创建用户
	 * @param userJson
	 */
	private UserInfo getUserInfoByAuthUserJson(String userJson) {
		JSON userObj = JSONUtil.parse(userJson);
		if(userObj!=null) {
			Integer gitUid = (Integer) userObj.getByPath("id");
			UserInfo dbUserInfo = userInfoDao.getByGitUid(gitUid);
			if(dbUserInfo == null) {
				dbUserInfo = new UserInfo();
				String name = (String) userObj.getByPath("name");
				String username = (String) userObj.getByPath("username");
				String state = (String) userObj.getByPath("state");
				String created_at = (String) userObj.getByPath("created_at");
				String avatar_url = (String) userObj.getByPath("avatar_url");
				String web_url = (String) userObj.getByPath("web_url");
				String email = (String) userObj.getByPath("email");
				dbUserInfo.setName(name);
				dbUserInfo.setUsername(username);
				dbUserInfo.setGitUid(gitUid);
				dbUserInfo.setState(state);
				dbUserInfo.setCreated_at(created_at);
				dbUserInfo.setAvatar_url(avatar_url);
				dbUserInfo.setWeb_url(web_url);
				dbUserInfo.setEmail(email);
				dbUserInfo.setVipAvatar(null);//会员头像
				dbUserInfo.setVipCar(null);//座驾
				dbUserInfo.setVipExpirationTime(null);//过期时间
				dbUserInfo.setVipLevel(0);//vip等级
				dbUserInfo.setVipScence(null);//vip场景
				dbUserInfo.setWealthValue(0);//财富值
				dbUserInfo.setCustWealthValue(0);//消耗的财富值
				dbUserInfo.setEventVoice(null);//事件声音
				userInfoDao.insert(dbUserInfo);
			}
			return dbUserInfo;
		}
		return null;
	}
	/**
	 * 获取用户授权后的用户信息
	 * @param code
	 * @param state
	 * @return
	 */
	public UserInfo getUserJsonByCodeAndState(String code, String state) {
		String redirectUri = "http://bell.test.banmazgai.com/auth/gitlab/redirect";
		String gitLabUrl = "http://git.dev.banmazgai.com/oauth/token";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("client_id", clientId);
		paramMap.put("client_secret", secret);
		paramMap.put("code", code);
		paramMap.put("grant_type", "authorization_code");
		paramMap.put("redirect_uri", redirectUri);
		String result = HttpUtil.post(gitLabUrl, paramMap);
		JSON jsonObject = JSONUtil.parse(result);
		String accessToken = (String) jsonObject.getByPath("access_token");
		String userUrl = "http://git.dev.banmazgai.com/api/v4/user?access_token="+accessToken;
		String userResult = HttpUtil.get(userUrl);
		UserInfo userInfo = getUserInfoByAuthUserJson(userResult);
		return userInfo;
	}
	/**
	 * 充值，创建订单,返回订单号
	 * @return
	 */
	public String recharge4CreateBill(String userId, Integer value) {
		UserInfo userInfo = userInfoDao.getUserInfoById(userId);
		if(userInfo != null) {
			Integer gitUid = userInfo.getGitUid();
			String recordNum = UUID.fastUUID().toString();
			WealthValueRecord record = new WealthValueRecord();
			record.setRecordNum(recordNum);
			record.setLogTimestamp(System.currentTimeMillis());
			record.setChangeType(1);
			record.setChannel("web_recharge");
			record.setDescription("通过网页充值");
			record.setGitUid(gitUid);
			record.setUserId(userId);
			record.setPayResult(0);
			record.setResult("待支付");
			record.setResultTimestamp(System.currentTimeMillis());
			record.setValue(value);
			wealthValueDao.insertWealthValue(record);
			return recordNum;
		}
		return null;
	}
	/**
	 * 根据订单号查询对象
	 * @param recordNum
	 * @return
	 */
	public WealthValueRecord getByRecordNum(String recordNum) {
		if(recordNum != null) {
			return wealthValueDao.getWealthValueRecordByRecordNum(recordNum);
		}
		return null;
	}
	/**
	 * 充值，支付订单
	 * @return
	 */
	public Boolean recharge4Pay(String recordNum, String payOrder, boolean payResult) {
		WealthValueRecord entity = getByRecordNum(recordNum);
		if(entity != null) {
			entity.setResultTimestamp(System.currentTimeMillis());
			if(payResult) {
				entity.setPayResult(1);
				entity.setResult("充值成功");
				//增加余额
				UserInfo userInfo = userInfoDao.getUserInfoById(entity.getUserId());
				Integer wealthValue = userInfo.getWealthValue();
				//计算当前值后，更新值，更新会员等级，判断会员等级
				wealthValue = wealthValue + entity.getValue();
				Integer vipLevel = calcVipLevel(userInfo.getWealthValue(), userInfo.getCustWealthValue());
				userInfo.setVipLevel(vipLevel);
				userInfo.setWealthValue(wealthValue);
				userInfoDao.updateById(userInfo);
			}else {
				entity.setPayResult(-1);
				entity.setResult("充值失败");
			}
			wealthValueDao.updateWealthValue(entity);
			return true;
		}
		return false;
	}
	/**
	 * 计算等级
	 * @param wealthValue
	 * @param custWealthValue
	 * @return
	 */
	private Integer calcVipLevel(Integer wealthValue, Integer custWealthValue) {
		Integer level = 0;
		if(wealthValue > 0) {
			level = 1;
		}
		if(custWealthValue > 10) {
			level = 2;
		} else if(custWealthValue >= 10) {
			level = 2;
		} else if(custWealthValue >= 50) {
			level = 3;
		} else if(custWealthValue >= 100) {
			level = 4;
		} else if(custWealthValue >= 150) {
			level = 5;
		} else if(custWealthValue >= 200) {
			level = 6;
		} else if(custWealthValue >= 300) {
			level = 7;
		} else if(custWealthValue >= 400) {
			level = 8;
		} else if(custWealthValue >= 500) {
			level = 9;
		} else if(custWealthValue >= 600) {
			level = 10;
		} else if(custWealthValue >= 700) {
			level = 11;
		} else if(custWealthValue >= 800) {
			level = 12;
		} else if(custWealthValue >= 900) {
			level = 13;
		} else if(custWealthValue >= 1000) {
			level = 14;
		} else {
			level = 2;
		}
		return level;
	}
	/**
	 * 更新头像
	 * @param id
	 * @param avatar
	 * @return
	 */
	public String updateUserInfo4Avatar(String id, String avatar) {
		return null;
	}
	/**
	 * 更新声音特效
	 * @param id
	 * @param voice
	 * @return
	 */
	public String updateUserInfo4EventVoice(String id, String voice) {
		return null;
	}
}
