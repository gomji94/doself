package doself.user.challenge.feed.service;

import java.util.List;
import java.util.Map;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface ChallengeFeedService {	
	// 로그인된 챌린지 멤버 아이디
	String getChallengeCodeByMemberId(String challengeMemberId);
	
	// 챌린지 피드 조회
	List<ChallengeFeed> getChallengeFeed(Map<String, Object> params);
	//FeedPageInfo<ChallengeFeed> getChallengeFeed(Map<String, Object> params);
	//FeedPageInfo<ChallengeFeed> getChallengeFeed(String challengeCode, FeedPageable pageable);
	
	// 피드 페이징
	PageInfo<ChallengeFeed> getChallengeFeedPage(String challengeCode, Pageable pageable);
	
	// 챌린지 진행 상태 조회
	List<ChallengeProgress> getProcessChallengeStatus(String challengeCode, String challengeStatus);

	// 챌린지 진행도 계산
	int calculateTotalProgress(String challengeCode);
	
	// 챌린지 참여율 상위 3명 조회
	List<ChallengeMemberList> getTopParticipants(String challengeCode);
	
	// 투데이 디데이 계산
	Map<String, String> calculateDPlusAndDMinus(String challengeCode);
	
	// 챌린지 참여 멤버 리스트 조회
	List<ChallengeMemberList> getMemberList(String challengeCode);
}
