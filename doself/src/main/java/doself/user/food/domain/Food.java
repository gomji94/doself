package doself.user.food.domain;

import lombok.Data;

@Data
public class Food {
	private String foodKeyNum;
	private String foodName;
	private String foodImg;
	private Integer foodWeight;
	private Integer foodKcal;
	private Integer foodCarbo;
	private Integer foodProtein;
	private Integer foodFat;
	private Integer foodSugar;
	private Integer foodCholesterol;
	private Integer foodSodium;
	private Integer foodCalcium;
	private Integer foodIron;
}
