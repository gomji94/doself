package doself.user.challenge.list.service;

import java.util.List;

import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageInfo;
import doself.util.CardPageable;

public interface ChallengeListService {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeDetailView getChallengeListView(String challengeCode);
	//List<ChallengeDetailView> getChallengeListView(String challengeCode);
	
	// 챌린지 추가(작업중)
	void addChallenge(ChallengeList challengeList);
	
	// 챌린지 페이지
	CardPageInfo<ChallengeList> getChallengeList(CardPageable cardPageable);
	
	// 챌린지 이름 중복 체크
	boolean isNameDuplicate(String challengeName);
}
