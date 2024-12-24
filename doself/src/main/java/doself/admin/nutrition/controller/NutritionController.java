package doself.admin.nutrition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/nutrition")
public class NutritionController {
	
	// 영양정보등록 요청
	@GetMapping("/list")
	public String challengeList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "등록 요청 관리");
		return "admin/nutrition/admin-nutrition-list";
	}
}
