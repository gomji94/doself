package doself.admin.challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import doself.admin.member.domain.Member;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

	private final ChallengeMapper challengeMapper;
	
	 //챌린지 리스트 출력
	@Override
	public PageInfo<Challenge> getChallengeList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
			
		switch(searchType) {
			case "cgName" 	-> searchType = "cg_name";
			case "csStatus" 	-> searchType = "cs.cs_status";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntChallengeList(searchMap);		
		List<Challenge> challengeList = challengeMapper.getChallengeList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}
	
	//챌린지내 개인기록 출력
	@Override
	public PageInfo<PersonalStat> getPersonalStatList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "cgName" 	-> searchType = "cg.cg_name";
			case "mbrName" 	-> searchType = "m.mbr_name";
			case "cmssParticipationCheck " 	-> searchType = "cmss_participation_check";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntPersonalStatList(searchMap);		
		List<PersonalStat> challengeList = challengeMapper.getPersonalStatList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}

	//챌린지내 개인점수 출력
	@Override
	public PageInfo<PersonalScore> getPersonalScoreList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
		case "cgName" 	-> searchType = "cg.cg_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntPersonalScoreList(searchMap);		
		List<PersonalScore> challengeList = challengeMapper.getPersonalScoreList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}

	//챌린지 기록 출력
	@Override
	public PageInfo<Stat> getStatList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
		case "cgName" 	-> searchType = "cg.cg_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntStatList(searchMap);		
		List<Stat> challengeList = challengeMapper.getStatList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}

	//챌린지 점수 출력
	@Override
	public PageInfo<Score> getScoreList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
		case "cgName" 	-> searchType = "cg.cg_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntScoreList(searchMap);		
		List<Score> challengeList = challengeMapper.getScoreList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}

	//챌린지 리더가 경고한 내역 출력
	@Override
	public PageInfo<Warning> getWarninglist(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
		case "cgName" 	-> searchType = "cg.cg_name";
		case "mbrName" 	-> searchType = "m.mbr_name";	
		case "cmwcCategory" 	-> searchType = "cmwc.cmwc_category";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntWarninglist(searchMap);		
		List<Warning> challengeList = challengeMapper.getWarninglist(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}

	//챌린지 보상지급기록 출력
	@Override
	public PageInfo<Reward> getRewardList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
		case "cgName" 	-> searchType = "cg.cg_name";
		case "mbrName" 	-> searchType = "m.mbr_name";	
		case "reward" 	-> searchType = "CONCAT_WS(' ', rh.rh_target, rh.rh_reward_type, rh.rh_rank)";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = challengeMapper.getCntRewardList(searchMap);		
		List<Reward> challengeList = challengeMapper.getRewardList(searchMap);
		
		return new PageInfo<>(challengeList, pageable, rowCnt);
	}
	
	// 챌린지 상태 완료이면 보상지급
	public void everydayCheck() {
		
		challengeMapper.updateReward();
	}

}
