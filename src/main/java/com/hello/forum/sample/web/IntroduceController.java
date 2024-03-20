package com.hello.forum.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroduceController {

	// http://localhost:8080/introduce
	@GetMapping("/introduce")
	public String viewIntroducePage(Model model) {
		
		model.addAttribute("name", "장민창");
		model.addAttribute("age", 40);
		model.addAttribute("city", "인천");
		
		return "introduce";
	}
}
