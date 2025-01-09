package doself.user.market.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.market.domain.MarketItem;
import doself.user.market.domain.PurchaseItem;
import doself.user.market.domain.PurchaseItemInfo;

@Mapper
public interface MarketMapper {

	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	// 회원 포인트 조회
	int getMemberPointById(String memberId);
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);
	
	// 포인트 구매내역 행 총 갯수 조회
	int getCntOfPurchaseListById(String memberId);
	
	// 포인트 구매내역 조회
	List<PurchaseItem> getPurchaseListById(Map<String, Object> params);
	
	// 마켓 아이템 구매
	int createPurchaseItem(PurchaseItemInfo purchaseItemInfo);
	
	// 마켓 아이템 구매 후 포인트 차감
	void modifyMemberPoint(Map<String, Object> params);
	
}
