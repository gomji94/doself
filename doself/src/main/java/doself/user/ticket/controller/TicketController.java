package doself.user.ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.user.ticket.service.TicketService;
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
	public String getPurchaseListByDate() {
		
		return "user/ticket/purchase-list";
	}
	
	@GetMapping("/purchasedetail")
	public String getPurchaseDetail() {
		
		return "user/ticket/purchase-detail";
	}
	
}
