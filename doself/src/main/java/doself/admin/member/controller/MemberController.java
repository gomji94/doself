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

@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	// 회원관리 조회
	@GetMapping("/list")
	public String getMemberList(HttpServletRequest request, Model model) {
		
		List<MemberDTO> memberList = memberService.getMemberList();
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "admin/index";
	}
	
	// 회원 정보 수정
	@GetMapping("/modifymember")
	public String modifyMember(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원 정보 수정");
		return "admin/member/modify-member";
	}
	
	// 로그관리 조회
	@GetMapping("/loglist")
	public String getMemberLog(HttpServletRequest request, Model model) {

		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원로그목록");
		
		return "admin/member/log-list";
	}
}
