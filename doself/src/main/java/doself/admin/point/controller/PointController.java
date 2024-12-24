package doself.admin.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/point")
public class PointController {

	@GetMapping("list")
	public String pointList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "포인트 상품");
		return "admin/point/admin-point-list";
	}
	
	@GetMapping("userhistorylist")
	public String userHistoryList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "회원별 사용 내역");
		return "admin/point/user-list";
	}
}
