package doself.user.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@GetMapping("/list")
	public String getCommunityList() {
		return "index";
	}
}
