package com.semgt.route.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
	@RequestMapping("/")
	public String toIndex() {
		return "static/main";
	}
}
