package doself.admin.challenge.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.RewardHistory;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;

@Mapper
public interface ChallengeMapper {
	
	//챌린지 리스트 출력
	List<Challenge> getChallengeList(Map<String, Object> searchMap);
	//챌린지 갯수
	int getCntChallengeList(Map<String, Object> searchMap);
	
	//챌린지내 개인기록 출력
	List<PersonalStat> getPersonalStatList(Map<String, Object> searchMap);
	//챌린지 개인기록 갯수
	int getCntPersonalStatList(Map<String, Object> searchMap);
	
	//챌린지내 개인점수 출력
	List<PersonalScore> getPersonalScoreList(Map<String, Object> searchMap);
	//챌린지 개인점수 갯수
	int getCntPersonalScoreList(Map<String, Object> searchMap);
	
	//챌린지 기록 출력
	List<Stat> getStatList(Map<String, Object> searchMap);
	//챌린지 개인기록 갯수
	int getCntStatList(Map<String, Object> searchMap);
	
	//챌린지 점수 출력
	List<Score> getScoreList(Map<String, Object> searchMap);
	//챌린지 점수 갯수
	int getCntScoreList(Map<String, Object> searchMap);
	
	//챌린지 리더가 경고한내역 출력
	List<Warning> getWarninglist(Map<String, Object> searchMap);
	//챌린지 리더가 경고한내역 갯수
	int getCntWarninglist(Map<String, Object> searchMap);
		
	//챌린지 보상지급기록 출력
	List<Reward> getRewardList(Map<String, Object> searchMap);
	//챌린지 보상지급 갯수
	int getCntRewardList(Map<String, Object> searchMap);
	
	//챌린지 상태 > 완료, 보상지급 지급x인 리스트 반환
	List<Challenge> getNoRewardList();
	//챌린지 상태 완료이면 보상지급 (보상지급여부 업데이트)
	int updateReward(String cgNum);
	
	//챌린지 보상받을 멤버와 얼마 받아야하는지 가져오기
	List<Map<String, Object>> getRewardMember(String cgNum);
	
	//포인트 지급
	int updateMemberReward(String mbrId, int rhPoint);
	//보상지급 기록 인서트
	void createRewardPaymentHistory(String newKey,String mbrId,int rhPoint,String cgNum);
	
	// 보상받을 챌린지와 얼마받아야하는지 가져오기
	List<Map<String, Object>> getRewardChallenge(Map<String, Object> searchMap);
	
	// 보상받을 챌린지의 멤버 가져오기
	List<String> getChallengeMemberList(String cgNum);
	// 보상 지급
	int updateChallengeMemberReward(Map<String, Object> rewardMember);
}
