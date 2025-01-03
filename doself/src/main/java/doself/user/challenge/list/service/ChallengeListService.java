package doself.user.challenge.list.service;

import java.util.List;

import doself.user.challenge.list.domain.ChallengeList;

public interface ChallengeListService {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeList getChallengeListView(String ChallengeCode);

	void addChallenge(ChallengeList challengeList);
}
