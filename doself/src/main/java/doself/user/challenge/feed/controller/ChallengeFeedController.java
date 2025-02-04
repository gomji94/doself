package doself.user.challenge.feed.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeMemberWarning;
import doself.user.challenge.feed.domain.ChallengeTotalProgress;
import doself.user.challenge.feed.domain.ParticipateChallengeList;
import doself.user.challenge.feed.service.ChallengeFeedService;
import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.service.ChallengeListService;
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
	
	@org.springframework.beans.factory.annotation.Value("${file.path}")
	private String fileRealPath;
	
	private final ChallengeFeedService challengeFeedService;
	private final ChallengeListService challengeListService;
	
	// 접속 아이디가 참여중인 챌린지 리스트 조회
	@GetMapping("/list")
	public String participateChallengeFeedList(
			@RequestParam(value = "challengeCodeValue", required = false) String challengeCode,
	        HttpSession session, Model model) {
	    
	    String loggedInMemberId = (String) session.getAttribute("SID");
	    
	    List<ParticipateChallengeList> participateChallengeList = challengeFeedService.getChallengeListByMemberId(loggedInMemberId);

	    model.addAttribute("challengeCode", challengeCode);
	    model.addAttribute("participateChallengeList", participateChallengeList);
	
		return "user/challenge/participate-challenge-list";
	}
		
	// 챌린지 피드 조회
	@GetMapping("/view/{challengeCode}")
	public String viewChallengeFeed(@PathVariable("challengeCode") String challengeCode, Pageable pageable,
								    String challengeFeedCode, ChallengeTotalProgress challengeTotalProgress,
	        						HttpSession session, Model model) {
		
		String loggedInMemberId = (String) session.getAttribute("SID");
		List<ChallengeMemberList> challengeMemberList = challengeFeedService.getMemberList(challengeCode);
	    
	    // 챌린지 난이도 리스트
	    var levelList = challengeListService.getChallengeLevelList();

	    var pageInfo = challengeFeedService.getChallengeFeedPage(challengeCode, pageable);
	    List<ChallengeFeed> challengeFeedList = pageInfo.getContents();
	    int currentPage = pageInfo.getCurrentPage();
	    int startPageNum = pageInfo.getStartPageNum();
	    int endPageNum = pageInfo.getEndPageNum();
	    int lastPage = pageInfo.getLastPage();
	    
	    if (challengeFeedList == null || challengeFeedList.isEmpty()) {
	        challengeFeedList = Collections.emptyList();
	    }
	    
	    List<ChallengeFeedComment> feedCommentList = challengeFeedService.getFeedCommentList(challengeFeedCode);
	    
	    var challengeFeedPageInfo = challengeFeedService.getChallengeFeedPage(challengeCode, pageable);
	    List<ChallengeTotalProgress> topParticipants = challengeFeedService.getTopParticipants(challengeCode);
	    var challengeTotalProgressInfo = challengeFeedService.getChallengeTotalProgressInfo(challengeCode);
	    var dateCalculations = challengeFeedService.calculateDPlusAndDMinus(challengeCode);
	    
	    model.addAttribute("challengeCode", challengeCode);
	    model.addAttribute("challengeFeedCode", challengeFeedCode);
	    model.addAttribute("challengeFeedList", challengeFeedList);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("startPageNum", startPageNum);
	    model.addAttribute("endPageNum", endPageNum);
	    model.addAttribute("lastPage", lastPage);
	    model.addAttribute("pageInfo", challengeFeedPageInfo);
	    model.addAttribute("topParticipants", topParticipants);
	    model.addAttribute("dPlus", dateCalculations.getOrDefault("dPlus", "N/A"));
	    model.addAttribute("dMinus", dateCalculations.getOrDefault("dMinus", "N/A"));
	    model.addAttribute("levelList", levelList);
	    model.addAttribute("challengeMemberList", challengeMemberList);
	    model.addAttribute("feedCommentList", feedCommentList);
	    model.addAttribute("loggedInMemberId", loggedInMemberId);
	    model.addAttribute("challengeTotalProgressInfo", challengeTotalProgressInfo);
	    
	    //log.info(">>> Controller >>> dateCalculations: {}", dateCalculations);
	    
	    return "user/challenge/challenge-view";
	}
	
	// 챌린지 멤버 경고
	@GetMapping("/memberwarning")
	public String getChallengeMemberDetails(@RequestParam("challengeCode") String challengeCode,
											ChallengeMemberList challengeMemberList, HttpSession session, Model model) {
		String loggedInMemberId = (String) session.getAttribute("SID");
		ChallengeMemberList warningList = challengeFeedService.getWarningList(challengeCode);
		List<ChallengeMemberList> warningMemberList = challengeFeedService.getWarningMemberList(challengeCode);
				
	    //log.info(">>> Controller >>> challengeMemberList: {}", challengeMemberList);

	    // 서비스 호출하여 데이터를 가져옴
	    Map<String, Object> response = challengeFeedService.getChallengeMemberDetails(challengeCode);

	    model.addAttribute("challengeCode", challengeCode);
	    model.addAttribute("memberList", response.get("memberList"));
	    model.addAttribute("warningCategoryList", response.get("warningCategoryList"));
	    model.addAttribute("challengeMemberList", challengeMemberList);
	    model.addAttribute("warningList", warningList);
	    model.addAttribute("warningMemberList", warningMemberList);

	    return "user/challenge/challenge-member-warning";
	}
	
	// 챌린지 경고 피드/댓글 리스트 조회
	@PostMapping("/fetchContent")
	@ResponseBody
	public List<Map<String, String>> fetchContent(@RequestBody Map<String, String> requestData) {
	    String challengeCode = requestData.get("challengeCode");
	    String memberId = requestData.get("memberId");
	    String type = requestData.get("type");
	    
	    List<Map<String, String>> contentList = new ArrayList<>();
	    if ("olc_005".equals(type)) { // 피드 조회
	        contentList = challengeFeedService.getFeedContentByChallengeAndMember(challengeCode, memberId);
	    } else if ("olc_006".equals(type)) { // 댓글 조회
	        contentList = challengeFeedService.getCommentContentByChallengeAndMember(challengeCode, memberId);
	    }

	    //log.info(">>> Controller >>> fetchContent requestData: {}", requestData);
	    //log.info(">>> Controller >>> fetchContent contentList: {}", contentList);
	    
	    return contentList;
	}
	
	// 챌린지 멤버 경고 폼
	@PostMapping("/memberlist/warningrequest")
	public ResponseEntity<Boolean> challengeMemberWarning(@RequestBody ChallengeMemberWarning warning, HttpSession session) {
		String loggedInMemberId = (String) session.getAttribute("SID");
	    boolean isWarning = challengeFeedService.warningChallengeMember(warning, loggedInMemberId);
	    return ResponseEntity.ok(isWarning);
	}
	
	// 챌린지 생성 화면 조회
	@GetMapping("/createchallenge")
	public String getCreateChallenge(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "챌린지 생성");
		
		return "user/challenge/create-challenge";
	}
	
	// 챌린지 생성 폼
	@PostMapping("/feed/createchallengerequest")
	@ResponseBody
	public String addChallenge(AddChallenge addChallenge,
							   @RequestPart(name = "files", required = false) MultipartFile files,
							   HttpSession session, Model model) {
		
		// 현재 세션의 아이디 설정
		addChallenge.setMemberId((String) session.getAttribute("SID"));
		challengeListService.addChallenge(files, addChallenge);
		
		return "redirect:/challenge/list";
	}
	
	// 챌린지 피드 생성 폼
	@PostMapping("/createchallengefeedrequest")
	public String addChallengeFeed(AddChallengeFeed addChallengeFeed,
								   @RequestPart(name = "files", required = false) MultipartFile files,
								   HttpSession session) {
	    String memberId = (String) session.getAttribute("SID");
	    addChallengeFeed.setChallengeMemberId(memberId);

	    challengeFeedService.addChallengeFeed(files, addChallengeFeed);
	    
	    //관리자 기능 실행
	    challengeFeedService.adminChallengeFeed(addChallengeFeed);

	    return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 수정 화면 조회
	@GetMapping("/modifychallengefeed")
	@ResponseBody
	public AddChallengeFeed getModifyChallengeFeed(@RequestParam("challengeFeedCode") String challengeFeedCode,
												   HttpSession session, Model model) {
		return challengeFeedService.getChallengeFeedByCode(challengeFeedCode);
	}
	
	// 챌린지 피드 수정 폼
	@PostMapping("/modifychallengefeedrequest")
	public String postMethodName(@RequestPart(name = "modifyFiles", required = false) MultipartFile files,
								 AddChallengeFeed addChallengeFeed, HttpSession session) {
		
		//log.info("fileYN:{}, files: {}", files.isEmpty(), files);
		//log.info("addChallengeFeed: {}", addChallengeFeed);
		
		String memberId = (String) session.getAttribute("SID");
	    addChallengeFeed.setChallengeMemberId(memberId);
	    
	    // 작성자 확인
	    AddChallengeFeed existingFeed = challengeFeedService.getChallengeFeedByCode(addChallengeFeed.getChallengeFeedCode());
	    if (!existingFeed.getChallengeMemberId().equals(memberId)) {
	        throw new IllegalArgumentException("수정 권한이 없습니다.");
	    }

	    challengeFeedService.modifyChallengeFeed(files, addChallengeFeed);
	    
	    return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 삭제
	@PostMapping("/deletechallengefeedrequest")
	public String deleteChallengeFeed(@RequestParam("challengeFeedCode") String challengeFeedCode, 
									  @ModelAttribute AddChallengeFeed addChallengeFeed, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
	    challengeFeedService.deleteChallengeFeed(challengeFeedCode, memberId);
		
		return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 댓글 등록
	@PostMapping("/createcommentrequest")
	@ResponseBody
	public boolean addChallengeFeedComment(@RequestParam("challengeFeedCode") String challengeFeedCode,
            							  @RequestParam("challengeFeedCommentContent") String commentContent,
										  AddChallengeFeed addChallengeFeed,
										  HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		
		ChallengeFeedComment comment = new ChallengeFeedComment();
	    comment.setChallengeFeedCode(challengeFeedCode);
	    comment.setChallengeFeedCommentAuthor(memberId);
	    comment.setChallengeFeedCommentContent(commentContent);
	    
	    //log.info(">>> Controller >>> challengeFeedCode: {}", challengeFeedCode);

	    boolean isCreate = challengeFeedService.addChallengeFeedComment(comment);

		return isCreate;
	}
	
	// 챌린지 피드 댓글 조회
	@GetMapping("/comment")
	@ResponseBody
	public List<ChallengeFeedComment> getFeedComment(@RequestParam(value = "challengeFeedCode") String challengeFeedCode,
													 HttpSession session) {
		String loggedInMemberId = (String) session.getAttribute("SID");
		
		List<ChallengeFeedComment> feedCommentList = challengeFeedService.getFeedCommentList(challengeFeedCode);
		feedCommentList.forEach(comment -> comment.setLoggedInMemberId(loggedInMemberId));
		
		return feedCommentList;
	}
	
	// 챌린지 피드 댓글 수정
	@PostMapping("/modifycommentrequest")
	@ResponseBody
	public boolean modifyCommentRequest(@RequestParam("challengeFeedCommentCode") String challengeFeedCommentCode,
									   @RequestParam("challengeFeedCommentContent") String challengeFeedCommentContent,
									   AddChallengeFeed addChallengeFeed) {
		
		boolean isModify = challengeFeedService.modifyFeedComment(challengeFeedCommentCode, challengeFeedCommentContent);
		
		return isModify;
	}
	
	// 챌린지 피드 댓글 삭제
	@PostMapping("/deletecommentrequest")
	@ResponseBody
	public boolean deleteCommentRequest(@RequestParam("challengeFeedCommentCode") String challengeFeedCommentCode,
									   AddChallengeFeed addChallengeFeed) {
		
		boolean isDelete = challengeFeedService.deleteFeedComment(challengeFeedCommentCode);
		
		return isDelete;
	}
	
	// 챌린지 피드 좋아요 증감
	@PostMapping("/like")
	public ResponseEntity<String> challengeFeedToggleLike(@RequestBody Map<String, Object> feedLike, HttpSession session) {
	    String challengeFeedCode = (String) feedLike.get("challengeFeedCode");
	    String loggedInMemberId = (String) session.getAttribute("SID");
	    Boolean liked = (Boolean) feedLike.get("liked");

	    log.info(">>> Controller >>> challengeFeedCode: {}, liked: {}", challengeFeedCode, liked);

	    try {
	        if (liked) {
	            challengeFeedService.incrementLike(challengeFeedCode);
	        } else {
	            challengeFeedService.decrementLike(challengeFeedCode);
	        }
	        return ResponseEntity.ok("좋아요 상태가 업데이트되었습니다.");
	    } catch (Exception e) {
	        log.error("좋아요 상태 업데이트 중 오류 발생: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("좋아요 상태 업데이트 중 오류 발생.");
	    }
	}
	
}
