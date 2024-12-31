package doself.admin.nutrition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/nutrition")
public class NutritionController {
	
	// 영양정보등록 요청 조회
	@GetMapping("/list")
	public String getNutritionList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "등록 요청 관리");
		return "admin/nutrition/admin-nutrition-list";
	}
	
	// 영양정보등록 요청 수정
	@GetMapping("/modifyrequest")
	public String modifyNutritionList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "영양 정보 처리");
		return "admin/nutrition/modify-nutrition-list";
	}
	
	// 영양정보 등록
	@GetMapping("/createList")
	public String createNutritionList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "영양 정보 등록");
		return "admin/nutrition/create-nutrition-list";
	}	
}
