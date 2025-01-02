package doself.user.food.service;

import java.util.List;

import doself.user.food.domain.Food;

public interface FoodService {
	
	// 음식 목록 조회
	List<Food> getFoodList();
	
	// 음식 상세 조회
	Food getFoodDetail(String foodKeyNum);

}
