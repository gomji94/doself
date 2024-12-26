package doself.user.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/market")
public class MarketController {
	
	@GetMapping("/itemlist")
	public String getItemList() {
		System.out.println("item-list-controller");
		return "user/market/item-list";
	}
	
	@GetMapping("/purchaselist")
	public String getPurchaseList() {
		System.out.println("purchase-list-controller");
		return "user/market/purchase-list";
	}
	
}
