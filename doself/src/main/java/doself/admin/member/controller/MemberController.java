package doself.admin.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	// 회원관리 조회
	@GetMapping("/list")
	public String getMemberList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<Member> memberList = memberService.getMemberList(searchType, searchKeyword, startDate, endDate);
		
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "admin/member/admin-member-list";
	}
	
	// 회원 정보 수정
	@GetMapping("/modifymember")
	public String modifyMember(Model model) {
		
		model.addAttribute("title", "회원 정보 수정");
		return "admin/member/modify-member";
	}
	
	// 로그관리 조회
	@GetMapping("/loglist")
	public String getMemberLog(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<MemberLog> memberLogList = memberService.getMemberLogList(searchType, searchKeyword, startDate, endDate);
		model.addAttribute("title", "회원로그목록");
		model.addAttribute("memberLogList", memberLogList);
		
		return "admin/member/log-list";
	}
}
