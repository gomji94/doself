package doself.user.challenge.feed.service;

import java.util.List;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProcess;
import doself.user.challenge.list.domain.ChallengeList;

public interface ChallengeFeedService {
	// 챌린지 피드 리스트 조회
	List<ChallengeFeed> getChallengeFeedList(int page, int pageSize);
	
	// 챌린지 진행 상태 조회
	List<ChallengeProcess> getChallengeProgress(String challengeCode);
	
	// 챌린지 참여 멤버 리스트 조회
	List<ChallengeMemberList> getMemberList(String challengeCode);
}
