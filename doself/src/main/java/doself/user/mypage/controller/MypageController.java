package doself.user.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	// 회원정보
	@GetMapping("/member/info")
	public String getMemberInfo() {
		
		return "user/mypage/info";
	}
	
	// 회원정보수정
	@PostMapping("/member/modify" )
	public String modifyUser() {
			
		return "user/mypage/";
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
