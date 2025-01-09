package doself.user.market.service;

import java.util.List;

import doself.user.market.domain.MarketItem;
import doself.user.market.domain.PurchaseItem;
import doself.user.market.domain.PurchaseItemInfo;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MarketService {
	
	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	// 회원 포인트 조회
	int getMemberPointById(String memberId);
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);
	
	// 마켓 아이템 구매
	int createPurchaseItem(PurchaseItemInfo purchaseItemInfo);
	
	// 포인트 구매내역 조회
	PageInfo<PurchaseItem> getPurchaseListById(String memberId, Pageable pageable);
}
