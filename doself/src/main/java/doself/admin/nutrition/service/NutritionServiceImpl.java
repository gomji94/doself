package doself.admin.nutrition.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.mapper.NutritionMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NutritionServiceImpl implements NutritionService{
	
	private final NutritionMapper nutritionMapper;
	
	@Override
	public List<Nutrition> getNutritionList() {
		
		return nutritionMapper.getNutritionList();
	}
	
}
