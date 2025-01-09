package doself.admin.nutrition.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.nutrition.domain.FoodNutritionInfo;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.domain.NutritionInfo;

@Mapper
public interface NutritionMapper {
	
	//등록요청 조회
	List<Nutrition> getNutritionRequestList(Map<String, Object> searchMap);
	
	//등록요청 갯수
	int getCntNutritionRequestList(Map<String, Object> searchMap);
	//특정 등록 조회
	Nutrition getNutritionByNirrNum(String nirrNum);
	
	// 음식 영양정보 등록
	void createFoodNutritionList(FoodNutritionInfo nutritionInfo);
	// 음식 영양정보 등록후 상태 수정
	int modifyFoodNutritionList(FoodNutritionInfo nutritionInfo);
	
	// 영양제 영양정보 등록
	void createNutritionList(NutritionInfo nutritionInfo);
	// 영양제 영양정보 등록후 상태 수정
	int modifyNutritionList(NutritionInfo nutritionInfo);
	
	//음식 영양정보 조회
	List<FoodNutritionInfo> getFoodNutritionList(Map<String, Object> searchMap);
	//음식 영양정보 개수 조회
	int getCntFoodNutritionList(Map<String, Object> searchMap);
	
	//영양제 영양정보 조회
	List<NutritionInfo> getNutritionList(Map<String, Object> searchMap);
	//영양제 영양정보 개수 조회
	int getCntNutritionList(Map<String, Object> searchMap);
	
	//영양정보 등록요청 반려
	int modifyNutrition(Nutrition nutrition);
	
}
