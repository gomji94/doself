package doself.user.challenge.feed.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.challenge.feed.domain.ChallengeFeed;

@Mapper
public interface PracticeMapper {

	// 멤버기록
	// 해당아이디, 해당챌린지, 해당날짜에 챌린지 피드 개수 조회 
	int getChallengeFeedCountByChallengeFeed(ChallengeFeed challengeFeed);
	// 해당아이디, 해당챌린지, 해당날짜에 챌린지 개인기록 개수 조회 
	int isDataMemberStat(ChallengeFeed challengeFeed);
	// 피드난이도별 개수 가져오기
	int getTopicLevel(String cgNum);
	// 챌린지 피드가 한개도 존재 하지 않을시 개인기록 insert
	void createPersonalStat(Map<String, Object> searchMap);
	// 챌린지 피드가 한개이상 존재할시 업데이트
	int updatePersonalStat(Map<String, Object> searchMap);
	
	
	// 멤버점수
	// 해당멤버의 누적 달성률 추출
	double getAchievementRate(ChallengeFeed challengeFeed);
	// 해당멤버의 누적 참여율 추출
	double getParticipationRate(ChallengeFeed challengeFeed);
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
	int isDataChallengeStat(ChallengeFeed challengeFeed);
	// 해당날짜 해당챌린지의 일일참여율 조회
	double getChallengeParticipationRate(ChallengeFeed challengeFeed); 
	// 해당날짜 해당챌린지의 일일달성률 조회
	double getChallengeAchievementRate(ChallengeFeed challengeFeed);
	// 데이터가 없으면 insert
	void createChallengeStat(Map<String, Object> challengeStatMap);
	// 데이터가 있으면 update
	int updateChallengeStat(Map<String, Object> challengeStatMap);
	
	
	// 챌린지 점수
	// 해당챌린지의 데이터가 있는지 조회
	int isDataChallengeScore(ChallengeFeed challengeFeed);
	// 챌린지 난이도 가져오기
	String getChallengeLevel(ChallengeFeed challengeFeed);
	// 챌린지 보상기준에 따른 난이도점수
	int getChallengeLevelScore(String challengeLevel);
	// 해당챌린지의 누적달성률 출력
	double getChallengeCumulativeAchievementRate(ChallengeFeed challengeFeed);
	// 해당챌린지의 누적참여율 출력
	double getChallengeCumulativeParticipationRate(ChallengeFeed challengeFeed);
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
