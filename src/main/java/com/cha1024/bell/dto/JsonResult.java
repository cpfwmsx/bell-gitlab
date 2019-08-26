package com.cha1024.bell.dto;

import java.io.Serializable;
import java.util.Objects;

import com.cha1024.bell.enums.ErrorCode;

import lombok.Data;

/**
 * 通用的JSON返回对象
 * 
 * @ClassName JsonResult
 * @Description {code: 10000, msg: "操作成功", data: null}
 * @author Nicolas <cpfwmsx@126.com>
 * @date 2018年9月5日 下午7:18:15
 *
 */
@Data
public class JsonResult implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -4492018341057153282L;
	private Integer code;
	private String msg;
	private Object data;
	private Long time;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 默认成功
	 * 
	 * @Title: success
	 * @Description: data为空
	 * @author Nicolas <cpfwmsx@126.com>
	 * @return
	 */
	public static JsonResult success() {
		return success(null);
	}

	public static JsonResult success(Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.SUCCESS.getCode());
		result.setMsg(ErrorCode.SUCCESS.getDesc());
		result.setData(data);
		return result;
	}

	public static JsonResult error() {
		return error(null);
	}

	public static JsonResult error(String errMsg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.ERROR.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult error(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.ERROR.getCode());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public static JsonResult error(Integer code, Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(code);
		result.setData(data);
		return result;
	}
	
	public static JsonResult error(Integer code, String msg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	public static JsonResult failure() {
		return failure(null);
	}

	public static JsonResult failure(String errMsg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.FAILUE.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult failure(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.FAILUE.getCode());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public static JsonResult networkFailure(String errMsg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.BADNETWORK.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult networkFailure(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.BADNETWORK.getCode());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public static JsonResult networkFailure() {
		return networkFailure(null);
	}

	public static JsonResult duplicate(String errMsg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.DUPLICATE.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult duplicate() {
		return duplicate(null);
	}

	public static JsonResult notfound(String errMsg) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.NOTFOUND.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult notfound(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.NOTFOUND.getCode());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public static JsonResult notfound() {
		return notfound(null);
	}

	public static JsonResult frozen(String errMsg) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.FROZEN.getCode());
		result.setTime(System.currentTimeMillis());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult frozen() {
		return frozen(null);
	}

	public static JsonResult baddata() {
		return baddata(null);
	}

	public static JsonResult baddata(String errMsg) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.BADDATA.getCode());
		result.setTime(System.currentTimeMillis());
		result.setMsg(errMsg);
		return result;
	}
	

	public static JsonResult baddata(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.BADDATA.getCode());
		result.setTime(System.currentTimeMillis());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public static JsonResult forbidden() {
		return forbidden(null);
	}

	public static JsonResult forbidden(String errMsg) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.FORBIDDEN.getCode());
		result.setTime(System.currentTimeMillis());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult forbidden(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setCode(ErrorCode.FORBIDDEN.getCode());
		result.setTime(System.currentTimeMillis());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

	public boolean isSuccess() {
		return Objects.equals(ErrorCode.SUCCESS.getCode(), getCode());
	}
	
	public static JsonResult tokenFailure(String errMsg) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.TOKENFAILUE.getCode());
		result.setMsg(errMsg);
		return result;
	}

	public static JsonResult tokenFailure(String errMsg, Object data) {
		JsonResult result = new JsonResult();
		result.setTime(System.currentTimeMillis());
		result.setCode(ErrorCode.TOKENFAILUE.getCode());
		result.setMsg(errMsg);
		result.setData(data);
		return result;
	}

}

