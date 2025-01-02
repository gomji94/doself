package doself.user.challenge.feed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class ChallengeFeedController {
	
	// 챌린지 피드 조회
	@GetMapping("/challengeview")
	public String getChallengeView(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 피드");
		return "user/challenge/challenge-view";
	}
	
	// 챌린지 멤버 리스트 조회
	@GetMapping("/memberlist")
	public String getMemberList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "멤버 리스트");
		return "user/challenge/member-list";
	}
	
	// 챌린지 멤버 경고 내역 조회
	@GetMapping("/warning")
	public String getMemberWarnig(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "멤버 경고");
		return "user/challenge/challenge-member-warning";
	}
	
	// 챌린지 피드 댓글 조회
	@GetMapping("/feedcomment")
	public String getFeedComment(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 피드 댓글");
		return "user/challenge/feed-comment";
	}
	
	// 챌린지 생성 화면 조회
	@GetMapping("/createchallenge")
	public String getCreateChallenge(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 생성");
		return "user/challenge/create-challenge";
	}
	
	// 챌린지 피드 수정 화면 조회
	@GetMapping("/modifychallengefeed")
	public String getModifyChallengeFeed(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 피드 수정");
		return "user/challenge/modify-challenge-feed";
	}
}
