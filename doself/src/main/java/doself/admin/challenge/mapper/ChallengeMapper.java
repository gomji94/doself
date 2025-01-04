package doself.admin.challenge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;

@Mapper
public interface ChallengeMapper {
	
	//챌린지 리스트 출력
	List<Challenge> getChallengeList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//챌린지내 개인기록 출력
	List<PersonalStat> getPersonalStatList(String searchType, String searchKeyword, String startDate, String endDate);

	//챌린지내 개인점수 출력
	List<PersonalScore> getPersonalScoreList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//챌린지 기록 출력
	List<Stat> getStatList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//챌린지 점수 출력
	List<Score> getScoreList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//챌린지 리더가 경고한내역 출력
	List<Warning> getWarninglist(String searchType, String searchKeyword, String startDate, String endDate);
	
	//챌린지 보상지급기록 출력
	List<Reward> getRewardList(String searchType, String searchKeyword, String startDate, String endDate);
	
}
