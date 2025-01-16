package doself.user.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Feed {
//private CharSequence dateString;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	private String feedCode;			  	// 피드 코드
	private String memberId;				// 작성자 아이디
	private String mealNutritionInfoCode;  // 음식 영양 정보 코드
	private String mealCategoryCode;		// 식사 카테고리
	private Integer feedFoodIntake;			// 섭취 인분
	private String feedContent;			// 피드 내용
	private String feedPicture;			  	// 피드 이미지
	private Date feedDate;	  				// 피드 등록 날짜
	private Date feedIntakeDate;			// 섭취 날짜
	private Integer	feedLike;			  	// 피드 좋아요(null과 비교 후 카운트)
	private Integer feedOpenStatus;	  		// 공개여부
	private String memberProfileImage;  	// 작성자 프로필 이미지
	private String feedCommentCode; 	 	// 피드 댓글 코드
	private String feedCommentContent;  	// 피드 댓글 내용
	private String mealNum;  				// 음식 영양 정보 코드
	private String mealName;       			// 음식 이름
    private String mealPicture;    			// 음식 사진
    private Integer mealWeight;    			// 1인분 중량
    private Integer mealCalories;      		// 칼로리
    private Integer mealCarbohydrates; 		// 탄수화물
    private Integer mealProtein;       		// 단백질
    private Integer mealFat;           		// 지방
    private Integer mealSugar;         		// 당
    private Integer mealCholesterol;   		// 콜레스테롤
    private Integer mealSodium;        		// 나트륨
    private Integer mealCalcium;       		// 칼슘
    private Integer mealIron;          		// 철분
    private boolean owner;

    // Getter와 Setter 추가
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
		
	
}