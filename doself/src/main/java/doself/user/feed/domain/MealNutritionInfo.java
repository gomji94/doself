package doself.user.feed.domain;

import lombok.Data;

@Data
public class MealNutritionInfo {
	private String mniNum;      // 음식 번호
    private String mniName;     // 음식 이름
    private String mniPicture;  // 음식 이미지
    private Integer mniWeight;  // 음식 중량
    private Integer mniKcal;    // 칼로리
    private Integer mniCarbo;   // 탄수화물
    private Integer mniProtein; // 단백질
    private Integer mniFat;     // 지방
    private Integer mniSugar;   // 당류
    private Integer mniCholesterol; // 콜레스테롤
    private Integer mniSodium;  // 나트륨
    private Integer mniCalcium; // 칼슘
    private Integer mniIron;    // 철분
}