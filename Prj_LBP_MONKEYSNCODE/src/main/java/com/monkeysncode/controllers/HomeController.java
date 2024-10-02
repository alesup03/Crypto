package com.monkeysncode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	@GetMapping("/")
	public String Home() {
		return "Home";
	}
	@GetMapping("/secured")
	public String Secured() {
		return "Secured";
	}
	
}
