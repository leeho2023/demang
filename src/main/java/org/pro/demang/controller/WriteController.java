package org.pro.demang.controller;

import java.util.HashMap;
import java.util.Map;

import org.pro.demang.model.testDTO;
import org.pro.demang.service.PostService;
import org.pro.demang.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WriteController {
	
	@Autowired
	PostService postService;
	@Autowired
	TestService testService;
		
	@GetMapping("/")
	public String index() {
		return "/test";
	}
	
	@PostMapping("/postInsert")
	   public String postInsert(
			   @RequestParam("file") MultipartFile file,
	         @RequestParam("p_content")String p_content) {
	      try {
	         Map<String, Object> hmap = new HashMap<String, Object>();
	         System.out.println("입력 시작");
	         hmap.put("p_image", file.getBytes());
	         hmap.put("p_content", p_content);
	         postService.postInsert(hmap);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return "redirect:/";
	   }
	
	@GetMapping("/test2")
	public String test2() {
		return "/test2";
	}
	
	@PostMapping("/testInsert")
	public String testInsert(
			@RequestParam("name")String name, 
			@RequestParam("file")MultipartFile file) {
			System.out.println(name + " ///////////////////// " + file);
			
			testDTO dto = new testDTO();
			testService.testInsert(dto);
			
		return "redirect:/test2"; 
	}
	
}
