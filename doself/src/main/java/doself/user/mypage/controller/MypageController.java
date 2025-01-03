package doself.user.mypage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.user.members.domain.Members;
import doself.user.members.service.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

	private final MembersService membersService;

	// 회원정보
	@GetMapping("/member/info")   //
	public String getMemberInfo(@RequestParam(name="memberId") String memberId, Model model) {	
		Members memberInfo = membersService.getMemberInfoById(memberId);
		String[] memberTel = memberInfo.getMemberPhoneNum().split("-");
		model.addAttribute("memberInfo",memberInfo);
		model.addAttribute("memberTel",memberTel);
		//log.info("250102memberInfo", memberInfo);
		return "user/mypage/info";
	}
	
	// 회원정보수정
	@PostMapping("/member/modify")
	public String modifyUser(Members member, RedirectAttributes reAttr) {
		
		//membersService.moidfyMember(member);
		
		return "user/mypage/info";
	}
	
	// 회원탈퇴
	@PostMapping("/member/delete" )
	public String deleteMember() {
			
		return "user/mypage/";
	}	
	
	// 회원티켓내역조회
	@GetMapping("/tickethistory" )
	public String getTicketHistory() {
			
		return "user/mypage/ticket-hisory";
	}
	
	// 회원티켓내역검색
	@GetMapping("/tickethistory/search" )
	public String getTicketHistoryByDate() {
			
		return "user/mypage/";
	}
	// 회원포인트내역조회
	@GetMapping("/pointhistory" )
	public String getPointHistory() {
		
		return "user/mypage/point-hisory";
	}
	
	// 회원포인트내역검색
	@GetMapping("/pointhistory/search" )
	public String getPointHistoryByDate() {
		
		return "user/mypage/";
	}
	
	// 회원피드내역조회
	@GetMapping("/feedlist" )
	public String getFeedList() {
		
		return "user/mypage/feed-list";
	}

	// 회원 특정피드조회
	@GetMapping("/feedList/view" )
	public String getFeedDetail() {
		
		return "user/mypage/";
	}
}
