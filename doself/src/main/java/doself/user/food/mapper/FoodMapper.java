package doself.user.food.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.food.domain.Food;

@Mapper
public interface FoodMapper {
	
	// 음식 목록 조회
	List<Food> getFoodList();
	
	// 음식 상세 조회
	Food getFoodDetail(String foodKeyNum);

}
