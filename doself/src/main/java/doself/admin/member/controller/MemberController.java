package doself.admin.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/member")
public class MemberController {
	
	// 회원관리 조회
	@GetMapping("/list")
	public String getMemberList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원목록");
		return "admin/index";
	}
	// 로그관리 조회
	@GetMapping("/loglist")
	public String getMemberLog(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원로그목록");
		return "admin/member/log-list";
	}
}
