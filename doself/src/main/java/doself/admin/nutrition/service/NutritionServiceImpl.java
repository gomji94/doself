package doself.admin.nutrition.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.nutrition.domain.FoodNutritionInfo;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.domain.NutritionInfo;
import doself.admin.nutrition.mapper.NutritionMapper;
import doself.common.mapper.CommonMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NutritionServiceImpl implements NutritionService{
	
	private final NutritionMapper nutritionMapper;
	private final CommonMapper commonMapper;
	
	// 영양정보 요청 검색
	@Override
	public PageInfo<Nutrition> getNutritionRequestList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
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
		
		int rowCnt = nutritionMapper.getCntNutritionRequestList(searchMap);		
		List<Nutrition> nutritionList = nutritionMapper.getNutritionRequestList(searchMap);
		
		return new PageInfo<>(nutritionList, pageable, rowCnt);
	}

	// 특정 영양정보 조회
	@Override
	public Nutrition getNutritionByNirrNum(String nirrNum) {
		
		return nutritionMapper.getNutritionByNirrNum(nirrNum);
	}

	// 영양정보 음식 새로운키생성
	@Override
	public String foodNutritionNewKey() {
		String newKey = commonMapper.getPrimaryKey("mni_","meal_nutrition_info","mni_num");
		return newKey;
	}
	
	// 영양정보 영양제 새로운키생성
	@Override
	public String NutritionNewKey() {
		String newKey = commonMapper.getPrimaryKey("dsi_","dietary_supplement_info","dsi_num");
		return newKey;
	}

	// 음식 영양정보 등록
	@Override
	public void createFoodNutritionList(FoodNutritionInfo nutiritionInfo) {
		
		nutritionMapper.createFoodNutritionList(nutiritionInfo);
		nutritionMapper.modifyFoodNutritionList(nutiritionInfo);
	}
	
	// 영양제 영양정보 등록
	@Override
	public void createNutritionList(NutritionInfo nutiritionInfo) {
		
		nutritionMapper.createNutritionList(nutiritionInfo);
		nutritionMapper.modifyNutritionList(nutiritionInfo);
	}

	// 음식 영양정보 조회
	@Override
	public PageInfo<FoodNutritionInfo> getFoodNutritionList(String searchType, String searchKeyword, Pageable pageable) {
		
		switch(searchType) {
			case "mniName" 	-> searchType = "mni_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("pageable", pageable);
		
		int rowCnt = nutritionMapper.getCntFoodNutritionList(searchMap);		
		List<FoodNutritionInfo> nutritionList = nutritionMapper.getFoodNutritionList(searchMap);
		
		return new PageInfo<>(nutritionList, pageable, rowCnt);
	}
	
	// 영양제 영양정보 조회
	@Override
	public PageInfo<NutritionInfo> getNutritionList(String searchType, String searchKeyword, Pageable pageable) {
		
		switch(searchType) {
		case "dsiName" 	-> searchType = "dsi_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("pageable", pageable);
		
		int rowCnt = nutritionMapper.getCntNutritionList(searchMap);		
		List<NutritionInfo> nutritionList = nutritionMapper.getNutritionList(searchMap);
		
		return new PageInfo<>(nutritionList, pageable, rowCnt);
	}

	// 영양정보 반려 등록
	@Override
	public void modifyNutrition(Nutrition nutrition) {
		
		nutritionMapper.modifyNutritionRejection(nutrition);
	}

	// 특정 음식 영양정보 조회
	@Override
	public FoodNutritionInfo getFoodInfoByMniNum(String mniNum) {

		return nutritionMapper.getFoodInfoByMniNum(mniNum);
	}

	// 특정 음식 영양정보 수정
	@Override
	public void modifyFoodNutrition(FoodNutritionInfo foodNutritionInfo) {
		
		nutritionMapper.modifyFoodNutrition(foodNutritionInfo);
	}

	// 특정 영양제 영양정보 조회
	@Override
	public NutritionInfo getNutritionInfoByDsiNum(String dsiNum) {
		
		return nutritionMapper.getNutritionInfoByDsiNum(dsiNum);
	}

	// 특정 영양제 영양정보 수정
	@Override
	public void modifyNutrition(NutritionInfo nutritionInfo) {
		
		nutritionMapper.modifyNutrition(nutritionInfo);
	}

	// 브랜드 이름 조회
	@Override
	public List<Map<String, Object>> getDsbList() {
		
		
		return nutritionMapper.getDsbList();
	}

	
}
