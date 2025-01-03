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
	public List<Challenge> getChallengeList() {
	
		return challengeMapper.getChallengeList();
	}
	
	//챌린지내 개인기록 출력
	@Override
	public List<PersonalStat> getPersonalStatList() {
		
		return challengeMapper.getPersonalStatList();
	}

	//챌린지내 개인점수 출력
	@Override
	public List<PersonalScore> getPersonalScoreList() {
		
		return challengeMapper.getPersonalScoreList();
	}

	//챌린지 기록 출력
	@Override
	public List<Stat> getStatList() {
		
		return challengeMapper.getStatList();
	}

	//챌린지 점수 출력
	@Override
	public List<Score> getScoreList() {
		
		return challengeMapper.getScoreList();
	}

	//챌린지 리더가 경고한 내역 출력
	@Override
	public List<Warning> getWarninglist() {
		
		return challengeMapper.getWarninglist();
	}

	//챌린지 보상지급기록 출력
	@Override
	public List<Reward> getRewardList() {
		
		return challengeMapper.getRewardList();
	}

	

}
