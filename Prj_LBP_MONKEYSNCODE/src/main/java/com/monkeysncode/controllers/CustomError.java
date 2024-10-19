package com.monkeysncode.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomError implements ErrorController
{
	    @GetMapping("/error")
	    public String handleError(HttpServletRequest request) {
	    	// Get the error code from the request
	        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            
	        // If the error code is 404, show the custom page
	        if (statusCode != null && statusCode == 404) {
	            return "Error";  // Page error
	        }
	        return "error"; // Any error page
	    }
}
