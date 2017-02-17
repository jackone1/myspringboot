package com.yy.springboot.base;

public class CheckBean  extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkResult; //校验结果
	private String checkDesc;//校验结果说明
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckDesc() {
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
}
