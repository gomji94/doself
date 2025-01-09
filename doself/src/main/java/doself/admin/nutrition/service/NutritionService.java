package doself.admin.nutrition.service;

import doself.admin.nutrition.domain.FoodNutritionInfo;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.domain.NutritionInfo;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface NutritionService {
	
	//영양정보등록 요청 조회
	PageInfo<Nutrition> getNutritionRequestList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//특정 영양정보 조회
	Nutrition getNutritionByNirrNum(String nirrNum);
	// 음식 영양정보 새로운 키 생성
	String foodNutritionNewKey();
	// 영양제 영양정보 새로운 키 생성
	String NutritionNewKey();
	//음식 영양정보 등록
	void createFoodNutritionList(FoodNutritionInfo nutiritionInfo);
	
	//영양제 영양정보 등록
	void createNutritionList(NutritionInfo nutritionInfo);
	
	//음식 영양정보 조회
	PageInfo<FoodNutritionInfo> getFoodNutritionList(String searchType, String searchKeyword, Pageable pageable);
	//영양제 영양정보 조회
	PageInfo<NutritionInfo> getNutritionList(String searchType, String searchKeyword, Pageable pageable);
	
	//반려
	void modifyNutrition(Nutrition nutrition);
}
