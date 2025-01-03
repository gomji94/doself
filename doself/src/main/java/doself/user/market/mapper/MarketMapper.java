package doself.user.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.market.domain.MarketItem;

@Mapper
public interface MarketMapper {

	// 마켓 아이템 리스트 조회
	List<MarketItem> getMarketItemList();
	
	
	// 마켓 아이템 상세 조회
	MarketItem getItemDetail(String pointItemKeyNum);
	
}
