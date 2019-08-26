package com.cha1024.bell.enums;

public enum ErrorCode {
	/**
	 * 成功
	 */
	SUCCESS(10000), 
	/**
	 * 失败
	 */
	FAILUE(10001),
	/**
	 * 错误
	 */
	ERROR(10002),
	/**
	 * 参数错误
	 */
	BADPARAM(10003), 
	/**
	 * 数据错误
	 */
	BADDATA(10004),
	/**
	 * 网络错误
	 */
	BADNETWORK(10005),
	/**
	 * 被冻结
	 */
	FROZEN(10006),
	/**
	 * 被禁止
	 */
	FORBIDDEN(10007), 
	/**
	 * 已过期
	 */
	EXPIRED(10008),
	/**
	 * 已重复
	 */
	DUPLICATE(10009),
	/**
	 * 未找到
	 */
	NOTFOUND(10010),
	/**
	 * token失效
	 */
	TOKENFAILUE(20000);

	private Integer code;
	/**
	 * 定义枚举使用的构造函数
	 * @param code
	 */
	private ErrorCode(Integer code) {
		setCode(code);
	}

	public Integer getCode() {
		return code;
	}

	void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 返回错误描述
	 * @Title: getDesc
	 * @Description: 中文解释错误详细
	 * @author Nicolas <cpfwmsx@126.com>
	 * @return
	 */
	public String getDesc() {
		switch (code) {
		case 10000:
			return "操作成功";
		case 10001:
			return "操作失败";
		case 10002:
			return "操作错误";
		case 10003:
			return "参数错误";
		case 10004:
			return "数据错误";
		case 10005:
			return "网络错误";
		case 10006:
			return "已被冻结";
		case 10007:
			return "已被禁止";
		case 10008:
			return "已过期";
		case 10009:
			return "已重复";
		case 10010:
			return "未找到";
		case 20000:
			return "token失效";
		default:
			return "未知错误";
		}
	}

}
