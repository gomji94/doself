package doself.admin.nutrition.domain;

import lombok.Data;

@Data
public class NutritionInfo {
	
	private String dsiNum;
	private String dsbNum;
	private String dsiName;
	private String dsiEfficacy;
	private String dsiPicture;
	private String dsiDailyRecommendedIntake;
	
	private String nirrNum;
}
