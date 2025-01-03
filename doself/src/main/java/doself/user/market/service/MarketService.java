package doself.user.market.service;

import java.util.List;

import doself.user.market.domain.MarketItem;

public interface MarketService {
	
	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);

}
