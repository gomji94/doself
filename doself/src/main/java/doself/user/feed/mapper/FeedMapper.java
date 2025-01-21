package doself.user.feed.mapper;

import java.util.List;
import java.util.Map;

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

    // 새 음식 추가
    void addMeal(MealNutritionInfo mealNutritionInfo);
    
    // 좋아요 추가
    void incrementLike(String feedNum);

    // 좋아요 제거
    void decrementLike(String feedNum);
    
    // 피드 수정
    int modifyFeed(Feed feed);
    
    // 피드 코드 조회
    Feed getFeedByCode(Map<String, Object> params);
    
    // 피드 댓글 추가
}
