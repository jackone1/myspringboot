package com.yy.springboot.utils;

import javax.servlet.http.HttpServletRequest;

import com.yy.springboot.sys.bean.EntryBean;
import com.yy.springboot.utils.CommEnum.OrgTypeEnum;

/**
 * 用户工具类
 */
public class UserUtil {

	public static final String ATTRIBUTE_USERINFO = "userInfo";

	/**
	 * 获取用户信息Session中
	 * 
	 * @param request
	 */
	public static EntryBean getUserInSession(HttpServletRequest request) {
		   return (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
	}
	
	/**
	 * 清除session中的用户信息
	 * 
	 * @param request
	 */
	public static void clearSession(HttpServletRequest request) {
		request.getSession().removeAttribute(ATTRIBUTE_USERINFO);
	}

	/**
	 * 将用户信息放入Session中
	 * 
	 * @param request
	 */
	public static void setUserToSession(HttpServletRequest request, EntryBean entryBean) {
		request.getSession().setAttribute(ATTRIBUTE_USERINFO, entryBean);
	}

	/**
	 * 判断用户是否登录
	 * @param request
	 * @param userName
	 * @return
	 */
	public static boolean isUserEntry(HttpServletRequest request, String userName) {
		EntryBean entryBean = (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
		if (null == entryBean) {
			return false;
		}
		if (!entryBean.getUserName().equals(userName)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断用户是否登录(用于拦截器)
	 * @param request
	 * @param userName
	 * @return
	 */
	public static boolean isUserEntryInterptor(HttpServletRequest request) {
		EntryBean entryBean = (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
		if (null == entryBean) {
			return false;
		} 
		return true;
	}
	
	
	/**
	 * 判断用户是否为生产企业
	 * 
	 * @param request
	 */
	public static boolean isProducerUser(HttpServletRequest request) {
		EntryBean entryBean = (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
		if (null == entryBean) {
			return false;
		}
		
		return OrgTypeEnum.ORG_TYPE_PRODUCER.getEnumCode().equals(entryBean.getCompanyType());
	}
	
	/**
	 * 判断用户是否为经营企业
	 * 
	 * @param request
	 */
	public static boolean isBussinessUser(HttpServletRequest request) {
		EntryBean entryBean = (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
		if (null == entryBean) {
			return false;
		}
		
		return OrgTypeEnum.ORG_TYPE_BUSINESS.getEnumCode().equals(entryBean.getCompanyType());
	}
	
	/**
	 * 获取用户的机构类型
	 * 
	 * @param request
	 */
	public static String getUserOrgType(HttpServletRequest request) {
		EntryBean entryBean = (EntryBean) request.getSession().getAttribute(ATTRIBUTE_USERINFO);
		if (null == entryBean) {
			return "";
		}
		return entryBean.getCompanyType();
	}
}
