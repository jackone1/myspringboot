package com.yy.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	
	@RequestMapping(value="/")
//	@ResponseBody
	public String doIndex(HttpServletRequest request, HttpServletResponse response) {
		return "/entry";
	}
	
	@RequestMapping(value="/index")
	public String goIndex(HttpServletRequest request, HttpServletResponse response) {
		return "/index";
	}
	
}
