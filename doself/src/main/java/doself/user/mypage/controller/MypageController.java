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
		
		return "";
	}
	
	// 회원정보수정
	@PostMapping("/member/modify" )
	public String modifyUser() {
			
		return "";
	}
	
	// 회원탈퇴
	@PostMapping("/member/delete" )
	public String deleteMember() {
			
		return "";
	}	
	
	// 회원티켓내역조회
	@GetMapping("/member/tickethistory" )
	public String getTicketHistory() {
			
		return "";
	}
	
	// 회원티켓내역검색
	@GetMapping("/member/tickethistory/search" )
	public String getTicketHistoryByDate() {
			
		return "";
	}
	// 회원포인트내역조회
	@GetMapping("/member/pointhistory" )
	public String getPointHistory() {
		
		return "";
	}
	
	// 회원포인트내역검색
	@GetMapping("/member/pointhistory/search" )
	public String getPointHistoryByDate() {
		
		return "";
	}
	
	// 회원피드내역조회
	@GetMapping("/member/feedList" )
	public String getFeedList() {
		
		return "";
	}

	// 회원 특정피드조회
	@GetMapping("/member/feedList/view" )
	public String getFeedDetail() {
		
		return "";
	}
}
