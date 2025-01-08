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
import doself.admin.nutrition.domain.Nutrition;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/declare")
@RequiredArgsConstructor
public class DeclareController {
	
	private final DeclareService declareService;
	
	// 신고접수 조회
	@GetMapping("/list")
	public String getDeclareList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		var pageInfo = declareService.getDeclareList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Declare> declareList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "신고접수");
		model.addAttribute("declareList", declareList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/declare/admin-declare-list";
	}
	
	// 신고접수상세 수정
	@GetMapping("/modifyrequest")
	public String modifyRequest(Model model) {

		model.addAttribute("title", "신고접수상세");
		return "admin/declare/modify-declare-list";
	}
	
	// 부정회원관리 조회
	@GetMapping("/userlist")
	public String getUserHistoryList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		var pageInfo = declareService.getDeclareUserList(searchType, searchKeyword, startDate, endDate, pageable);
		List<DeclareUser> declareUserList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "부정회원관리");
		model.addAttribute("declareUserList", declareUserList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/declare/user-list";
	}	
}
