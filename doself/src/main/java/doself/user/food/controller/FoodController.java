package doself.user.food.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.food.domain.Food;
import doself.user.food.domain.NutritionRequestInfo;
import doself.user.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("food")
@Slf4j
public class FoodController {
	
	private final FoodService foodService;

	@GetMapping("/list")
	public String getFoodList(Model model) {
		List<Food> foodList = foodService.getFoodList();
		
		// log.info("foodList : {}", foodList);
		
		model.addAttribute("foodList", foodList);
		
		return "user/food/list";
	}
	
	@GetMapping("/view")
	public String getFoodDetail(@RequestParam(name = "foodKeyNum") String foodKeyNum, Model model) {
		
		// log.info("food : {}", foodService.getFoodDetail(foodKeyNum));
		
		model.addAttribute("foodInfo", foodService.getFoodDetail(foodKeyNum));
		
		return "user/food/view";
	}
	
	@PostMapping("/createrequest")
	public String createNutritionRequest(NutritionRequestInfo nutritionRequestInfo) {
		//TODO: process POST request
		
		System.out.println(nutritionRequestInfo);
		
		return null;
	}
	
	
}
