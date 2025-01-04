package doself.admin.nutrition.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.service.NutritionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/nutrition")
@RequiredArgsConstructor
public class NutritionController {
	
	private final NutritionService nutritionService;
	
	// 영양정보등록 요청 조회
	@GetMapping("/list")
	public String getNutritionList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<Nutrition> nutritionList = nutritionService.getNutritionList(searchType, searchKeyword, startDate, endDate);
		
		model.addAttribute("title", "등록 요청 관리");
		model.addAttribute("nutritionList", nutritionList);
		return "admin/nutrition/admin-nutrition-list";
	}
	
	// 영양정보등록 요청 수정
	@GetMapping("/modifyrequest")
	public String modifyNutritionList(Model model) {

		model.addAttribute("title", "영양 정보 처리");
		return "admin/nutrition/modify-nutrition-list";
	}
	
	// 영양정보 등록
	@GetMapping("/createList")
	public String createNutritionList(Model model) {

		model.addAttribute("title", "영양 정보 등록");
		return "admin/nutrition/create-nutrition-list";
	}	
}
