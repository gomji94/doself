package doself.admin.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/challenge")
public class ChallengeController {
	
	//챌린지관리 조회
	@GetMapping("/list")
	public String getChallengeList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지");
		return "admin/challenge/admin-challenge-list";
	}
	
	//챌린지내 개인기록 조회
	@GetMapping("/personalstatlist")
	public String getChallengePersonalStat(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인기록");
		return "admin/challenge/personal-stat-list";
	}
	
	//챌린지내 개인점수 조회
	@GetMapping("/personalscorelist")
	public String getChallengePersonalScore(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인점수");
		return "admin/challenge/personal-score-list";
	}

	//챌린지 별 기록 조회
	@GetMapping("/statlist")
	public String getChallengeStatList(Model model) {

		model.addAttribute("title", "챌린지 별 기록");
		return "admin/challenge/stat-list";
	}
	
	//챌린지 별 점수 조회
	@GetMapping("/scorelist")
	public String getChallengeScore(Model model) {

		model.addAttribute("title", "챌린지 별 점수");
		return "admin/challenge/score-list";
	}
	
	//챌린지 리더가 경고한 내역 조회
	@GetMapping("/warninglist")
	public String getWarningList(Model model) {

		model.addAttribute("title", "경고내역");
		return "admin/challenge/warning-list";
	}
	
	//챌린지 보상내역 조회
	@GetMapping("/rewardlist")
	public String getRewardList(Model model) {

		model.addAttribute("title", "챌린지 보상내역");
		return "admin/challenge/reward-list";
	}
}
