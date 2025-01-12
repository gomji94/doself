package doself.user.challenge.list.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.service.ChallengeListService;
import doself.util.CardPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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
	public String getChallengeList(CardPageable cardPageable, Model model) {
		var challengeListPageInfo = challengeListService.getChallengeList(cardPageable);
		//List<ChallengeList> challengeList = challengeListService.getChallengeList();
		List<ChallengeList> challengeList = challengeListPageInfo.getContents();
		int currentPage = challengeListPageInfo.getCurrentPage();
		int startPageNum = challengeListPageInfo.getStartPageNum();
		int endPageNum = challengeListPageInfo.getEndPageNum();
		int lastPage = challengeListPageInfo.getLastPage();
		
		log.info("Fetched Challenge List: {}", challengeList);
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		return "user/challenge/challenge-list";
	}
	
	// 진행중인 챌린지 상세 정보 조회
	@GetMapping("/list/view")
	@ResponseBody
	// HttpServletRequest : 클라이언트가 보낸 사용자 입력 및 데이터 추출
	public ChallengeDetailView getChallengeListView(@RequestParam("challengeCode") String challengeCode) {
        log.info("Challenge Code: {}", challengeCode);
        ChallengeDetailView challengeDetail = challengeListService.getChallengeListView(challengeCode);
        log.info("Challenge Detail: {}", challengeDetail);
        return challengeDetail;
    }
	
	@PostMapping("/list/createchallengerequest")
	public String addChallenge(ChallengeList challengeList) {
		log.info("challengeList : {}", challengeList);
		challengeListService.addChallenge(challengeList);
		return "redirect:/challenge/list";
	}
	
	@PostMapping("list/view/participation")
	public String challengeParticipation(ChallengeList challengeList) {
		return "redirect:/challenge/list";
	}
	
}
