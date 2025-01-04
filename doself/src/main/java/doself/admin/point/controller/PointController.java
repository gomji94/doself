package doself.admin.point.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;
import doself.admin.point.service.PointService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin/point")
@RequiredArgsConstructor
public class PointController {

	private final PointService pointService;
	
	// 포인트 상품 조회
	@GetMapping("list")
	public String getPointList(Model model) {
		
		List<Point> pointList = pointService.getPointList();
		
		model.addAttribute("title", "포인트 상품");
		model.addAttribute("pointList", pointList);
		return "admin/point/admin-point-list";
	}
	
	// 회원별 포인트 사용 내역 조회
	@GetMapping("userhistorylist")
	public String getUserHistoryList(@RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<PointUserHistory> pointUserHistoryList = pointService.getPointUserHistoryList(startDate, endDate);
		model.addAttribute("title", "회원별 사용 내역");
		model.addAttribute("pointUserHistoryList", pointUserHistoryList);
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
