package doself.admin.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {
	
	@GetMapping(value={"","/"})
	public String adminHome() {
		
		return "admin/index";
	}	
}
