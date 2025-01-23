package doself.user.challenge.feed.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doself.user.challenge.feed.domain.AddChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ParticipateChallengeList;
import doself.user.challenge.feed.service.ChallengeFeedService;
import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.service.ChallengeListService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;


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
	public String viewChallengeFeed(@PathVariable("challengeCode") String challengeCode,
	        						Pageable pageable, String challengeFeedCode,
	        						HttpSession session, Model model) {
		
		//log.info(">>> location/controller >>> challengeCode: {}", challengeCode);  // >>>>>> check
	    
		String loggedInMemberId = (String) session.getAttribute("SID");
		List<ChallengeMemberList> challengeMemberList = challengeFeedService.getMemberList(challengeCode);
	    var pageInfo = challengeFeedService.getChallengeFeedPage(challengeCode, pageable);
	    
	    // 챌린지 난이도 리스트
	    var levelList = challengeListService.getChallengeLevelList();

	    List<ChallengeFeed> challengeFeedList = pageInfo.getContents();
	    int currentPage = pageInfo.getCurrentPage();
	    int startPageNum = pageInfo.getStartPageNum();
	    int endPageNum = pageInfo.getEndPageNum();
	    int lastPage = pageInfo.getLastPage();
	    
	    ChallengeFeed modifyChallengeFeedList = challengeFeedService.getModifyChallengeFeed(challengeFeedCode);
	    
	    List<ChallengeFeedComment> feedCommentList = challengeFeedService.getFeedCommentList(challengeFeedCode);
	    
	    var challengeFeedPageInfo = challengeFeedService.getChallengeFeedPage(challengeCode, pageable);
	    var challengeProgress = challengeFeedService.getProcessChallengeStatus(challengeCode);
	    int totalProgress = challengeFeedService.calculateTotalProgress(challengeCode);
	    var topParticipants = challengeFeedService.getTopParticipants(challengeCode);
	    var dateCalculations = challengeFeedService.calculateDPlusAndDMinus(challengeCode);

	    model.addAttribute("challengeCode", challengeCode);
	    model.addAttribute("challengeFeedCode", challengeFeedCode);
	    model.addAttribute("challengeFeedList", challengeFeedList);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("startPageNum", startPageNum);
	    model.addAttribute("endPageNum", endPageNum);
	    model.addAttribute("lastPage", lastPage);
	    model.addAttribute("lastPage", lastPage);
	    model.addAttribute("pageInfo", pageInfo);
	    model.addAttribute("pageInfo", challengeFeedPageInfo);
	    model.addAttribute("challengeProgress", challengeProgress);
	    model.addAttribute("totalProgress", totalProgress);
	    model.addAttribute("topParticipants", topParticipants);
	    model.addAttribute("dPlus", dateCalculations.get("dPlus"));
	    model.addAttribute("dMinus", dateCalculations.get("dMinus"));
	    model.addAttribute("levelList", levelList);
	    model.addAttribute("challengeMemberList", challengeMemberList);
	    model.addAttribute("feedCommentList", feedCommentList);
	    model.addAttribute("loggedInMemberId", loggedInMemberId);
	    model.addAttribute("modifyChallengeFeedList", modifyChallengeFeedList);
	    
	    log.info(">>> location/controller >>> modifyChallengeFeedList: {}", modifyChallengeFeedList);
	    //log.info(">>> location/controller >>> challengeCode: {}", challengeCode);
	    //log.info(">>> location/controller >>> challengeFeedList: {}", challengeFeedList);
	    //log.info(">>> location/controller >>> challengeProgress: {}", challengeProgress);
	    //log.info(">>> location/controller >>> Total Progress: {}", challengeFeedService.calculateTotalProgress(challengeCode));
	    //log.info(">>> location/controller >>> Top Participants: {}", challengeFeedService.getTopParticipants(challengeCode));
	    //log.info(">>> location/controller >>> Date Calculations: {}", dateCalculations);
	    //log.info(">>> location/controller >>> feedCommentList: {}", feedCommentList);

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
		//log.info(">>> location/controller >>> files: {}", files);
		
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

	    //log.info(">>> location/controller >>> addChallengeFeed: {}", addChallengeFeed);
	    
	    return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 수정 화면 조회
	@GetMapping("/modifychallengefeed")
	@ResponseBody
	public AddChallengeFeed getModifyChallengeFeed(@RequestParam("challengeFeedCode") String challengeFeedCode) {
		return challengeFeedService.getChallengeFeedByCode(challengeFeedCode);
	}
	
	// 챌린지 피드 수정 폼(이전 코드)
	@PostMapping("/modifychallengefeedrequest")
	public String postMethodName(@RequestPart(name = "files", required = false) MultipartFile files,
								 @ModelAttribute AddChallengeFeed addChallengeFeed, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
	    addChallengeFeed.setChallengeMemberId(memberId);

	    challengeFeedService.modifyChallengeFeed(files, addChallengeFeed);
	    
	    log.info(">>> location/controller >>> addChallengeFeed: {}", addChallengeFeed);
	    
	    return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 수정 폼(해결X)
//	@PostMapping("/modifychallengefeedrequest")
//	public String postMethodName(@RequestPart(name = "files", required = false) MultipartFile files,
//								 @ModelAttribute AddChallengeFeed addChallengeFeed,
//								 @RequestParam(value = "previewImagePath", required = false) String previewImagePath,
//								 HttpSession session) {
//		String memberId = (String) session.getAttribute("SID");
//	    addChallengeFeed.setChallengeMemberId(memberId);
//
//	    
//	    log.info("Preview Image Path: {}", previewImagePath);
//	    log.info(">>> location/controller >>> addChallengeFeed: {}", addChallengeFeed);
//	    
//	    challengeFeedService.modifyChallengeFeed(files, addChallengeFeed);
//		//return "redirect:/challenge/feed/list";
//	    return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
//	}
	
	// 챌린지 피드 삭제
	@PostMapping("/deletechallengefeedrequest")
	public String deleteChallengeFeed(@RequestParam("challengeFeedCode") String challengeFeedCode, 
									  @ModelAttribute AddChallengeFeed addChallengeFeed, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
	    challengeFeedService.deleteChallengeFeed(challengeFeedCode, memberId);
	     	    
	    //challengeFeedService.deleteChallengeFeed(files, addChallengeFeed);
		
		return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 댓글 등록
	@PostMapping("/createcommentrequest")
	public String addChallengeFeedComment(@RequestParam("challengeFeedCode") String challengeFeedCode,
            							  @RequestParam("challengeFeedCommentContent") String commentContent,
										  AddChallengeFeed addChallengeFeed,
										  HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		
		ChallengeFeedComment comment = new ChallengeFeedComment();
	    comment.setChallengeFeedCode(challengeFeedCode);
	    comment.setChallengeFeedCommentAuthor(memberId);
	    comment.setChallengeFeedCommentContent(commentContent);

	    challengeFeedService.addChallengeFeedComment(comment);

	    //log.info(">>> location/controller >>> comment: {}", comment);
	    
		return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 댓글 조회
	@GetMapping("/feedcomment")
	@ResponseBody
	public List<ChallengeFeedComment> getFeedComment(@RequestParam(value = "challengeFeedCode") String challengeFeedCode,
													 HttpSession session) {
		String loggedInMemberId = (String) session.getAttribute("SID");
		
		List<ChallengeFeedComment> feedCommentList = challengeFeedService.getFeedCommentList(challengeFeedCode);
		feedCommentList.forEach(comment -> comment.setLoggedInMemberId(loggedInMemberId));
		//log.info("Feed Comment List: {}", feedCommentList);
		
		return feedCommentList;
	}
	
	// 챌린지 피드 댓글 수정
	@PostMapping("/modifycommentrequest")
	public String modifyCommentRequest(@RequestParam("challengeFeedCommentCode") String challengeFeedCommentCode,
									   @RequestParam("challengeFeedCommentContent") String challengeFeedCommentContent,
									   AddChallengeFeed addChallengeFeed) {
		
		challengeFeedService.modifyFeedComment(challengeFeedCommentCode, challengeFeedCommentContent);
		
		return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	// 챌린지 피드 댓글 삭제
	@PostMapping("/deletecommentrequest")
	public String deleteCommentRequest(@RequestParam("challengeFeedCommentCode") String challengeFeedCommentCode,
									   AddChallengeFeed addChallengeFeed) {
		
		challengeFeedService.deleteFeedComment(challengeFeedCommentCode);
		
		return "redirect:/challenge/feed/view/" + addChallengeFeed.getChallengeCode();
	}
	
	
}
