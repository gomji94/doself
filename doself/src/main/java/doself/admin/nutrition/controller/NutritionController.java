package doself.admin.nutrition.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.admin.challenge.domain.Challenge;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.service.NutritionService;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/nutrition")
@RequiredArgsConstructor
public class NutritionController {
	
	private final NutritionService nutritionService;
	
	// 영양정보등록 요청 조회
	@GetMapping("/list")
	public String getNutritionList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
			
		var pageInfo = nutritionService.getNutritionList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Nutrition> nutritionList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "등록 요청 관리");
		model.addAttribute("nutritionList", nutritionList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
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
