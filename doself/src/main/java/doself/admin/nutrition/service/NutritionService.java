package doself.admin.nutrition.service;

import doself.admin.nutrition.domain.Nutrition;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface NutritionService {
	
	//영양정보등록 요청
	PageInfo<Nutrition> getNutritionList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
}
