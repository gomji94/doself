package doself.admin.nutrition.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.nutrition.domain.Nutrition;

@Mapper
public interface NutritionMapper {
	
	//등록요청 조회
	List<Nutrition> getNutritionList(Map<String, Object> searchMap);
	//등록요청 갯수
	int getCntNutritionList();
}
