package doself.admin.nutrition.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.admin.nutrition.domain.FoodNutritionInfo;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.domain.NutritionInfo;
import doself.admin.nutrition.service.NutritionService;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/nutrition")
@RequiredArgsConstructor
@Slf4j
public class NutritionController {
	
	private final NutritionService nutritionService;
	
	// 영양정보등록 요청 조회
	@GetMapping("/list")
	public String getNutritionRequestList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
			
		var pageInfo = nutritionService.getNutritionRequestList(searchType, searchKeyword, startDate, endDate, pageable);
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
	
	// 특정 영양정보 등록 요청 정보 가져오기
	@GetMapping("/modifyrequest")
	public String getModifyNutritionListByNirrNum(@RequestParam(value="nirrNum") String nirrNum, Model model) {
		
		Nutrition nutritionInfo = nutritionService.getNutritionByNirrNum(nirrNum);		
		model.addAttribute("title", "영양 정보 처리");
		model.addAttribute("nutritionInfo", nutritionInfo);
		
		return "admin/nutrition/modify-nutrition-rejection";
	}
	
	// 반려
	@PostMapping("modifyrequest")
	public String modifyNutritionList(Nutrition nutrition) {
		
		nutritionService.modifyNutrition(nutrition);
		
		return "redirect:/admin/nutrition/list";
	}

	
	// 영양정보 등록페이지 이동
	@GetMapping("/createList")
	public String getNutritionByNirrNum(@RequestParam(value="nirrNum", defaultValue = "") String nirrNum, Model model) {
		
		String newKey = "";
		Nutrition getNutritionByNirrNum = nutritionService.getNutritionByNirrNum(nirrNum);
		String viewName ="";
		if(getNutritionByNirrNum.getNirrCategory().equals("음식")) {
			newKey = nutritionService.foodNutritionNewKey();
			viewName = "admin/nutrition/create-food-nutrition-list";
		}
		else if(getNutritionByNirrNum.getNirrCategory().equals("영양제")) {
			newKey = nutritionService.NutritionNewKey();
			viewName = "admin/nutrition/create-nutrition-list";
		}
		log.info("newKey : {}", newKey);
		model.addAttribute("title", "영양 정보 등록");
		model.addAttribute("getNutritionByNirrNum", getNutritionByNirrNum);
		model.addAttribute("newKey",newKey);
		return viewName;
	}
	
	
	//음식 영양정보 등록
	@PostMapping("/createFood") 
	public String createFoodNutritionList(FoodNutritionInfo nutritionInfo) {
		
		nutritionService.createFoodNutritionList(nutritionInfo);
		return "redirect:/admin/nutrition/foodlist"; 
	}
	
	//영양제 영양정보 등록
	@PostMapping("/createNutrition") 
	public String createNutritionList(NutritionInfo nutritionInfo) {
		
		nutritionService.createNutritionList(nutritionInfo);
		return "redirect:/admin/nutrition/nutritionlist"; 
	}
	 
	// 음식 영양정보 조회
	@GetMapping("/foodlist")
	public String getFoodNutritionList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword, Model model, Pageable pageable) {
		
		
		var pageInfo = nutritionService.getFoodNutritionList(searchType, searchKeyword, pageable);
		List<FoodNutritionInfo> nutritionList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "등록 요청 관리");
		model.addAttribute("nutritionList", nutritionList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		return "admin/nutrition/food-nutrition-list";
	}
	
	// 영양제 영양정보 조회
	@GetMapping("/nutritionlist")
	public String getNutritionList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword, Model model, Pageable pageable) {
		
		
		var pageInfo = nutritionService.getNutritionList(searchType, searchKeyword, pageable);
		List<NutritionInfo> nutritionList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "등록 요청 관리");
		model.addAttribute("nutritionList", nutritionList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		return "admin/nutrition/nutrition-list";
	}
	
}
