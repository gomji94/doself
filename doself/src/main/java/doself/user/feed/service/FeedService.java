package doself.user.feed.service;

import java.util.List;

import doself.user.feed.domain.Feed;
import doself.user.feed.mapper.FeedMapper;

public interface FeedService {
	// 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 특정 피드 상세 조회
	Feed getFeedDetail(String feedCode);
	
	// 피드 추가
	void addFeed(Feed feed);
	
	// 자동완성 검색
}
