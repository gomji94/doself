package doself.admin.nutrition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.challenge.domain.Challenge;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.mapper.NutritionMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NutritionServiceImpl implements NutritionService{
	
	private final NutritionMapper nutritionMapper;
	
	@Override
	public PageInfo<Nutrition> getNutritionList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "nirrCategory" 	-> searchType = "nirr.nirr_category";
			case "scStatus" 		-> searchType = "sc.sc_status";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = nutritionMapper.getCntNutritionList();		
		List<Nutrition> nutritionList = nutritionMapper.getNutritionList(searchMap);
		
		return new PageInfo<>(nutritionList, pageable, rowCnt);
	}
	
}
