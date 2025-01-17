package doself.user.challenge.feed.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import doself.file.mapper.FilesMapper;
import doself.file.service.FileService;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.service.ChallengeFeedService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Controller
@RequestMapping("/challenge/feed")
@RequiredArgsConstructor
@Slf4j
public class ChallengeFeedController {
	
	private final ChallengeFeedService challengeFeedService;
	private final FileService fileService;
	private final FilesMapper filesMapper;
	
	// 챌린지 피드 조회
	/*
	 * @GetMapping("/view") public String viewChallengeFeed(
	 * 
	 * @RequestParam(value = "challengeCodeValue", required = false) String
	 * challengeCode,
	 * 
	 * @RequestParam(value = "challengeStatus", required = false) String
	 * challengeStatus, Pageable pageable, HttpSession session, Model model) {
	 * 
	 * // 로그인된 사용자 정보 확인 String loggedInMemberId = (String)
	 * session.getAttribute("SID");
	 * 
	 * // 추가 페이징 처리 var pageInfo =
	 * challengeFeedService.getChallengeFeedPage(challengeCode,pageable);
	 * 
	 * List<ChallengeFeed> challengeFeedList = pageInfo.getContents(); int
	 * currentPage = pageInfo.getCurrentPage(); int startPageNum =
	 * pageInfo.getStartPageNum(); int endPageNum = pageInfo.getEndPageNum(); int
	 * lastPage = pageInfo.getLastPage();
	 * 
	 * // for (ChallengeFeed feed : challengeFeedList) { // if
	 * (feed.getChallengeFeedPicture() == null ||
	 * feed.getChallengeFeedPicture().isEmpty()) { //
	 * feed.setChallengeFeedPicture("/images/default-image.png"); // 기본 이미지 경로 설정 //
	 * } else { // feed.setChallengeFeedPicture("/uploaded_files/" +
	 * feed.getChallengeFeedPicture()); // 실제 파일 경로 설정 // } // }
	 * 
	 * // 챌린지 진행률 및 상태 처리(원형 그래프, 이미지 그래프) → 처음 호출 시에만 실행(캐싱 로직 추가)
	 * List<ChallengeProgress> challengeProgress =
	 * challengeFeedService.getProcessChallengeStatus(challengeCode,
	 * challengeStatus); if (challengeProgress == null ||
	 * challengeProgress.isEmpty()) { challengeProgress = new ArrayList<>(); // 빈
	 * 리스트로 초기화 } model.addAttribute("challengeProgress", challengeProgress);
	 * 
	 * // 진행률 합계 계산 (최소화된 호출) Integer totalProgress = (Integer)
	 * session.getAttribute("totalProgress"); if (totalProgress == null) {
	 * totalProgress = challengeFeedService.calculateTotalProgress(challengeCode);
	 * session.setAttribute("totalProgress", totalProgress); }
	 * 
	 * // 챌린지 참여율 상위 3명 List<ChallengeMemberList> topParticipants =
	 * challengeFeedService.getTopParticipants(challengeCode);
	 * 
	 * // 투데이 디데이 Map<String, String> dateCalculations =
	 * challengeFeedService.calculateDPlusAndDMinus(challengeCode);
	 * 
	 * model.addAttribute("challengeCode", challengeCode);
	 * model.addAttribute("challengeFeedList", challengeFeedList);
	 * model.addAttribute("currentPage", currentPage);
	 * model.addAttribute("startPageNum", startPageNum);
	 * model.addAttribute("endPageNum", endPageNum); model.addAttribute("lastPage",
	 * lastPage); model.addAttribute("challengeProgress", challengeProgress);
	 * model.addAttribute("totalProgress", totalProgress);
	 * model.addAttribute("topParticipants", topParticipants);
	 * model.addAttribute("dPlus", dateCalculations.get("dPlus"));
	 * model.addAttribute("dMinus", dateCalculations.get("dMinus"));
	 * model.addAttribute("challengeCurrentMember",
	 * challengeFeedService.getCurrentMemberCount(challengeCode));
	 * 
	 * 
	 * //log.info("Challenge Feed List: {}", pageInfo.getContents());
	 * //log.info("Page Info: {}", pageInfo); log.info("challengeProgress: {}",
	 * challengeProgress); log.info("challengeCode: {}", challengeCode);
	 * //log.info("challengeStatus: {}", challengeStatus);
	 * log.info("challengeFeedList: {}", challengeFeedList);
	 * 
	 * // 참여 중인 챌린지 코드 가져오기(나중에 다 되면 주석 제거 예정) // if (challengeCode == null) { //
	 * challengeCode =
	 * challengeFeedService.getChallengeCodeByMemberId(loggedInMemberId); // if
	 * (challengeCode == null) { // return "redirect:/feed/list"; // } // }
	 * 
	 * return "user/challenge/challenge-view"; }
	 */
	
	@GetMapping("/view")
	public String viewChallengeFeed(
	        @RequestParam(value = "challengeCodeValue", required = false) String challengeCode,
	        @RequestParam(value = "challengeStatus", required = false) String challengeStatus,
	        Pageable pageable, HttpSession session, Model model) {
	    
	    // 로그인된 사용자 정보 확인
	    String loggedInMemberId = (String) session.getAttribute("SID");
	    
	    // 추가 페이징 처리
	    var pageInfo = challengeFeedService.getChallengeFeedPage(loggedInMemberId,pageable);

	    List<ChallengeFeed> challengeFeedList = pageInfo.getContents();
	    int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		
		
        // 챌린지 진행률 및 상태 처리(원형 그래프, 이미지 그래프) → 처음 호출 시에만 실행(캐싱 로직 추가)
		/*
		 * List<ChallengeProgress> challengeProgress =
		 * challengeFeedService.getProcessChallengeStatus(challengeCode,
		 * challengeStatus); if (challengeProgress == null ||
		 * challengeProgress.isEmpty()) { challengeProgress = new ArrayList<>(); // 빈
		 * 리스트로 초기화 } model.addAttribute("challengeProgress", challengeProgress);
		 */
        // 진행률 합계 계산 (최소화된 호출)
		/*
		 * Integer totalProgress = (Integer) session.getAttribute("totalProgress"); if
		 * (totalProgress == null) { totalProgress =
		 * challengeFeedService.calculateTotalProgress(challengeCode);
		 * session.setAttribute("totalProgress", totalProgress); }
		 */
        
	    // 챌린지 참여율 상위 3명
		/*
		 * List<ChallengeMemberList> topParticipants =
		 * challengeFeedService.getTopParticipants(challengeCode);
		 */
        
        // 투데이 디데이
		/*
		 * Map<String, String> dateCalculations =
		 * challengeFeedService.calculateDPlusAndDMinus(challengeCode);
		 */
        
		/*
		 * model.addAttribute("challengeCode", challengeCode);
		 * model.addAttribute("challengeFeedList", challengeFeedList);
		 * model.addAttribute("currentPage", currentPage);
		 * model.addAttribute("startPageNum", startPageNum);
		 * model.addAttribute("endPageNum", endPageNum); model.addAttribute("lastPage",
		 * lastPage); model.addAttribute("challengeProgress", challengeProgress);
		 * model.addAttribute("totalProgress", totalProgress);
		 * model.addAttribute("topParticipants", topParticipants);
		 * model.addAttribute("dPlus", dateCalculations.get("dPlus"));
		 * model.addAttribute("dMinus", dateCalculations.get("dMinus"));
		 * model.addAttribute("challengeCurrentMember",
		 * challengeFeedService.getCurrentMemberCount(challengeCode));
		 */


	    //log.info("Challenge Feed List: {}", pageInfo.getContents());
	    //log.info("Page Info: {}", pageInfo);
		/*
		 * log.info("challengeProgress: {}", challengeProgress);
		 * log.info("challengeCode: {}", challengeCode);
		 * //log.info("challengeStatus: {}", challengeStatus);
		 * log.info("challengeFeedList: {}", challengeFeedList);
		 */

        // 참여 중인 챌린지 코드 가져오기(나중에 다 되면 주석 제거 예정)
//	    if (challengeCode == null) {
//            challengeCode = challengeFeedService.getChallengeCodeByMemberId(loggedInMemberId);
//            if (challengeCode == null) {
//            	return "redirect:/feed/list";
//            }
//        }
        
	    return "user/challenge/challenge-view";
	}
	
	// 챌린지 멤버 리스트 조회
	@GetMapping("/memberlist")
	@ResponseBody
	public List<ChallengeMemberList> getMemberList(@RequestParam(value = "challengeCode") String challengeCode, Model model) {
	    // 데이터 가져오기
		List<ChallengeMemberList> memberList = challengeFeedService.getMemberList(challengeCode);
		//log.info("Member List: {}", memberList);
		
	    return memberList;
	}
	
	// 챌린지 멤버 경고 사유 선택 조회
	@GetMapping("/warning")
	public String getMemberWarnig(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "멤버 경고");
		return "user/challenge/challenge-member-warning";
	}
	
	// 챌린지 피드 댓글 조회(모달 안 열림/추후 수정필요)
	@GetMapping("/feedcomment")
	@ResponseBody
	public List<ChallengeFeedComment> getFeedComment(@RequestParam(value = "challengeFeedCode") String challengeFeedCode, Model model) {
		List<ChallengeFeedComment> feedCommentList = challengeFeedService.getFeedCommentList(challengeFeedCode);
		
		log.info("Feed Comment List: {}", feedCommentList);
		
		return feedCommentList;
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
