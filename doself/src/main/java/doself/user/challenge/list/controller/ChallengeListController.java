package doself.user.challenge.list.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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

import doself.user.challenge.list.domain.AddChallenge;
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
		
		//log.info("Fetched Challenge List: {}", challengeList);
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
        //log.info("Challenge Code: {}", challengeCode);
        ChallengeDetailView challengeDetail = challengeListService.getChallengeListView(challengeCode);
        //log.info("Challenge Detail: {}", challengeDetail);
        return challengeDetail;
    }
	
	// 챌린지 생성 폼
	@PostMapping("/list/createchallengerequest")
	public String addChallenge(AddChallenge addChallenge,
			@RequestPart(name = "files", required = false) MultipartFile files, HttpSession session) {
		addChallenge.setMemberId((String) session.getAttribute("SID"));
		challengeListService.addChallenge(addChallenge);
		
		// 파일 처리
	    if (files != null && !files.isEmpty()) {
	        String filePath = "uploads/" + files.getOriginalFilename();
	        try {
	            files.transferTo(new File(filePath));
	            addChallenge.setChallengePicture(filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "redirect:/error";
	        }
	    }
		
	    log.info(">>>>>>>>>> addChallenge : {}", addChallenge);
		log.info(">>>>>>>>>> file : {}", files);

		return "redirect:/challenge/list";
	}
	
	// 챌린지 이름 중복 처리
	@PostMapping("/checkDuplicateName")
	@ResponseBody
    public Map<String, Boolean> checkDuplicateName(@RequestBody Map<String, String> request) {
        String challengeName = request.get("challengeName");
        boolean isAvailable = !challengeListService.isNameDuplicate(challengeName);
        log.info(">>>>>>>>>> challengeName : {}", challengeName);
        return Collections.singletonMap("available", isAvailable);
    }
	
	@PostMapping("list/view/participation")
	public String challengeParticipation(ChallengeList challengeList) {
		return "redirect:/challenge/list";
	}
	
}
