package com.yy.springboot.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.springboot.beans.SpringBootDrugBean;
import com.yy.springboot.beans.SpringBootDrugBeanList;
import com.yy.springboot.memory.MemoryObjects;
import com.yy.springboot.memory.utils.MemorySpringBootDrugBeanListUtils;
import com.yy.springboot.sys.bean.BackBean;
import com.yy.springboot.sys.bean.EntryBean;
import com.yy.springboot.utils.CommEnum.EntryStatusCodeEnum;
import com.yy.springboot.utils.CommEnum.OrgTypeEnum;
import com.yy.springboot.utils.ConfigUtils;
import com.yy.springboot.utils.HttpClientUtil;
import com.yy.springboot.utils.JsonMapper;
import com.yy.springboot.utils.Md5PwdEncoder;
import com.yy.springboot.utils.PwdEncoder;
import com.yy.springboot.utils.UserUtil;

@Controller()
@RequestMapping("entry")
public class EntryController {

	private static final Logger logger = LoggerFactory.getLogger(EntryController.class);
	
	@Value("${version}")
	private String springBootscVersion;
	
	/**
	 * 进入登录页面(注销)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String doEntry(ModelMap modelMap, HttpServletRequest request) {
		modelMap.clear();
		UserUtil.clearSession(request);
		return "redirect:/";
	}

	/**
	 * 点击登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String dologin(ModelMap modelMap, String userName, String password, HttpServletRequest request) {
		modelMap.clear();
		if (UserUtil.isUserEntry(request, userName)) {
			return "redirect:/index";
		}
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			modelMap.put("message", "请填写用户信息");
			return "/entry";
		}
		// md5加密密码
		PwdEncoder pwdEncoder = new Md5PwdEncoder();
		password = pwdEncoder.encodePassword(password);

		// 调用接口
		EntryBean entryBean = new EntryBean(userName, password);
		String requestMessgae = JsonMapper.nonEmptyMapper().toJson(entryBean);
		String responseMessage = HttpClientUtil.getInterfaceData(ConfigUtils.getValueByKey("LOGIN_POST_URL"), requestMessgae, 20000, "utf-8", "utf-8");
		if (StringUtils.isEmpty(responseMessage)) {
			modelMap.put("message", "用户信息不存在");
		} else {}
		return "/entry";
	}

	public String clearData(ModelMap modelMap, HttpServletRequest request, Boolean clearStatus) {
		modelMap.clear();
		//清理数据，否则删除用户Session
		if(clearStatus){
			//从Session里取用户信息
			try {
				MemorySpringBootDrugBeanListUtils.setSpringBootDrugBeanList(MemoryObjects.getInstance(), new SpringBootDrugBeanList(), true);
			} catch (Exception e) {
				UserUtil.clearSession(request);
				modelMap.put("message", "系统异常，未能初始化文件!");
				return "/entry"; //异常时，取消登录
			}
			return "redirect:/index";
		}else{
			UserUtil.clearSession(request);
			return "redirect:/";
		}
	}
}
