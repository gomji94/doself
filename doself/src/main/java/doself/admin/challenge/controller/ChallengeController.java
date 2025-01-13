package doself.admin.challenge.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;
import doself.admin.challenge.service.ChallengeService;
import doself.admin.member.domain.MemberLog;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/challenge")
@RequiredArgsConstructor
@Slf4j
public class ChallengeController {
	
	private final ChallengeService challengeService;
	
	//챌린지관리 조회
	@GetMapping("/list")
	public String getChallengeList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		var pageInfo = challengeService.getChallengeList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Challenge> challengeList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지");
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/admin-challenge-list";
	}
	
	//챌린지내 개인기록 조회
	@GetMapping("/personalstatlist")
	public String getChallengePersonalStat(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		
		var pageInfo = challengeService.getPersonalStatList(searchType, searchKeyword, startDate, endDate, pageable);
		List<PersonalStat> personalStatlist = pageInfo.getContents();		
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지내 개인기록");
		model.addAttribute("personalStatlist", personalStatlist);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		return "admin/challenge/personal-stat-list";
	}
	
	//챌린지내 개인점수 조회
	@GetMapping("/personalscorelist")
	public String getChallengePersonalScore(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		

		var pageInfo = challengeService.getPersonalScoreList(searchType, searchKeyword, startDate, endDate, pageable);
		List<PersonalScore> personalScorelist = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지내 개인점수");
		model.addAttribute("personalScorelist", personalScorelist);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/personal-score-list";
	}

	//챌린지 별 기록 조회
	@GetMapping("/statlist")
	public String getChallengeStatList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
			
		
		var pageInfo = challengeService.getStatList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Stat> statlist = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지 별 기록");
		model.addAttribute("statlist", statlist);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/stat-list";
	}
	
	//챌린지 별 점수 조회
	@GetMapping("/scorelist")
	public String getChallengeScore(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		
		var pageInfo = challengeService.getScoreList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Score> scoreList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지 별 점수");
		model.addAttribute("scoreList", scoreList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/score-list";
	}
	
	//챌린지 리더가 경고한 내역 조회
	@GetMapping("/warninglist")
	public String getWarningList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		
		var pageInfo = challengeService.getWarninglist(searchType, searchKeyword, startDate, endDate, pageable);
		List<Warning> warningList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "경고내역");
		model.addAttribute("warningList", warningList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/warning-list";
	}
	
	//챌린지 보상내역 조회
	@GetMapping("/rewardlist")
	public String getRewardList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		
		var pageInfo = challengeService.getRewardList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Reward> rewardList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "챌린지 보상내역");
		model.addAttribute("rewardList", rewardList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/challenge/reward-list";
	}
	
	// 챌린지 상태 완료이면 보상 지급
	public void everydayCheck() {
		
		challengeService.everydayCheck();
	}
}
