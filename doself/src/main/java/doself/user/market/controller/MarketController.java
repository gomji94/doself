package doself.user.market.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.community.domain.Article;
import doself.user.market.domain.PurchaseItem;
import doself.user.market.service.MarketService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpSession;
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
	
	@GetMapping("/purchase")
	public String createPurchaseItem(@RequestParam(name = "pointItemKeyNum") String pointItemKeyNum, Model model) {
		
		model.addAttribute("itemInfo", marketService.getItemDetail(pointItemKeyNum));
		
		return "user/market/purchase-item";
	}
	
	@GetMapping("/purchaselist")
	public String getPurchaseListById(Pageable pageable, Model model, HttpSession session) {
		
		String memberId = (String) session.getAttribute("SID");
		
		var pageInfo = marketService.getPurchaseListById(memberId, pageable);
		List<PurchaseItem> purchaseList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		log.info("purchaseList : {}", purchaseList);
		
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "user/market/purchase-list";
	}
	
	
	
}
