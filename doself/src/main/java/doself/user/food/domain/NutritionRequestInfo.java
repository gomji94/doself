package doself.user.food.domain;

import lombok.Data;

@Data
public class NutritionRequestInfo {
	
	private String requestTableLastPkNum;
	private String requestCategory;
	private String requestItemName;
	private String memberId;
	
}
