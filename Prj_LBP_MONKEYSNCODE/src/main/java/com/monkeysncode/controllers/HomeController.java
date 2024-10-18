package com.monkeysncode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController { // Controller who manages the home
	
	@GetMapping("/")
	public String Home() {
		return "Home1";
	}
	
	@GetMapping("/secured")
	public String Secured() {
		return "Secured";
	}
	
}
