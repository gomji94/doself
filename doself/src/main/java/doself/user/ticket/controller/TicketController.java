package doself.user.ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	@GetMapping("/itemlist")
	public String getItemList() {
		
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
