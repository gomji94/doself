package doself.user.challenge.list.service;

import java.util.List;

import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageInfo;
import doself.util.CardPageable;
import doself.util.PageInfo;

public interface ChallengeListService {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeList getChallengeListView(String ChallengeCode);
	
	// 챌린지 추가(작업중)
	void addChallenge(ChallengeList challengeList);
	
	// 챌린지 페이지
	CardPageInfo<ChallengeList> getChallengeList(CardPageable cardPageable);
}
