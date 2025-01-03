package doself.admin.declare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/declare")
public class DeclareController {
	
	// 신고접수 조회
	@GetMapping("list")
	public String getDeclareList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "신고접수");
		return "admin/declare/admin-declare-list";
	}
	
	// 신고접수상세 수정
	@GetMapping("modifyrequest")
	public String modifyRequest(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "신고접수상세");
		return "admin/declare/modify-declare-list";
	}
	
	// 부정회원관리 조회
	@GetMapping("userlist")
	public String getUserHistoryList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "부정회원관리");
		return "admin/declare/user-list";
	}	
}
