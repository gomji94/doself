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
import doself.util.CardPageable;

@Mapper
public interface ChallengeFeedMapper {
	// 로그인된 챌린지 멤버 아이디
	String getChallengeCodeByMemberId(String memberId);
	
	// 현재 참여중인 챌린지 리스트 조회
	//List<ParticipateChallengeList> getChallengeList();
	
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
    
    // 챌린지 피드 댓글 조회
    List<ChallengeFeedComment> getFeedCommentList(@Param("challengeFeedCode") String challengeFeedCode);
    
    // 챌린지 현재 멤버수
 	int getCurrentMemberCount(String challengeCode);
 	
 	// 챌린지 피드 생성
 	void addChallengeFeed(AddChallengeFeed addChallengeFeed);

 	// 멤버 점수 업데이트
	void updateMemberScore(String memberId, String challengeCode, double newScore);
}
