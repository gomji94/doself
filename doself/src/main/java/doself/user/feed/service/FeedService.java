package doself.user.feed.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import doself.user.feed.domain.Feed;

public interface FeedService {
	// 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 특정 피드 상세 조회
	Feed getFeedDetail(String feedCode);
	
	// 피드 추가
	void addFeed(Feed feed,  MultipartFile feedPicture);

	// 자동완성 검색
	List<String> findKeywords(String query);
	
	// 음식이름 조회
	String getOrCreateMealNutritionInfo(String mealName);
}
