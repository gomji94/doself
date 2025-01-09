package doself.admin.challenge.service;

import java.util.List;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface ChallengeService {
	
	//챌린지 리스트 출력
	PageInfo<Challenge> getChallengeList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//챌린지내 개인기록 출력
	PageInfo<PersonalStat> getPersonalStatList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);

	//챌린지내 개인점수 출력
	PageInfo<PersonalScore> getPersonalScoreList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//챌린지 기록 출력
	PageInfo<Stat> getStatList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//챌린지 점수 출력
	PageInfo<Score> getScoreList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//챌린지 리더가 경고한 내역 출력
	PageInfo<Warning> getWarninglist(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//챌린지 보상지급기록 출력
	PageInfo<Reward> getRewardList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
}
