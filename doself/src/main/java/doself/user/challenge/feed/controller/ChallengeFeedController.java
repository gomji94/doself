package doself.user.challenge.feed.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.service.ChallengeFeedService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
	public String viewChallengeFeed(
	        @RequestParam(value = "challengeCodeValue", required = false) String challengeCode,
	        //@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
	        @RequestParam(value = "challengeStatus", required = false) String challengeStatus,
	        Pageable pageable, HttpSession session, Model model) {
	    
	    // 로그인된 사용자 정보 확인
	    String loggedInMemberId = (String) session.getAttribute("SID");

	    // 참여 중인 챌린지 코드 가져오기
	    if (challengeCode == null) {
            challengeCode = challengeFeedService.getChallengeCodeByMemberId(loggedInMemberId);
            if (challengeCode == null) {
            	return "redirect:user/food/list";
            }
        }
	    
	    // 추가 페이징 처리
	    var pageInfo = challengeFeedService.getChallengeFeedPage(challengeCode,pageable);

	    List<ChallengeFeed> challengeFeedList = pageInfo.getContents();
	    int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		log.info("challengeFeedList: {}", challengeFeedList);
	    
        // 챌린지 진행률 및 상태 처리(원형 그래프, 이미지 그래프) → 처음 호출 시에만 실행(캐싱 로직 추가)
        List<ChallengeProgress> challengeProgress = (List<ChallengeProgress>) session.getAttribute("challengeProgress");
        if (challengeProgress == null) { // 세션에 저장된 값이 없을 경우에만 실행
            challengeProgress = challengeFeedService.getProcessChallengeStatus(challengeCode, challengeStatus);
            session.setAttribute("challengeProgress", challengeProgress); // 결과를 세션에 저장
        }

        // 진행률 합계 계산 (최소화된 호출)
        Integer totalProgress = (Integer) session.getAttribute("totalProgress");
        if (totalProgress == null) { // 캐시된 값이 없을 때만 호출
            totalProgress = challengeFeedService.calculateTotalProgress(challengeCode);
            session.setAttribute("totalProgress", totalProgress); // 계산 결과를 세션에 저장
        }
        
        //int totalProgress = challengeFeedService.calculateTotalProgress(challengeCode);
	    
        log.info("Challenge Progress: {}", challengeProgress);
        log.info("challengeStatus: {}", challengeStatus);
        
	    // 챌린지 참여율 상위 3명
        List<ChallengeMemberList> topParticipants = challengeFeedService.getTopParticipants(challengeCode);
        
        // 투데이 디데이
        Map<String, String> dateCalculations = challengeFeedService.calculateDPlusAndDMinus(challengeCode);
        
        model.addAttribute("challengeCode", challengeCode);
        model.addAttribute("challengeFeedList", challengeFeedList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPageNum", startPageNum);
        model.addAttribute("endPageNum", endPageNum);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("challengeProgress", challengeProgress);
        model.addAttribute("totalProgress", totalProgress);
        model.addAttribute("topParticipants", topParticipants);
        model.addAttribute("dPlus", dateCalculations.get("dPlus"));
        model.addAttribute("dMinus", dateCalculations.get("dMinus"));

	    //log.info("Challenge Feed List: {}", pageInfo.getContents());
	    //log.info("Page Info: {}", pageInfo);
	    //log.info("challengeProgress: {}", challengeProgress);
	    //log.info("challengeCode: {}", challengeCode);

	    return "user/challenge/challenge-view";
	    // "더보기"를 위한 요청인지 확인 후 처리 ? HTML fragment 반환 : 기본 페이지 반환
	    //return currentPage > 1 ? "user/challenge/challenge-feed-fragment :: feedFragment" : "user/challenge/challenge-view";
	}
	
	// 챌린지 멤버 리스트 조회
	@GetMapping("/memberlist")
	@ResponseBody
	public List<ChallengeMemberList> getMemberList(@RequestParam(value = "challengeCode") String challengeCode, Model model) {
	    // 데이터 가져오기
	    List<ChallengeMemberList> memberList = challengeFeedService.getMemberList(challengeCode);

	    return memberList;
	}
	
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
