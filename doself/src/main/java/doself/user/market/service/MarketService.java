package doself.user.market.service;

import java.util.List;

import doself.user.market.controller.PurchaseItem;
import doself.user.market.domain.MarketItem;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MarketService {
	
	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);
	
	// 포인트 구매내역 조회
	PageInfo<PurchaseItem> getPurchaseListById(String memberId, Pageable pageable);
}
