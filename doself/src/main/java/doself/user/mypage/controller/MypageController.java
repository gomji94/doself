package doself.user.mypage.controller;

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

	// 회원정보수정
	@GetMapping("/member/info")   //
	public String getMemberInfoView(@RequestParam(name="memberId") String memberId, Model model) {	
		Members memberInfo = membersService.getMemberInfoById(memberId);
		String[] memberTel = memberInfo.getMemberPhoneNum().split("-");
		String Email = memberInfo.getMemberEmail();
		int atIndex = Email.indexOf("@");
		String memberEmail = Email.substring(0, atIndex);
		model.addAttribute("memberInfo",memberInfo);
		model.addAttribute("memberTel",memberTel);
		model.addAttribute("memberEmail",memberEmail);
		return "user/mypage/info";
	}
	
	// 회원정보수정
	@PostMapping("/member/info")
	public String modifyMember(Members member, RedirectAttributes reAttr, Model model) {
		member.setMemberEmail(removeCommas(member.getMemberEmail())); 
		member.setMemberPhoneNum(removeCommasPhone(member.getMemberPhoneNum())); 
		int resultType = membersService.passwordChk(member);
		System.out.println(member);
		System.out.println(resultType);
		if(resultType == 1) {
			/* membersService.modifyMember(member); */
		} else {
			
		}
		model.addAttribute("resultType", resultType);
		
		
		reAttr.addAttribute("memberId", member.getMemberId());
		return "redirect:/mypage/member/info";
	}
	
	// 회원탈퇴
	@PostMapping("/member/delete" )
	public String deleteMember() {
			
		return "/";
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
	
	
	
	// 공통 메소드 static common 에 작성 할 것.
	// 유효성검사 js 로 검증 후 데이터 넘겨받기
	public static String removeCommas(String input) {
        if (input == null) {
            return null; // Handle null input gracefully
        }
        return input.replace(",", "");
    }
	
	public static String removeCommasPhone(String input) {
        if (input == null) {
            return null; // Handle null input gracefully
        }
        return input.replace(",", "-");
    }
	
	
	
	
}
