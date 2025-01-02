package doself.user.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
@Slf4j
public class MarketController {
	
	private final MarketService marketService;
	
	@GetMapping("/itemlist")
	public String getItemList(Model model) {
		
		model.addAttribute("itemList", marketService.getMarketItemList());
		
		return "user/market/item-list";
	}
	
	@GetMapping("/view")
	public String getItemDetail(@RequestParam(name = "pointItemKeyNum") String pointItemKeyNum, Model model) {
		
		model.addAttribute("itemInfo", marketService.getItemDetail(pointItemKeyNum));
		
		return "user/market/view";
	}
	
	
	
	@GetMapping("/purchaselist")
	public String getPurchaseList() {
		return "user/market/purchase-list";
	}
	
}
