package doself.admin.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/point")
public class PointController {

	// 포인트 상품 조회
	@GetMapping("list")
	public String getPointList(Model model) {

		model.addAttribute("title", "포인트 상품");
		return "admin/point/admin-point-list";
	}
	
	// 회원별 포인트 사용 내역 조회
	@GetMapping("userhistorylist")
	public String getUserHistoryList(Model model) {
		
		model.addAttribute("title", "회원별 사용 내역");
		return "admin/point/user-history-list";
	}
	
	// 포인트 상품 생성
	@GetMapping("create")
	public String createPointList(Model model) {

		model.addAttribute("title", "상품 추가");
		return "admin/point/create-point-list";
	}
	
	// 포인트 상품 수정
	@GetMapping("modify")
	public String modifyPointList(Model model) {

		model.addAttribute("title", "상품 수정");
		return "admin/point/modify-point-list";
	}
	
}
