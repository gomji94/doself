package doself.user.feed.service;

import java.util.List;

import doself.user.feed.domain.Feed;

public interface FeedService {
	// 피드 리스트 조회
	List<Feed> getFeedList();
}
