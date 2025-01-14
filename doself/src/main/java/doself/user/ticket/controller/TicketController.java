package doself.user.ticket.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.domain.TicketPurchaseInfo;
import doself.user.ticket.service.TicketService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Slf4j
public class TicketController {
	
	private final TicketService ticketService;
	
	@GetMapping("/itemlist")
	public String getItemList(Model model) {
			
		model.addAttribute("ticketList", ticketService.getTicketList());
		return "user/ticket/item-list";
	}
	
	@GetMapping("/purchaselist")
	public String getPurchaseList(HttpSession session,
										@RequestParam(name="startDate", required=false) String startDate,
									    @RequestParam(name="endDate", required=false) String endDate,
									    Model model, Pageable pageable) {
		
		String memberId = (String) session.getAttribute("SID");
		var pageTicketInfo = ticketService.getTicketHistory(memberId, pageable, startDate, endDate);
		
		List<TicketPurchase> ticketPurchase = pageTicketInfo.getContents();
		int currentPage = pageTicketInfo.getCurrentPage();
		int startPageNum = pageTicketInfo.getStartPageNum();
		int endPageNum = pageTicketInfo.getEndPageNum();
		int lastPage = pageTicketInfo.getLastPage();
		//log.info("ticketPurchase: {}",ticketPurchase);
		model.addAttribute("ticketPurchase", ticketPurchase);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "user/ticket/purchase-list";
	}
	
	@GetMapping("/purchaselist/search")
	public String getPurchaseListBySearch(@RequestParam(value = "dayFilter") String dayFilter, 
											Pageable pageable, Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		var pageInfo = ticketService.getPurchaseListBySearch(memberId, pageable, dayFilter);
		List<TicketPurchase> ticketPurchase = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		//log.info("ticketPurchase: {}",ticketPurchase);
		model.addAttribute("ticketPurchase", ticketPurchase);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "user/ticket/purchase-list";
	}
	
	
	@GetMapping("/purchasedetail/view")
	public String getPurchaseDetail(@RequestParam(name="paymentNum") String paymentNum,
									Model model, HttpSession session) {
		
		String memberId = (String) session.getAttribute("SID");
		TicketPurchaseInfo ditailInfo = ticketService.getPurchaseDitail(memberId, paymentNum);
		
		model.addAttribute("ditailInfo", ditailInfo);
		
		return "user/ticket/purchase-detail";
	}
	
}
