package doself.user.ticket.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.common.mapper.CommonMapper;
import doself.user.ticket.domain.Order;
import doself.user.ticket.domain.RefundRequest;
import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.domain.TicketPurchaseInfo;
import doself.user.ticket.mapper.TicketMapper;
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
	private final TicketMapper ticketMapper;
	private final CommonMapper commonMapper;
	
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
		
		//구매일기준 7일초과 환불신청가능창 비활성화
		TicketPurchaseInfo detailInfo = ticketService.getPurchaseDetail(memberId, paymentNum);
		String ticketPurchaseDate = detailInfo.getTicketPurchaseDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(ticketPurchaseDate, formatter);
		LocalDateTime dateTimeAfterWeek = dateTime.plusDays(7);
		LocalDateTime dateTimeNow = LocalDateTime.now();
		boolean timeLapse = dateTimeAfterWeek.isAfter(dateTimeNow);
		
		
		model.addAttribute("detailInfo", detailInfo);
		model.addAttribute("timeLapse", timeLapse);
		
		return "user/ticket/purchase-detail";
	}
	
	@GetMapping("/purchasedetail/refund")
	public String getPurchaseRefund(@RequestParam(name="paymentNum") String paymentNum, Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		
		TicketPurchaseInfo detailInfo = ticketService.getPurchaseDetail(memberId, paymentNum);
		model.addAttribute("detailInfo", detailInfo);
		
		return "user/ticket/purchase-detail-refund";
	}
	
	@PostMapping("/purchasedetail/refund")
	@ResponseBody
	public boolean purchaseRefund(RefundRequest refundRequest, HttpSession session) {
		
		boolean isReg = false;
		
		refundRequest.setMemberId((String) session.getAttribute("SID"));
		refundRequest.setRefundRequestPkValue(commonMapper.getPrimaryKey("prr_", "payment_refund_request", "prr_num"));
		int result = ticketMapper.createRefundRequest(refundRequest);
		if(result > 0) isReg = true; 
		
		return isReg;
	}
	
	@PostMapping("/purchasedetail/isCheck")
	@ResponseBody
	public boolean isCheckDuplicate(String ticketNum, RedirectAttributes reAttr) {
		boolean isCheck = false;
		int result = ticketMapper.cntDuplicate(ticketNum);
		if(result> 0) isCheck = true;
		return isCheck;
	}
	
	@PostMapping("/payment")
	@ResponseBody
	public Map<String, Object> paymentTest(String ticketKey, HttpSession session) {
		
		String memberId = (String) session.getAttribute("SID");
		String orderKeyValue =  commonMapper.getPrimaryKey("ctph_", "challenge_ticket_payment_history", "ctph_num");
		TicketItem ticketInfo = ticketMapper.getTicketInfoByTicketKey(ticketKey);
		session.setAttribute("STK", ticketKey);
		
		Map<String, Object> preOrderData = new HashMap<String, Object>();
		preOrderData.put("memberId", memberId);
		preOrderData.put("orderKeyValue", orderKeyValue);
		preOrderData.put("ticketPrice", ticketInfo.getTicketPrice());
		preOrderData.put("ticketName", ticketInfo.getTicketCategory());
		
		return preOrderData;
	}
	
	@PostMapping("/payment/result")
	@ResponseBody
	public boolean paymentResult(Order order, HttpSession session) {
		
		String ticketCode = (String) session.getAttribute("STK");
		order.setTicketCode(ticketCode);
		//System.out.println("=-======================> 확인용 " + order);
		
		return ticketService.createTicketOrder(order);
	}
	
	
}
