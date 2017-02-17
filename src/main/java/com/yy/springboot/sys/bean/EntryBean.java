package com.yy.springboot.sys.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EntryBean implements Serializable{ 
	   private  String userName;//门户网站用户名
	   private  String password;
	   private  String realName;// 委托人姓名
	   private  String phone;// 电话
	   private  String email;// 邮箱
	   private  String department;// 部门
	   private  String companyName;//公司名称
	   private  String companyCode; //机构编码（唯一标识）
	   private  String companyType; //企业类型 0：经营企业，1：生产企业(见：OrgTypeEnum)
	   private  String yyzzCode;//营业执照注册号
	   private  String orgCode;//组织机构代码
	   private  String qyfrName;//企业法人
	   private  String qyzcdz;//企业注册地址
	   private  String ypjyxkzbh;//药品经营许可证编号
	   private  String gspCode;// GSP证书编号
	   private  String gmpCode;// GMP证书编号 	
	   private  String ypscxkzbh; //药品生产许可证编号 
	   
	   
	   private  String loginName; //
	   private  String loginPassword; //

		private String springBootscVersion; // 接口返回验证信息
	   
	public EntryBean() {
		super();
	}


	public EntryBean(String loginName, String loginPassword) { 
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}
	
	
	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getYyzzCode() {
		return yyzzCode;
	}
	public void setYyzzCode(String yyzzCode) {
		this.yyzzCode = yyzzCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getQyfrName() {
		return qyfrName;
	}
	public void setQyfrName(String qyfrName) {
		this.qyfrName = qyfrName;
	}
	public String getQyzcdz() {
		return qyzcdz;
	}
	public void setQyzcdz(String qyzcdz) {
		this.qyzcdz = qyzcdz;
	}
	public String getYpjyxkzbh() {
		return ypjyxkzbh;
	}
	public void setYpjyxkzbh(String ypjyxkzbh) {
		this.ypjyxkzbh = ypjyxkzbh;
	}
	public String getGspCode() {
		return gspCode;
	}
	public void setGspCode(String gspCode) {
		this.gspCode = gspCode;
	}
	public String getGmpCode() {
		return gmpCode;
	}
	public void setGmpCode(String gmpCode) {
		this.gmpCode = gmpCode;
	}
	public String getYpscxkzbh() {
		return ypscxkzbh;
	}
	public void setYpscxkzbh(String ypscxkzbh) {
		this.ypscxkzbh = ypscxkzbh;
	}
	public String getSpringBootscVersion() {
		return springBootscVersion;
	}
	public void setSpringBootscVersion(String springBootscVersion) {
		this.springBootscVersion = springBootscVersion;
	}
	   
	   
}
