package doself.user.challenge.list.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import doself.file.mapper.FilesMapper;
import doself.file.service.FileService;
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
	private final FileService fileService;
	private final FilesMapper filesMapper;
	
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
		
		//log.info("Fetched Challenge List: {}", challengeList);
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
        
        //log.info(">>> location/controller >>> challengeDetail: {}", challengeDetail);
        return challengeDetail;
    }
	
	// 챌린지 생성 폼
	@PostMapping("/list/createchallengerequest")
	@ResponseBody
	public String addChallenge(AddChallenge addChallenge,
							   @RequestPart(name = "files", required = false) MultipartFile files,
							   HttpSession session, Model model) {
		
		// 현재 세션의 아이디 설정
		addChallenge.setMemberId((String) session.getAttribute("SID"));
		
		//log.info(">>> location/controller >>> files: {}", files);
		
		// 파일 처리
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
	public Map<String, Object> challengeParticipation(@RequestBody AddChallengeMember addChallengeMember,
	        										  HttpSession session, String challengeCode) {
		Map<String, Object> response = new HashMap<>();
		
		addChallengeMember.setChallengeMemberId((String) session.getAttribute("SID"));
		challengeListService.addChallengeMember(addChallengeMember, challengeCode);
		
	    boolean isParticipated = challengeListService.addChallengeMember(addChallengeMember, challengeCode);
	    
	    log.info(">>> location/controller >>> response: {}", response);
	    log.info(">>> location/controller >>> addChallengeMember: {}", addChallengeMember);
	    
		//return "redirect:/challenge/list";
	    return response;
	}
}
