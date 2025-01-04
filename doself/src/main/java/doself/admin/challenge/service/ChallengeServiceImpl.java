package doself.admin.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;
import doself.admin.challenge.mapper.ChallengeMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

	private final ChallengeMapper challengeMapper;
	
	 //챌린지 리스트 출력
	@Override
	public List<Challenge> getChallengeList(String searchType, String searchKeyword, String startDate, String endDate) {
	
		return challengeMapper.getChallengeList(searchType, searchKeyword, startDate, endDate);
	}
	
	//챌린지내 개인기록 출력
	@Override
	public List<PersonalStat> getPersonalStatList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getPersonalStatList(searchType, searchKeyword, startDate, endDate);
	}

	//챌린지내 개인점수 출력
	@Override
	public List<PersonalScore> getPersonalScoreList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getPersonalScoreList(searchType, searchKeyword, startDate, endDate);
	}

	//챌린지 기록 출력
	@Override
	public List<Stat> getStatList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getStatList(searchType, searchKeyword, startDate, endDate);
	}

	//챌린지 점수 출력
	@Override
	public List<Score> getScoreList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getScoreList(searchType, searchKeyword, startDate, endDate);
	}

	//챌린지 리더가 경고한 내역 출력
	@Override
	public List<Warning> getWarninglist(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getWarninglist(searchType, searchKeyword, startDate, endDate);
	}

	//챌린지 보상지급기록 출력
	@Override
	public List<Reward> getRewardList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return challengeMapper.getRewardList(searchType, searchKeyword, startDate, endDate);
	}

	

}
