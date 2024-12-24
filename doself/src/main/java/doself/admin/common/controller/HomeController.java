package doself.admin.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class HomeController {
	
	@GetMapping(value={"","/"})
	public String adminHome(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		System.out.println(request.getRequestURI());
		return "admin/index";
	}
}
