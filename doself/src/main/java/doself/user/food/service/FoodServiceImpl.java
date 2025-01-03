package doself.user.food.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.food.domain.Food;
import doself.user.food.mapper.FoodMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	
	private final FoodMapper foodMapper;
	
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
	
	

}
