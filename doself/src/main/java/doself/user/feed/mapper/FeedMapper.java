package doself.user.feed.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import doself.admin.declare.domain.Declare;
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
    
    // 피드 댓글 삭제
    void deleteFeedComments(String feedCode);
    
    // 피드 이미지 파일 삭제
    void deleteFeedFiles(String feedCode);
    
    // 하루 먹은 영양 정보 비교 테이블 외래키 삭제
    void deleteDailyNutritionalIntakeComparison(String feedCode);
    
    // 하루 먹은 영양 정보 테이블 외래키 삭제
    void deletedDilyNutritionalIntakeInfo(String feedCode);
    
    // 피드 삭제
	void deleteFeed(String feedCode, String memberId);
    
    // 피드 댓글 추가
	int addFeedComment(Feed comment);

	// 피드 댓글 조회
	List<Feed> getFeedCommentList(@Param("feedCode") String feedCode);
	
	// 피드 댓글 수정
	int modifyFeedComment(@Param("feedCommentCode") String feedCommentCode,
						   @Param("feedCommentContent") String feedCommentContent);
		
	// 피드 댓글 삭제
	int deleteFeedComment(@Param("feedCommentCode") String feedCommentCode);
	
	// 피드 신고
	void insertReportRequest(Declare declare);
	
	// 하루 먹은 영양 정보 조회
	/*
	 * List<DailyNutritionalIntakeInfo> getNutritionalInfoByDate(Map<String, Object>
	 * params);
	 */
}
