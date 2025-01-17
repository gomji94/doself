package doself.user.challenge.list.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.domain.AddChallengeMember;
import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageable;

@Mapper
public interface ChallengeListMapper {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeDetailView selectChallengeDetail(String challengeCode);
	//List<ChallengeDetailView> getChallengeListView(@Param("challengeCode") String challengeCode);

	// 챌린지 추가(작업중)
	void addChallenge(AddChallenge addChallenge);
	
	// 멤버 추가(작업중)
	void addChallengeMember(AddChallengeMember addChallengeMember);
	
	// 특정 챌린지 멤버 조회(중복 체크)
	List<AddChallengeMember> getChallengeMembers(String challengeCode);
	
	// 챌린지 페이지
	List<ChallengeList> getChallengeList(CardPageable cardPageable);
	
	// 챌린지 총 갯수
	int getCntChallengeList();
	
	// 챌린지명 중복 체크
	boolean isNameDuplicate(String challengeName);
	
	// 챌린지 생성 조회
	List<AddChallenge> addChallengeList();
	
	// 챌린지 주제 리스트
	List<Map<String, String>> getChallengeTopicList();
	
	// 챌린지 난이도 리스트
	List<Map<String, String>> getChallengeLevelList();
	
	// 챌린지 상태 리스트
	List<Map<String, Object>> getChallengeStatusList();
	
	// 챌린지 상태 코드 조회
	String selectChallengeStatus(AddChallengeMember addChallengeMember);
}
