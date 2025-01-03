package doself.admin.nutrition.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.nutrition.domain.Nutrition;

@Mapper
public interface NutritionMapper {
	
	List<Nutrition> getNutritionList();
}
