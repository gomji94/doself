package doself.user.food.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.common.mapper.CommonMapper;
import doself.user.food.domain.Food;
import doself.user.food.domain.NutritionRequestInfo;
import doself.user.food.mapper.FoodMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	
	private final FoodMapper foodMapper;
	private final CommonMapper commonMapper;
	
	@Override
	public List<Food> getFoodList() {
		// TODO Auto-generated method stub
		return foodMapper.getFoodList();
	}

	@Override
	public Food getFoodDetail(String foodKeyNum) {
		// TODO Auto-generated method stub
		return foodMapper.getFoodDetail(foodKeyNum);
	}

	@Override
	public List<Food> searchFoodByFoodName(String foodName) {
		// TODO Auto-generated method stub
		
		return foodMapper.searchFoodByFoodName(foodName);
	}

	@Override
	public void createNutritionRequest(NutritionRequestInfo nutritionRequestInfo) {
		// TODO Auto-generated method stub
		
		switch (nutritionRequestInfo.getRequestCategory()) {
			case "food" -> nutritionRequestInfo.setRequestCategory("음식");
			case "nutritionalSupplements" -> nutritionRequestInfo.setRequestCategory("영양제");
		}
		
		String formattedKeyNum = commonMapper.getPrimaryKey("pumh_", "point_use_management_history", "pumh_num");
		
		nutritionRequestInfo.setRequestTableLastPkNum(formattedKeyNum);
		
		foodMapper.createNutritionRequest(nutritionRequestInfo);
		
	}
	
	

}
