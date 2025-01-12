package doself.admin.nutrition.service;

import java.util.List;
import java.util.Map;

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
	
	//특정 음식영양정보 조회
	FoodNutritionInfo getFoodInfoByMniNum(String mniNum);
	
	//특정 음식영양정보 수정
	void modifyFoodNutrition(FoodNutritionInfo foodNutritionInfo);
	
	//특정 양양제 영양정보 조회
	NutritionInfo getNutritionInfoByDsiNum(String dsiNum);
	
	//특정 영양제 영양정보 수정
	void modifyNutrition(NutritionInfo nutritionInfo);
	
	// 브랜드이름 조회
	List<Map<String, Object>> getDsbList();
}
