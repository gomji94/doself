package doself.admin.nutrition.service;

import java.util.List;

import doself.admin.nutrition.domain.Nutrition;

public interface NutritionService {
	
	//영양정보등록 요청
	List<Nutrition> getNutritionList(String searchType, String searchKeyword, String startDate, String endDate);
}
