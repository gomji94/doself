package doself.admin.point.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;
import doself.admin.point.service.PointService;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/admin/point")
@RequiredArgsConstructor
@Slf4j
public class PointController {

	private final PointService pointService;
	
	// 포인트 상품 조회
	@GetMapping("/list")
	public String getPointList(Model model) {
		
		List<Point> pointList = pointService.getPointList();
		
		model.addAttribute("title", "포인트 상품");
		model.addAttribute("pointList", pointList);
		return "admin/point/admin-point-list";
	}
	
	// 회원별 포인트 사용 내역 조회
	@GetMapping("/userhistorylist")
	public String getUserHistoryList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate, Model model, Pageable pageable) {
		
		var pageInfo = pointService.getPointUserHistoryList(searchType, searchKeyword, startDate, endDate, pageable);
		List<PointUserHistory> pointUserHistoryList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("title", "회원별 사용 내역");
		model.addAttribute("pointUserHistoryList", pointUserHistoryList);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "admin/point/user-history-list";
	}
	
	// 포인트 상품 생성
	@GetMapping("/create")
	public String createPointListView(Model model) {
		String newKey = pointService.pointLastNum();
		model.addAttribute("title", "상품 추가");
		model.addAttribute("newKey", newKey);
		log.info("newKey : {}",newKey);
		return "admin/point/create-point-list";
	}
	@PostMapping("/create")
	public String createPointList(Point point) {
		
		pointService.createPointList(point);
		return "redirect:/admin/point/list";
	}
	
	// 포인트 상품 수정
	@GetMapping("/modify")
	public String getModifyPointList(@RequestParam(name="peplNum") String peplNum, Model model) {

		Point pointInfo = pointService.getPointInfoByPeplNum(peplNum);
		log.info("peplNum : {}",peplNum);
		model.addAttribute("title", "상품 수정");
		model.addAttribute("pointInfo", pointInfo);
		log.info("pointInfo : {}",pointInfo);
		return "admin/point/modify-point-list";
	}
	
	@PostMapping("/modify")
	public String modifyPointList(Point point, RedirectAttributes reAttr) {

		pointService.modifyPoint(point);
		reAttr.addAttribute("peplNum",point.getPeplNum());
		
		return "redirect:/admin/point/list";
	}
	
	@GetMapping("/delete")
	public String deletePointList(@RequestParam(value="peplNum") String peplNum, RedirectAttributes reAttr) {
		pointService.deletePointList(peplNum);
		
		return "redirect:/admin/point/list";
	}
	
}
