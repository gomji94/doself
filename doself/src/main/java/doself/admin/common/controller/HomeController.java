package doself.admin.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {
	
	@GetMapping(value={"","/"})
	public String adminHome(Model model) {

		return "admin/member/admin-member-list";
	}
}
