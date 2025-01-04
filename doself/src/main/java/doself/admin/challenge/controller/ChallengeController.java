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
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/challenge")
@RequiredArgsConstructor
public class ChallengeController {
	
	private final ChallengeService challengeService;
	
	//챌린지관리 조회
	@GetMapping("/list")
	public String getChallengeList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<Challenge> challengeList = challengeService.getChallengeList(searchType, searchKeyword, startDate, endDate);

		model.addAttribute("title", "챌린지");
		model.addAttribute("challengeList", challengeList);
		return "admin/challenge/admin-challenge-list";
	}
	
	//챌린지내 개인기록 조회
	@GetMapping("/personalstatlist")
	public String getChallengePersonalStat(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<PersonalStat> personalStatlist = challengeService.getPersonalStatList(searchType, searchKeyword, startDate, endDate);

		model.addAttribute("title", "챌린지내 개인기록");
		model.addAttribute("personalStatlist", personalStatlist);
		return "admin/challenge/personal-stat-list";
	}
	
	//챌린지내 개인점수 조회
	@GetMapping("/personalscorelist")
	public String getChallengePersonalScore(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<PersonalScore> personalScorelist = challengeService.getPersonalScoreList(searchType, searchKeyword, startDate, endDate);

		model.addAttribute("title", "챌린지내 개인점수");
		model.addAttribute("personalScorelist", personalScorelist);
		return "admin/challenge/personal-score-list";
	}

	//챌린지 별 기록 조회
	@GetMapping("/statlist")
	public String getChallengeStatList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<Stat> statlist = challengeService.getStatList(searchType, searchKeyword, startDate, endDate);
		model.addAttribute("title", "챌린지 별 기록");
		model.addAttribute("statlist", statlist);
		return "admin/challenge/stat-list";
	}
	
	//챌린지 별 점수 조회
	@GetMapping("/scorelist")
	public String getChallengeScore(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<Score> scoreList = challengeService.getScoreList(searchType, searchKeyword, startDate, endDate);
		model.addAttribute("title", "챌린지 별 점수");
		model.addAttribute("scoreList", scoreList);
		return "admin/challenge/score-list";
	}
	
	//챌린지 리더가 경고한 내역 조회
	@GetMapping("/warninglist")
	public String getWarningList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<Warning> warningList = challengeService.getWarninglist(searchType, searchKeyword, startDate, endDate);
		model.addAttribute("title", "경고내역");
		model.addAttribute("warningList", warningList);
		return "admin/challenge/warning-list";
	}
	
	//챌린지 보상내역 조회
	@GetMapping("/rewardlist")
	public String getRewardList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<Reward> rewardList = challengeService.getRewardList(searchType, searchKeyword, startDate, endDate);
		model.addAttribute("title", "챌린지 보상내역");
		model.addAttribute("rewardList", rewardList);
		return "admin/challenge/reward-list";
	}
}
