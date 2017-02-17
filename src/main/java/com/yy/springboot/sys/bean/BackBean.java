package com.yy.springboot.sys.bean;

import java.io.Serializable;

public class BackBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2654498937011350160L;
	private String procCode;// 接口返回状态码
	private EntryBean payload;// 验证成功返回用户信息
	private String message; // 接口返回验证信息

	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public EntryBean getPayload() {
		return payload;
	}

	public void setPayload(EntryBean payload) {
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
