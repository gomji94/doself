package doself.admin.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.admin.member.domain.MemberDTO;
import doself.admin.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
	public String getMemberList(Model model) {
		
		List<MemberDTO> memberList = memberService.getMemberList();
		log.info("memberList : {}", memberList);
		
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
	public String getMemberLog(Model model) {

		model.addAttribute("title", "회원로그목록");
		
		return "admin/member/log-list";
	}
}
