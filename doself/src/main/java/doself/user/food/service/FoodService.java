package doself.user.food.service;

import java.util.List;

import doself.user.food.domain.Food;
import doself.user.food.domain.NutritionRequestInfo;

public interface FoodService {
	
	// 음식 목록 조회
	List<Food> getFoodList();
	
	// 음식 상세 조회
	Food getFoodDetail(String foodKeyNum);
	
	// 음식 검색
	List<Food> searchFoodByFoodName(String foodName);
	
	// 음식 등록 요청
	int createNutritionRequest(NutritionRequestInfo nutritionRequestInfo);

}
