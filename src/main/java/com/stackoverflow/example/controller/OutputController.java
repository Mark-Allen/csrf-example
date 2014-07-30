package com.stackoverflow.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/output/{name}")
public class OutputController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String register(@PathVariable("name") String name, HttpSession session) {
		logger.debug("Arrived at OutputController GET method for name={}.",name);
		logger.debug("SessionId={}",session.getId());
		return "/output";
	} 

	@ModelAttribute("name")
	private String getAccountStatus(@PathVariable("name") String name) { 
			logger.debug("Putting name = {} into model.",name);
			return name;
		}
}
