package doself.user.challenge.feed.service;

import java.util.List;

import doself.user.challenge.feed.domain.ChallengeFeed;

public interface ChallengeFeedService {
	// 챌린지 피드 리스트 조회
	List<ChallengeFeed> getChallengeFeedList(int page, int pageSize);
}
