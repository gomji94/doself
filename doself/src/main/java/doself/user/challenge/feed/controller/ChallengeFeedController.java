package doself.user.challenge.feed.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.service.ChallengeFeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("challenge/feed")
@RequiredArgsConstructor
@Slf4j
public class ChallengeFeedController {
	
	private final ChallengeFeedService challengeFeedService;
	
	// 챌린지 피드 조회
	@GetMapping("/view")
	public String getChallengeView(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 10;
		List<ChallengeFeed> challengeFeedList = challengeFeedService.getChallengeFeedList(page, pageSize);
		log.info("Fetched challenge feed list: {}", challengeFeedList); // 로그 추가
		model.addAttribute("challengeFeedList", challengeFeedList);
		return "user/challenge/challenge-view";
	}
	
	// 챌린지 멤버 리스트 조회
	@GetMapping("/memberlist")
	public String getMemberList(@RequestParam(value = "challengeCode") String challengeCode, Model model) {
	    log.info("Received challengeCode: {}", challengeCode);

	    // 데이터 가져오기
	    List<ChallengeMemberList> memberList = challengeFeedService.getMemberList(challengeCode);
	    log.info("Fetched memberList: {}", memberList);

	    model.addAttribute("memberList", memberList);

	    // Thymeleaf fragment 반환
	    return "user/challenge/member-list :: member-list";
	}
	/*
	 * public String getMemberList(@RequestParam(value = "challengeCode") String
	 * challengeCode, Model model) { log.info("Received challengeCode: {}",
	 * challengeCode); // 입력 확인 List<ChallengeMemberList> memberList =
	 * challengeFeedService.getMemberList(challengeCode);
	 * log.info("Fetched memberList from Service: {}", memberList);
	 * model.addAttribute("memberList", memberList); return
	 * "user/challenge/member-list"; }
	 */
	
	// 챌린지 멤버 경고 사유 선택 조회(지금은 스킵하고 나중에 작업)
	@GetMapping("/warning")
	public String getMemberWarnig(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "멤버 경고");
		return "user/challenge/challenge-member-warning";
	}
	
	// 챌린지 피드 댓글 조회
	@GetMapping("/feedcomment")
	public String getFeedComment(Model model) {
		
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
