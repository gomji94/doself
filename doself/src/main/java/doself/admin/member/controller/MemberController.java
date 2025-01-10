package doself.admin.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.service.MemberService;
import doself.admin.point.domain.Point;
import doself.util.Pageable;
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
	public String getMemberList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		var pageInfo = memberService.getMemberList(searchType, searchKeyword, startDate, endDate, pageable);
		List<Member> memberList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
	
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/member/admin-member-list";
	}
	
	// 회원 정보 수정
	@GetMapping("/modify")
	public String getModifyMember(@RequestParam(name="mbrId") String mbrId, Model model) {

		Member memberInfo = memberService.getMemberInfoByMbrId(mbrId);
		var gradeList = memberService.getMemberMgCodeList();
		var ageList = memberService.getAgeCategoryList();
		
		model.addAttribute("title", "회원 정보 수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("ageList", ageList);
		
		return "admin/member/modify-member";
	}
	// 회원 정보 수정
	@PostMapping("/modify")
	public String modifyMember(Member member, RedirectAttributes reAttr) {

		memberService.modifyMember(member);
		
		reAttr.addAttribute("mbrId",member.getMbrId());
		
		return "redirect:/admin/member/list";
	}
	// 회원 정보 삭제(is_deleted = deleted로 업데이트)
	@GetMapping("/delete")
	public String deleteMember(@RequestParam(value="mbrId") String mbrId) {
		
		memberService.deleteMember(mbrId);
		
		return "redirect:/admin/member/list";
	}
	
	// 로그관리 조회
	@GetMapping("/loglist")
	public String getMemberLog(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {

		
		var pageInfo = memberService.getMemberLogList(searchType, searchKeyword, startDate, endDate, pageable);
		List<MemberLog> memberLogList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();

		model.addAttribute("title", "회원로그목록");
		model.addAttribute("memberLogList", memberLogList);
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/member/log-list";
	}
}
