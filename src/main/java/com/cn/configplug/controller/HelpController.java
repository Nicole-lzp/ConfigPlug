package com.cn.configplug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelpController {

	@RequestMapping("/")
	public String help() {
//		return "yzm/Code";
		return "homepage";
	}
	
	@RequestMapping("/yzm")
	public String yzm() {
		return "yzm/Code";
	}
	
}
