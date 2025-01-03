package doself.admin.challenge.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.admin.challenge.domain.Challenge;
import doself.admin.challenge.domain.PersonalScore;
import doself.admin.challenge.domain.PersonalStat;
import doself.admin.challenge.domain.Reward;
import doself.admin.challenge.domain.Score;
import doself.admin.challenge.domain.Stat;
import doself.admin.challenge.domain.Warning;
import doself.admin.challenge.service.ChallengeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/challenge")
@RequiredArgsConstructor
public class ChallengeController {
	
	private final ChallengeService challengeService;
	
	//챌린지관리 조회
	@GetMapping("/list")
	public String getChallengeList(HttpServletRequest request, Model model) {
		
		List<Challenge> challengeList = challengeService.getChallengeList();
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지");
		model.addAttribute("challengeList", challengeList);
		return "admin/challenge/admin-challenge-list";
	}
	
	//챌린지내 개인기록 조회
	@GetMapping("/personalstatlist")
	public String getChallengePersonalStat(HttpServletRequest request, Model model) {
		
		List<PersonalStat> personalStatlist = challengeService.getPersonalStatList();
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인기록");
		model.addAttribute("personalStatlist", personalStatlist);
		return "admin/challenge/personal-stat-list";
	}
	
	//챌린지내 개인점수 조회
	@GetMapping("/personalscorelist")
	public String getChallengePersonalScore(HttpServletRequest request, Model model) {
		
		List<PersonalScore> personalScorelist = challengeService.getPersonalScoreList();
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인점수");
		model.addAttribute("personalScorelist", personalScorelist);
		return "admin/challenge/personal-score-list";
	}

	//챌린지 별 기록 조회
	@GetMapping("/statlist")
	public String getChallengeStatList(Model model) {
		
		List<Stat> statlist = challengeService.getStatList();
		model.addAttribute("title", "챌린지 별 기록");
		model.addAttribute("statlist", statlist);
		return "admin/challenge/stat-list";
	}
	
	//챌린지 별 점수 조회
	@GetMapping("/scorelist")
	public String getChallengeScore(Model model) {

		List<Score> scoreList = challengeService.getScoreList();
		model.addAttribute("title", "챌린지 별 점수");
		model.addAttribute("scoreList", scoreList);
		return "admin/challenge/score-list";
	}
	
	//챌린지 리더가 경고한 내역 조회
	@GetMapping("/warninglist")
	public String getWarningList(Model model) {

		List<Warning> warningList = challengeService.getWarninglist();
		model.addAttribute("title", "경고내역");
		model.addAttribute("warningList", warningList);
		return "admin/challenge/warning-list";
	}
	
	//챌린지 보상내역 조회
	@GetMapping("/rewardlist")
	public String getRewardList(Model model) {

		List<Reward> rewardList = challengeService.getRewardList();
		model.addAttribute("title", "챌린지 보상내역");
		model.addAttribute("rewardList", rewardList);
		return "admin/challenge/reward-list";
	}
}
