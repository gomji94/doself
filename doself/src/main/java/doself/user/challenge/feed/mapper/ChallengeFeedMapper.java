package doself.user.challenge.feed.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.domain.ParticipateChallengeList;

@Mapper
public interface ChallengeFeedMapper {
	// 로그인된 챌린지 멤버 아이디
	String getChallengeCodeByMemberId(String memberId);
	
	// 현재 참여중인 챌린지 리스트 조회
	List<ChallengeFeed> getChallengeList();

	// 현재 참여중인 챌린지의 피드목록 조회
	List<ChallengeFeed> getChallengeListByChallengeCode(Map<String, Object> params);
	
	// 로그인된 사용자가 참여 중인 챌린지 리스트 가져오기
	List<ParticipateChallengeList> getChallengeListByMemberId(@Param("memberId") String memberId);
	
	// 챌린지 피드
	List<ChallengeFeed> getChallengeFeed(Map<String, Object> params);
	
	// 피드 갯수 카운트
	int getChallengeFeedCount(@Param("challengeCode") String challengeCode);
	
    // 챌린지 진행 상태 조회
    List<ChallengeProgress> getChallengeProgress(@Param("challengeCode") String challengeCode);
    
	// 총 업로드 데이터 합계
    Integer getTodayProgressSum(@Param("challengeCode") String challengeCode);

	// 참여 멤버 상위 3명 표시
    List<ChallengeMemberList> getTopParticipants(@Param("challengeCode") String challengeCode);
	
	// 투데이 디데이 계산
	ChallengeProgress getChallengeProgressByCode(@Param("challengeCode") String challengeCode);
	
    // 참여 멤버 점수
    //List<ChallengeMemberList> getParticipantsWithScore(@Param("challengeCode")String challengeCode);

    // 챌린지 멤버 리스트 조회
    List<ChallengeMemberList> getMemberList(@Param("challengeCode") String challengeCode);
    
    // 챌린지 현재 멤버수
 	int getCurrentMemberCount(String challengeCode);
 	
 	// 챌린지 피드 생성
 	void addChallengeFeed(AddChallengeFeed addChallengeFeed);

 	// 멤버 점수 업데이트
	void updateMemberScore(String memberId, String challengeCode, double newScore);
	
	// 챌린지 피드 수정
	void modifyChallengeFeed(AddChallengeFeed addChallengeFeed);

	// 챌린지 피드 코드
	AddChallengeFeed getChallengeFeedByCode(Map<String, Object> params);
	
	// 챌린지 피드 삭제
	void deleteChallengeFeed(@Param("challengeFeedCode") String challengeFeedCode, @Param("memberId") String memberId);

	// 챌린지 피드 댓글 등록
	int addChallengeFeedComment(ChallengeFeedComment challengeFeedComment);
	
	// 챌린지 피드 이미지 조회
	String getFeedImage(String challengeFeedCode);
	
	// 챌린지 피드 댓글 리스트 조회
	List<ChallengeFeedComment> getFeedCommentList(@Param("challengeFeedCode") String challengeFeedCode);
	
	// 챌린지 피드 댓글 1개 조회
	String ChallengeFeedCommentView(@Param("challengeFeedCommentCode") String challengeFeedCommentCode);
	
	// 챌린지 피드 댓글 수정
	void modifyFeedComment(@Param("challengeFeedCommentCode") String challengeFeedCommentCode,
						   @Param("challengeFeedCommentContent") String challengeFeedCommentContent);
	
	// 챌린지 피드 댓글 삭제
	void deleteFeedComment(@Param("challengeFeedCommentCode") String challengeFeedCommentCode);
	
	// 챌린지 수정
	ChallengeFeed getModifyChallengeFeed(String challengeFeedCode);
	
	
	
	// 관리자
	// 멤버기록
		// 해당아이디, 해당챌린지, 해당날짜에 챌린지 피드 개수 조회 
		int getChallengeFeedCountByChallengeFeed(AddChallengeFeed challengeFeed);
		// 해당아이디, 해당챌린지, 해당날짜에 챌린지 개인기록 개수 조회 
		int isDataMemberStat(AddChallengeFeed challengeFeed);
		// 피드난이도별 개수 가져오기
		int getTopicLevel(String cgNum);
		// 챌린지 피드가 한개도 존재 하지 않을시 개인기록 insert
		void createPersonalStat(Map<String, Object> searchMap);
		// 챌린지 피드가 한개이상 존재할시 업데이트
		int updatePersonalStat(Map<String, Object> searchMap);
		
		
		// 멤버점수
		// 해당멤버의 누적 달성률 추출
		double getAchievementRate(AddChallengeFeed challengeFeed);
		// 해당멤버의 누적 참여율 추출
		double getParticipationRate(AddChallengeFeed challengeFeed);
		// 챌린지 보상기준에 따른 달성률 점수 추출
		int getAchievementScore(double achievementScore);
		// 챌린지 보상기준에 따른 참여율 점수 추출
		int getParticipationScore(double participationRate);
		// 해당아이디의 해당챌린지의 챌린지개인점수 데이터가 있는지 조회
		int isDataMemberScore(Map<String, Object> memberScoreMap);
		// 해당아이디의 해당챌린지의 챌린지 개인점수 데이터가 없으면 insert
		void createPersonalScore(Map<String, Object> memberScoreMap);
		// 해당아이디의 해당챌린지의 챌린지 개인점수 데이터가 있으면 update
		int updatePersonalScore(Map<String, Object> memberScoreMap);
		// 챌린지 개인점수 랭킹 업데이트
		int updateMemberScoreRank();
		
		
		// 챌린지 기록
		// 해당날짜 해당챌린지의 데이터가 있는지 조회
		int isDataChallengeStat(AddChallengeFeed challengeFeed);
		// 해당날짜 해당챌린지의 일일참여율 조회
		double getChallengeParticipationRate(AddChallengeFeed challengeFeed); 
		// 해당날짜 해당챌린지의 일일달성률 조회
		double getChallengeAchievementRate(AddChallengeFeed challengeFeed);
		// 데이터가 없으면 insert
		void createChallengeStat(Map<String, Object> challengeStatMap);
		// 데이터가 있으면 update
		int updateChallengeStat(Map<String, Object> challengeStatMap);
		
		
		// 챌린지 점수
		// 해당챌린지의 데이터가 있는지 조회
		int isDataChallengeScore(AddChallengeFeed challengeFeed);
		// 챌린지 난이도 가져오기
		String getChallengeLevel(AddChallengeFeed challengeFeed);
		// 챌린지 보상기준에 따른 난이도점수
		int getChallengeLevelScore(String challengeLevel);
		// 해당챌린지의 누적달성률 출력
		double getChallengeCumulativeAchievementRate(AddChallengeFeed challengeFeed);
		// 해당챌린지의 누적참여율 출력
		double getChallengeCumulativeParticipationRate(AddChallengeFeed challengeFeed);
		// 챌린지보상기준에 따른 누적달성률 점수
		int getChallengeAchievementScore(double challengeCumulativeAchievementRate);
		// 챌린지보상기준에 따른 누적참여율 점수
		int getChallengeParticipationScore(double challengeCumulativeParticipationRate);
		// 데이터가 존재하지 않으면 insert
		void createChallengeScore(Map<String, Object> challengeScoreMap);
		// 데이터가 존재하면 update
		int updateChallengeScore(Map<String, Object> challengeScoreMap);
		// 랭킹 update
		int updateChallengeScoreRank();
		
		// 퇴장당하지 않은 멤버중 경고 3번받은 멤버 조회
		List<Map<String, Object>> getAccumulatedWarningMember();
		// 챌린지 퇴장 처리
		void createChallengeMemberCsNum(Map<String, Object> e);
}
