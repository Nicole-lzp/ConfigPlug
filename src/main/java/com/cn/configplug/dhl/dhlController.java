package com.cn.configplug.dhl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dhl")
public class dhlController {

	@RequestMapping("/pageLayout")
	public String pageLayout() {
		return "dhl/pageLayout";
	}
	
	// 片段表达式
	@RequestMapping("/fragment")
	public String fragment() {
		return "dhl/layout2/fragment";
	}
	
	@RequestMapping("/layout")
	public String layout() {
		return "dhl/layout";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "dhl/home";
	}
	
	@RequestMapping("/project")
	public String project() {
		return "dhl/homepage";
	}
}
