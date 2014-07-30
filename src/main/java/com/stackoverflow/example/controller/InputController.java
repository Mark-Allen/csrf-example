package com.stackoverflow.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stackoverflow.example.domain.InputFormData;

@Controller
@RequestMapping("/")
public class InputController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String register(HttpSession session) {
		logger.debug("Arrived at InputController GET method.");
		logger.debug("SessionId={}",session.getId());
		return "/input";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("inputFormData") InputFormData individual,
			BindingResult result, RedirectAttributes redirectAttrs, HttpServletRequest request,HttpSession session) {
		logger.debug("Arrived at InputController POST method.");
		logger.debug("SessionId={}",session.getId());
		if (result.hasErrors()) {
			logger.debug("Form has errors.");
			// errors in the form
			// show the checkout form again
			return "/input";
		}

		logger.debug("No errors, continue with processing for {}:", individual.getName());
 
		  return "redirect:/output/"+individual.getName();
	}

	
	@ModelAttribute("inputFormData")
	private InputFormData getInputFormData() {
		return new InputFormData();
	}

}
