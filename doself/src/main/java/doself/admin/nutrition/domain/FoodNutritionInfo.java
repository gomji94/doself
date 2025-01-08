package doself.admin.nutrition.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodNutritionInfo {
	private String mniNum;
	private String mniName;
	private String mniPicture;
	private int mniWeight;
	private BigDecimal mniKcal; 
	private BigDecimal mniCarbo; 
	private BigDecimal mniProtein; 
	private BigDecimal mniFat; 
	private BigDecimal mniSugar; 
	private BigDecimal mniCholesterol; 
	private BigDecimal mniSodium; 
	private BigDecimal mniCalcium; 
	private BigDecimal mniIron; 
	
	private String nirrNum;
}
