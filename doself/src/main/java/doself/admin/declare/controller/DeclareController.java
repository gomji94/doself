package doself.admin.declare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;
import doself.admin.declare.service.DeclareService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/declare")
@RequiredArgsConstructor
public class DeclareController {
	
	private final DeclareService declareService;
	
	// 신고접수 조회
	@GetMapping("list")
	public String getDeclareList(Model model) {

		List<Declare> declareList = declareService.getDeclareList();
		
		model.addAttribute("title", "신고접수");
		model.addAttribute("declareList", declareList);
		return "admin/declare/admin-declare-list";
	}
	
	// 신고접수상세 수정
	@GetMapping("modifyrequest")
	public String modifyRequest(Model model) {

		model.addAttribute("title", "신고접수상세");
		return "admin/declare/modify-declare-list";
	}
	
	// 부정회원관리 조회
	@GetMapping("userlist")
	public String getUserHistoryList(Model model) {
		
		List<DeclareUser> declareUserList = declareService.getDeclareUserList();
		
		model.addAttribute("title", "부정회원관리");
		model.addAttribute("declareUserList", declareUserList);
		return "admin/declare/user-list";
	}	
}
