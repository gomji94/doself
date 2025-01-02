package doself.user.challenge.list.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.service.ChallengeListService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Slf4j
public class ChallengeListController {
	
	// 챌린지 리스트 서비스 → 인스턴스 생성자 메소드
	private final ChallengeListService challengeListService;
	
	// 진행중인 챌린지 리스트 조회
	@GetMapping("/list")
	// HttpServletRequest : 클라이언트가 보낸 사용자 입력 및 데이터 추출
	public String getChallengeList(HttpServletRequest request, Model model) {
		List<ChallengeList> challengeList = challengeListService.getChallengeList();
		
		model.addAttribute("currentURI", request.getRequestURI());
//		model.addAttribute("title", "챌린지 목록");
		model.addAttribute("challengeList", challengeList);
		return "user/challenge/challenge-list";
	}
	
	// 진행중인 챌린지 상세 정보 조회
	@GetMapping("/list/view")
	// HttpServletRequest : 클라이언트가 보낸 사용자 입력 및 데이터 추출
	public String getChallengeListView(@RequestParam(name="ChallengeCode") String ChallengeCode,
									   HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
//		model.addAttribute("title", "챌린지 상세 정보");
		return "user/challenge/challenge-list-view";
	}
	
	@PostMapping("/list/createchallengerequest")
	public String addChallenge(ChallengeList challengeList) {
		log.info("challengeList : {}", challengeList);
		challengeListService.addChallenge(challengeList);
		return "redirect:/user/challenge/challenge-list";
	}
	
	@PostMapping("list/view/participation")
	public String challengeParticipation(ChallengeList challengeList) {
		
		
		return "redirect:/user/challenge/challenge-list";
	}
	
}
