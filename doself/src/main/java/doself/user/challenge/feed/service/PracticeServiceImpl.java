package doself.user.challenge.feed.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.common.mapper.CommonMapper;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.mapper.PracticeMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PracticeServiceImpl implements PracticeService {
	
	private final PracticeMapper practiceMapper;
	private final CommonMapper commonMapper;
	
	//챌린지 피드 생성
	@Override
	public void createChallengeFeed(ChallengeFeed challengeFeed) {

		
		// 챌린지 피드생성
		
		
		
		
		
		
		// ...(*￣０￣)ノ챌린지내 개인기록	...(*￣０￣)ノ
		
		// 챌린지 피드생성시 실행
		// 해당아이디의 해당챌린지의 해당날짜에 데이터의 수(챌린지피드 수) 조회
		int challengeFeedCount = practiceMapper.getChallengeFeedCountByChallengeFeed(challengeFeed);
		
		// 챌린지내 개인기록 해당아이디, 해당챌린지, 해당날짜에 데이터가 있는지 조회
		int isDataMemberStat = practiceMapper.isDataMemberStat(challengeFeed);
		
		// 챌린지 번호
		String cgNum = challengeFeed.getChallengeCode();
		
		// 해당 챌린지의 난이도 별 개수 확인
		int topicLevel =  practiceMapper.getTopicLevel(cgNum);
		// 개인기록 키생성
		String memberStatKey = commonMapper.getPrimaryKey("cmss_", "challenge_member_score_stats", "cmss_num");
		
		Map<String, Object> memberStatMap = new HashMap<String, Object>();
		memberStatMap.put("challengeFeed", challengeFeed);
		memberStatMap.put("newKey", memberStatKey);
		memberStatMap.put("topicLevel", topicLevel);
		memberStatMap.put("challengeFeedCount", challengeFeedCount);
		
		// 챌린지내 개인기록 한개도 존재하지 않을시 insert
		if(isDataMemberStat == 0) {	
			
			practiceMapper.createPersonalStat(memberStatMap);
		}
		//한개이상 존재할때 update
		else {
			
			practiceMapper.updatePersonalStat(memberStatMap);			
		}
		
		
		
		// ...(*￣０￣)ノ챌린지 개인 점수...(*￣０￣)ノ
		
		// 년도,월 분리
		Date date = challengeFeed.getChallengeFeedDate();
		// Calendar 객체로 날짜 처리
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 년도와 월 추출
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		// 해당멤버의 누적달성률 추출
		double achievementRate = practiceMapper.getAchievementRate(challengeFeed);
		// 해당멤버의 누적참여율 추출
		double participationRate = practiceMapper.getParticipationRate(challengeFeed);
		// 챌린지 보상기준에 따른 달성률 점수 추출
		int achievementScore = practiceMapper.getAchievementScore(achievementRate);
		// 챌린지 보상기준에 따른 참여율 점수 추출
		int participationScore = practiceMapper.getParticipationScore(participationRate);
		
		
		Map<String, Object> memberScoreMap = new HashMap<String, Object>();
		memberScoreMap.put("challengeFeed", challengeFeed);
		memberScoreMap.put("year", year);
		memberScoreMap.put("month", month);
		memberScoreMap.put("achievementRate", achievementRate);
		memberScoreMap.put("participationRate", participationRate);
		memberScoreMap.put("achievementScore", achievementScore);
		memberScoreMap.put("participationScore", participationScore);
		
		// 해당아이디의 해당챌린지의 챌린지개인점수 데이터가 있는지 조회
		int isDataMemberScore = practiceMapper.isDataMemberScore(memberScoreMap);
		
		// 챌린지 개인점수 키 생성
		String memberScoreKey = commonMapper.getPrimaryKey("cpsl_", "challenge_personal_score_list", "cpsl_num");
		memberScoreMap.put("memberScoreKey", memberScoreKey);
		
		// 데이터가 없으면 insert
		if(isDataMemberScore == 0) {
			
			practiceMapper.createPersonalScore(memberScoreMap);
		}
		// 데이터가 있으면 update
		else {
			
			practiceMapper.updatePersonalScore(memberScoreMap);
		}		
		// 랭킹 update
		practiceMapper.updateMemberScoreRank();
		
		
		
		// ...(*￣０￣)ノ챌린지 별 기록 ...(*￣０￣)ノ
		
		// 해당날짜 해당챌린지의 데이터가 있는지 조회
		int isDataChallengeStat = practiceMapper.isDataChallengeStat(challengeFeed);
		
		// 해당날짜 해당챌린지의 일일참여율 조회
		double challengeParticipationRate = practiceMapper.getChallengeParticipationRate(challengeFeed); 
		// 해당날짜 해당챌린지의 일일당설률 조회
		double challengeAchievementRate = practiceMapper.getChallengeAchievementRate(challengeFeed);
		
		Map<String, Object> challengeStatMap = new HashMap<String, Object>();
		
		challengeStatMap.put("challengeFeed", challengeFeed);
		challengeStatMap.put("challengeParticipationRate", challengeParticipationRate);
		challengeStatMap.put("challengeAchievementRate", challengeAchievementRate);
		
		// 없으면 insert
		if(isDataChallengeStat == 0) {
			//챌린지 기록 키생성
			String challengeStatKey = commonMapper.getPrimaryKey("ctps_", "challenge_today_participation_stats", "ctps_num");
			challengeStatMap.put("challengeStatKey", challengeStatKey);
			
			practiceMapper.createChallengeStat(challengeStatMap);
		}
		// 있으면 update
		else {
			
			practiceMapper.updateChallengeStat(challengeStatMap);
		}
		
		
		
		
		// ...(*￣０￣)ノ챌린지 점수...(*￣０￣)ノ
		
		Map<String, Object> challengeScore = new HashMap<String, Object>();
		
		// 해당챌린지의 데이터가 있는지 조회
		int isDataChallengeScore = practiceMapper.isDataChallengeScore(challengeFeed);
		
		// 챌린지 난이도 가져오기
		String challengeLevel = practiceMapper.getChallengeLevel(challengeFeed);
		// 챌린지보상기준에 따른 난이도 점수
		int challengeLevelScore = practiceMapper.getChallengeLevelScore(challengeLevel);
		// 해당챌린지의 누적달성률 추출
		double challengeCumulativeAchievementRate = practiceMapper.getChallengeCumulativeAchievementRate(challengeFeed);
		// 해당챌린지의 누적참여율 추출
		double challengeCumulativeParticipationRate = practiceMapper.getChallengeCumulativeParticipationRate(challengeFeed);
		// 챌린지보상기준에 따른 누적달성률 점수
		int challengeAchievementScore = practiceMapper.getChallengeAchievementScore(challengeCumulativeAchievementRate);
		// 챌린지보상기준에 따른 누적참여율 점수
		int challengeParticipationScore = practiceMapper.getChallengeParticipationScore(challengeCumulativeParticipationRate);
		
		challengeScore.put("challengeFeed", challengeFeed);
		challengeScore.put("year", year);
		challengeScore.put("month", month);
		challengeScore.put("challengeLevel", challengeLevel);
		challengeScore.put("challengeLevelScore", challengeLevelScore);
		challengeScore.put("challengeCumulativeAchievementRate", challengeCumulativeAchievementRate);
		challengeScore.put("challengeCumulativeParticipationRate", challengeCumulativeParticipationRate);
		challengeScore.put("challengeAchievementScore", challengeAchievementScore);
		challengeScore.put("challengeParticipationScore", challengeParticipationScore);
		
		
		// 데이터가 존재하지 않으면 insert
		if(isDataChallengeScore == 0) {
			//챌린지점수 기본키 생성
			String challengeScoreKey = commonMapper.getPrimaryKey("mcsl_", "monthly_challenge_score_list", "mcsl_num");
			challengeScore.put("memberScoreKey", challengeScoreKey);
			
			practiceMapper.createChallengeScore(challengeScore);
		}
		// 데이터가 존재하면 update
		else {
			
			practiceMapper.updateChallengeScore(challengeScore);
		}
		
		// 랭킹 update
		practiceMapper.updateChallengeScoreRank();
		
		
		// ...(*￣０￣)ノ리더가 경고한 내역...(*￣０￣)ノ (리더가 경고누를 때 판별)
		
		// 해당챌린지 해당멤버 경고 3번받으면 해당챌린지 퇴장
		
		// 퇴장당하지 않은 멤버중 경고 3번받은 멤버 조회
		List<Map<String, Object>> accumulatedWarningMember = practiceMapper.getAccumulatedWarningMember();
		
		// 챌린지 퇴장 처리 (insert)
		for(Map<String, Object> e : accumulatedWarningMember) {
			String cgmNumKey = commonMapper.getPrimaryKey("cgm_", "challenge_group_member", "cgm_num");
			e.put("cgmNumKey", cgmNumKey);
			
			practiceMapper.createChallengeMemberCsNum(e);
		}
	}
}
