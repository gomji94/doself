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
	
	// 챌린지 추가(작업중)
	void addChallenge(MultipartFile files, AddChallenge addChallenge);
	
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
	
	//파일 삭제
	void deleteFile(Files fileDto);
	
	// 챌린지 멤버 추가(작업중)
	boolean addChallengeMember(AddChallengeMember addChallengeMember);
	
	// 이미 참여 중인지 확인
	boolean isAlreadyParticipated(AddChallengeMember addChallengeMember);
}
