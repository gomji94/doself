package doself.user.challenge.list.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.domain.AddChallengeMember;
import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.service.ChallengeListService;
import doself.util.CardPageable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Slf4j
public class ChallengeListController {
	
	@org.springframework.beans.factory.annotation.Value("${file.path}")
	private String fileRealPath;
	
	// 챌린지 리스트 서비스 → 인스턴스 생성자 메소드
	private final ChallengeListService challengeListService;
	
	// 진행중인 챌린지 리스트 조회
	@GetMapping("/list")
	// HttpServletRequest : 클라이언트가 보낸 사용자 입력 및 데이터 추출
	public String getChallengeList(CardPageable cardPageable, Model model) {
		var challengeListPageInfo = challengeListService.getChallengeList(cardPageable);
		
		// 챌린지 주제 리스트
		var topicList = challengeListService.getChallengeTopicList();
	    // 챌린지 난이도 리스트
	    var levelList = challengeListService.getChallengeLevelList();
		
		List<ChallengeList> challengeList = challengeListPageInfo.getContents();
		int currentPage = challengeListPageInfo.getCurrentPage();
		int startPageNum = challengeListPageInfo.getStartPageNum();
		int endPageNum = challengeListPageInfo.getEndPageNum();
		int lastPage = challengeListPageInfo.getLastPage();
		
		// 참여 멤버 수를 계산하여 ChallengeList에 반영
	    challengeList.forEach(cl -> {
	        int memberCount = challengeListService.getCurrentMemberCount(cl.getChallengeCode());
	        cl.setChallengeCurrentMember(memberCount);
	    });
		
		//log.info(">>> location/controller >>> challengeList : {}", challengeList);
		
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("topicList", topicList);
		model.addAttribute("levelList", levelList);
		return "user/challenge/challenge-list";
	}
	
	// 진행중인 챌린지 상세 정보 조회
	@GetMapping("/list/view")
	@ResponseBody
	// HttpServletRequest : 클라이언트가 보낸 사용자 입력 및 데이터 추출
	public ChallengeDetailView getChallengeListView(@RequestParam("challengeCode") String challengeCode) {
        ChallengeDetailView challengeDetail = challengeListService.getChallengeListView(challengeCode);

        return challengeDetail;
    }
	
	// 챌린지 생성 폼
	@PostMapping("/list/createchallengerequest")
	@ResponseBody
	public String addChallenge(AddChallenge addChallenge,
							   @RequestPart(name = "files", required = false) MultipartFile files,
							   HttpSession session, Model model) {
		
		String memberId = (String) session.getAttribute("SID");
		
		// 현재 세션 아이디의 티켓 개수 조회
	    int openingTicketCount = challengeListService.getOpeningTicketCount(memberId);

	    // 티켓 개수가 0개이면 생성 불가
	    if (openingTicketCount <= 0) {
	        model.addAttribute("errorMessage", "챌린지 생성 티켓을 보유하고 있지 않습니다.");
	        return "redirect:/challenge/list";
	    }

	    // 세션 ID를 챌린지 리더로 설정
	    addChallenge.setMemberId(memberId);

	    // 챌린지 생성 처리
	    challengeListService.addChallenge(files, addChallenge);
		
		return "redirect:/challenge/list";
	}
	
	// 챌린지 이름 중복 처리
	@PostMapping("/checkDuplicateName")
	@ResponseBody
    public Map<String, Boolean> checkDuplicateName(@RequestBody Map<String, String> request) {
        String challengeName = request.get("challengeName");
        boolean isAvailable = !challengeListService.isNameDuplicate(challengeName);
        //log.info(">>> location/controller >>> challengeName : {}", challengeName);
        return Collections.singletonMap("available", isAvailable);
    }
	
	// 챌린지 참여 폼
	@PostMapping("list/view/participation")
	@ResponseBody
	public Map<String, Object> challengeParticipation(@RequestBody AddChallengeMember addChallengeMember,
	        										  HttpSession session, Model model) {
		
		Map<String, Object> response = new HashMap<>();
		String sessionId = (String) session.getAttribute("SID");
		
	    if (sessionId == null) {
	        response.put("success", false);
	        response.put("message", "세션이 만료되었거나 로그인이 필요합니다.");
	        return response;
	    }
	    addChallengeMember.setChallengeMemberId(sessionId);
	    
	    // 현재 세션 아이디의 챌린지 참여 티켓 개수 조회
	    int participationTicketCount = challengeListService.getParticipationTicketCount(sessionId);

	    // 티켓 개수가 0개이면 참여 불가
	    if (participationTicketCount <= 0) {
	        response.put("success", false);
	        response.put("message", "챌린지 참여 티켓을 보유하고 있지 않습니다.");
	        return response;
	    }
	    
	    // 이미 참여 중인지 확인
	    boolean isAlreadyParticipated = challengeListService.isAlreadyParticipated(addChallengeMember);
	    if (isAlreadyParticipated) {
	        response.put("success", false);
	        response.put("message", "이미 참여중인 챌린지입니다.");
	        return response;
	    }

	    try {
	        // 챌린지 멤버 추가
	        boolean isParticipated = challengeListService.addChallengeMember(addChallengeMember);
	        if (isParticipated) {
	            // 참여 성공 시 티켓 차감
	        	challengeListService.decrementParticipationTicket(sessionId);
	        	
	        	int updatedTicketCount = challengeListService.getParticipationTicketCount(sessionId);
	            log.info(">>> 참여 후 남은 티켓 개수 (memberId: {}, updatedCount: {})", sessionId, updatedTicketCount);

	            // 챌린지 상태 업데이트
	            challengeListService.updateChallengeStatuses();

	            response.put("success", true);
	            response.put("message", "챌린지 참여가 완료되었습니다.");
	        } else {
	            response.put("success", false);
	            response.put("message", "참여 처리 중 오류가 발생했습니다.");
	        }
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "참여 처리 중 오류가 발생했습니다.");
	    }
		
//	    try {
//	        // 이미 참여 중인지 확인
//	        boolean isAlreadyParticipated = challengeListService.isAlreadyParticipated(addChallengeMember);
//
//	        if (isAlreadyParticipated) {
//	            response.put("success", false);
//	            response.put("message", "이미 참여중인 챌린지입니다.");
//	            return response;
//	        }
//
//	        boolean isParticipated = challengeListService.addChallengeMember(addChallengeMember);
//	        response.put("success", isParticipated);
//	        response.put("message", isParticipated ? "참여가 완료되었습니다." : "참여 처리 중 오류가 발생했습니다.");
//	    } catch (IllegalArgumentException e) {
//	        response.put("success", false);
//	        response.put("message", e.getMessage());
//	    } catch (Exception e) {
//	        response.put("success", false);
//	        response.put("message", "참여 처리 중 오류가 발생했습니다.");
//	    }
//	    
//	    List<ChallengeList> challengeList = challengeListService.getChallengeList();
//	    for(ChallengeList challengeIdx : challengeList) {
//	    	String statusCode = challengeIdx.getChallengeStatusCode();
//	    	challengeListService.updateChallengeStatuses();
//	    	return response;
//	    }
	    
	    log.info(">>> location/controller >>> : response {}", response);
	    
	    return response;
	}
}
