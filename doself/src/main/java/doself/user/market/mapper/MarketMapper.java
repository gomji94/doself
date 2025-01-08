package doself.user.market.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.market.domain.MarketItem;
import doself.user.market.domain.PurchaseItem;

@Mapper
public interface MarketMapper {

	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);
	
	// 포인트 구매내역 행 총 갯수 조회
	int getCntOfPurchaseListById(String memberId);
	
	// 포인트 구매내역 조회
	List<PurchaseItem> getPurchaseListById(Map<String, Object> params);
	
}
