package doself.user.challenge.list.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import doself.file.domain.Files;
import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.domain.AddChallengeMember;
import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageInfo;
import doself.util.CardPageable;

public interface ChallengeListService {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeDetailView getChallengeListView(String challengeCode);
	
	// 챌린지 생성
	void addChallenge(MultipartFile files, AddChallenge addChallenge);
	
	// 챌린지 생성 티켓 개수 조회
	int getOpeningTicketCount(String memberId);

	// 챌린지 생성 후 티켓 차감
	void decrementOpeningTicket(String memberId);
	
	// 챌린지 페이지
	CardPageInfo<ChallengeList> getChallengeList(CardPageable cardPageable);
	
	// 챌린지 이름 중복 체크
	boolean isNameDuplicate(String challengeName);
	
	// 챌린지 주제 리스트
	List<Map<String, String>> getChallengeTopicList();
	
	// 챌린지 난이도 리스트
	List<Map<String, String>> getChallengeLevelList();
	
	// 챌린지 상태 리스트
	List<Map<String, Object>> getChallengeStatusList();
	
	// 챌린지 참여 티켓 개수 조회
	int getParticipationTicketCount(String memberId);

	// 챌린지 참여 후 티켓 차감
	void decrementParticipationTicket(String memberId);
	
	// 챌린지 멤버 추가
	boolean addChallengeMember(AddChallengeMember addChallengeMember);
	
	// 이미 참여 중인지 확인
	boolean isAlreadyParticipated(AddChallengeMember addChallengeMember);
	
	// 챌린지 상태 업데이트
	void updateChallengeStatus(String challengeCode, String statusCode);
	
	// 챌린지 상태 코드 업데이트
	void updateChallengeStatuses();
	
	// 챌린지 참여 멤버수 조회
	int getCurrentMemberCount(String challengeCode);
}
