package doself.user.mypage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.user.members.domain.Members;
import doself.user.members.domain.TicketList;
import doself.user.members.service.MembersService;
import doself.util.Pageable;
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
	public String modifyMember(Members member, RedirectAttributes reAttr) {
		member.setMemberEmail(removeCommas(member.getMemberEmail())); 
		member.setMemberPhoneNum(removeCommasPhone(member.getMemberPhoneNum())); 		
		membersService.modifyMember(member);
		reAttr.addAttribute("memberId", member.getMemberId());
		
		return "redirect:/mypage/member/info";
	}
	
	@PostMapping("/pwCheck")
	@ResponseBody
	public boolean pwCheck(@RequestParam(name="memberId") String memberId,
					       @RequestParam(name="oldMemberPw") String oldMemberPw) {
		
		return membersService.passwordChk(memberId, oldMemberPw);
	}
	
	@PostMapping("/pwUpdate")
	@ResponseBody
	public boolean pwUpdate(@RequestParam(name="memberId") String memberId,
							@RequestParam(name="oldMemberPw") String oldMemberPw,
							@RequestParam(name="newMemberPw") String newMemberPw, 
							@RequestParam(name="confirmMemberPw") String confirmMemberPw) {
	    // 현재 비밀번호 확인
	    if (!membersService.passwordChk(memberId,oldMemberPw)) {
	        return false; // 현재 비밀번호가 일치하지 않음
	    }
	    // 새 비밀번호와 확인 비밀번호 일치 여부 확인
	    if (!newMemberPw.equals(confirmMemberPw)) {
	        return false; // 새 비밀번호가 일치하지 않음
	    }
	    // 새 비밀번호가 현재 비밀번호와 동일한지 확인
	    if (oldMemberPw.equals(newMemberPw)) {
	        return false; // 새 비밀번호가 기존 비밀번호와 동일
	    }
		return membersService.updatePassword(memberId,newMemberPw);
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
	
	// 회원탈퇴
	@PostMapping("/member/delete" )
	public String deleteMember() {
		 
		return "/";
	}	
	
	// 회원티켓내역조회
	@GetMapping("/tickethistory" )
	public String getTicketHistory(@RequestParam(name="memberId", required=false, defaultValue="id008") String memberId,
								   @RequestParam(name="startDate", required=false) String startDate,
								   @RequestParam(name="endDate", required=false) String endDate,
								   Model model,
								   Pageable pageable) {
		
		var pageInfo = membersService.getTicketHistory(memberId, pageable, startDate, endDate);
		
		Members memberInfo = membersService.getMemberInfoById(memberId);
		List<TicketList> ticketList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		log.info("pageInfo: {}", pageInfo);
		//log.info("ticketUsedCnt: {}", ticketList.get(0).getTicketUsedCnt());
		//log.info("ticketNotUseCnt: {}", ticketList.get(0).getTicketNotUsedCnt());
		
		model.addAttribute("ticketList", ticketList);
		model.addAttribute("ticketUsedCnt", ticketList.get(0).getTicketUsedCnt());
		model.addAttribute("ticketNotUsedCnt", ticketList.get(0).getTicketNotUsedCnt());
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		
		return "user/mypage/ticket-history";
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
