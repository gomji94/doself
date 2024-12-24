package doself.admin.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/challenge")
public class ChallengeController {
	
	//챌린지관리
	@GetMapping("/list")
	public String challengeList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지");
		return "admin/challenge/admin-challenge-list";
	}
	
	//챌린지내 개인기록
	@GetMapping("/personalstatlist")
	public String challengePersonalStat(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인기록");
		return "admin/challenge/personal-stat-list";
	}
	
	//챌린지내 개인점수
	@GetMapping("/personalscorelist")
	public String challengePersonalScore(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지내 개인점수");
		return "admin/challenge/personal-score-list";
	}

	//챌린지 별 기록
	@GetMapping("/statlist")
	public String challengeStatList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 별 기록");
		return "admin/challenge/stat-list";
	}
	
	//챌린지 별 점수
	@GetMapping("/scorelist")
	public String challengeScore(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 별 점수");
		return "admin/challenge/score-list";
	}
	
	//챌린지 리더가 경고한 내역
	@GetMapping("/warninglist")
	public String warningList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "경고내역");
		return "admin/challenge/warning-list";
	}
	
	//챌린지 보상내역
		@GetMapping("/rewardlist")
		public String rewardList(HttpServletRequest request, Model model) {
			
			model.addAttribute("currentURI", request.getRequestURI());
			model.addAttribute("title", "챌린지 보상내역");
			return "admin/challenge/reward-list";
		}
}
