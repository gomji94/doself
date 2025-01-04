package doself.admin.declare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getDeclareList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<Declare> declareList = declareService.getDeclareList(searchType, searchKeyword, startDate, endDate);
		
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
	public String getUserHistoryList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<DeclareUser> declareUserList = declareService.getDeclareUserList(searchType, searchKeyword, startDate, endDate);
		
		model.addAttribute("title", "부정회원관리");
		model.addAttribute("declareUserList", declareUserList);
		return "admin/declare/user-list";
	}	
}
