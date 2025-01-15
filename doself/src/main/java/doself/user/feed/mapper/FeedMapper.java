package doself.user.feed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.feed.domain.Feed;
import doself.user.feed.domain.MealNutritionInfo;

@Mapper
public interface FeedMapper {
	// 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 특정 피드 상세 조회
	Feed getFeedDetail(String feedCode);

	// 피드 추가
	int addFeed(Feed feed);
	
	// 자동완성 검색
	List<String> findKeywords(String query);
	
	// 음식 이름으로 음식 번호 조회
	String findByName(String mealName);

    // 새 음식 추가
    void addMeal(MealNutritionInfo mealNutritionInfo);
}
