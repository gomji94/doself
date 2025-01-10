package doself.user.mypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.user.members.domain.Members;
import doself.user.members.domain.PointList;
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

	//회원정보 수정전 검증
	@GetMapping("/modify")
	public String getPwCheck (@RequestParam(name="memberId") String memberId,
							  Model model) {
		model.addAttribute("memberId", memberId);
		return "user/mypage/modify-check";
	}
	
	// 회원정보수정
	@GetMapping("/member/info")   
	public String getMemberInfoView(@RequestParam(name="memberId") String memberId,
									@RequestParam(name="msg", required = false) String msg, 
									@ModelAttribute("message") String message, 
									Model model){
		Members memberInfo = membersService.getMemberInfoById(memberId);
        String[] memberTel = memberInfo.getMemberPhoneNum().split("-");
		String Email = memberInfo.getMemberEmail();
		int atIndex = Email.indexOf("@");
		String memberEmail = Email.substring(0, atIndex);
		
		
		model.addAttribute("memberInfo", memberInfo);
		if(msg !=null) model.addAttribute("msg", msg);
		
		return "user/mypage/info";
		}	

	// 회원정보수정
	@PostMapping("/modify")
	public String modifyMember(@RequestParam(name="memberId") String memberId,  
	                           @RequestParam(name="memberPw") String memberPw,  
	                           RedirectAttributes reAttr,
	                           Model model) {						
		//member.setMemberEmail(removeCommas(member.getMemberEmail())); 
		//member.setMemberPhoneNum(removeCommasPhone(member.getMemberPhoneNum())); 		
		String viewName = "redirect:/mypage/modify";
		Map<String, Object> resultMap = membersService.matchedMember(memberId, memberPw);
		boolean isMatched = (boolean)resultMap.get("isMatched");
		if(isMatched) {
			Members memberInfo = (Members)resultMap.get("memberInfo");
			model.addAttribute("memberInfo", memberInfo);
			viewName = "user/mypage/info";
		} else {
				reAttr.addAttribute("memberId", memberId);
				reAttr.addFlashAttribute("message", "회원의 정보가 일치하지 않습니다.");
		  }
		  return viewName;
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
	@PostMapping("/delete" )
	public String deleteMember(@RequestParam(name="memberId") String memberId) {
		
		
		membersService.removeMemberById(memberId);
		return "redirect:/login";
	}	
	
	// 회원티켓 내역조회
	@GetMapping("/tickethistory" )
	public String getTicketHistory(@RequestParam(name = "memberId") String memberId,
								   @RequestParam(name="startDate", required=false) String startDate,
								   @RequestParam(name="endDate", required=false) String endDate,
								   Model model, Pageable pageable) {
		
		var pageTicketInfo = membersService.getTicketHistory(memberId, pageable, startDate, endDate);
		Members memberInfo = membersService.getMemberInfoById(memberId);
		
		List<TicketList> ticketList = pageTicketInfo.getContents();
		int currentPage = pageTicketInfo.getCurrentPage();
		int startPageNum = pageTicketInfo.getStartPageNum();
		int endPageNum = pageTicketInfo.getEndPageNum();
		int lastPage = pageTicketInfo.getLastPage();
		
		//log.info("pageInfo: {}", pageInfo);
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
	public String getPointHistory(@RequestParam(name = "memberId") String memberId,
								  @RequestParam(name="startDate", required=false, defaultValue = "2024-01-01") String startDate,
			                      @RequestParam(name="endDate", required=false, defaultValue = "2024-12-31") String endDate,
			                      Model model, Pageable pageable) {
		
		var pagePointInfo = membersService.getPointHistory(memberId, pageable, startDate, endDate);
		Members memberInfo = membersService.getMemberInfoById(memberId);
		
		List<PointList> pointList = pagePointInfo.getContents();
		int currentPage = pagePointInfo.getCurrentPage();
		int startPageNum = pagePointInfo.getStartPageNum();
		int endPageNum = pagePointInfo.getEndPageNum();
		int lasePage = pagePointInfo.getLastPage();
		
		//log.info("pageInfo: {}", pagePointInfo);
		//log.info("pointList: {}", pointList);
		
		model.addAttribute("pointList", pointList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lasePage", lasePage);
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		
		return "user/mypage/point-history";
	}
	
	// 회원피드내역조회
	@GetMapping("/feedlist")
	public String getFeedList(@RequestParam(name = "memberId") String memberId, Model model) {
		
		Members memberInfo = membersService.getMemberInfoById(memberId);
		System.out.println(memberInfo);
		model.addAttribute("memberInfo", memberInfo);
		return "user/mypage/feed-list";	
	}

	// 회원 특정피드조회
	@GetMapping("/feedList/view" )
	public String getFeedDetail() {
			
		return "user/mypage";
	}
	
	// 회원 영앙제알람체크
	@GetMapping("/medicinearla")
	public String getMedicineArlam() {
		
		return "user/mypage/medicine-arlam";
	}
}
