package org.pro.demang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LhhController {

    @GetMapping("/")
	public String home() {
		
		return "other/homeNav";
	}

	@GetMapping("/userSearch")
	public String userSearch(@RequestParam("searchVal")String searchVal) {
		System.out.println(searchVal);
		return "redirect:/";
	}

	@GetMapping("/boardSearch")
	public String boardSearch(@RequestParam("searchVal")String searchVal) {
		System.out.println(searchVal);
		return "redirect:/";
	}
}
