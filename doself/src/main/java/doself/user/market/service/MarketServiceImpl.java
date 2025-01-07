package doself.user.market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.market.controller.PurchaseItem;
import doself.user.market.domain.MarketItem;
import doself.user.market.mapper.MarketMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
	
	private final MarketMapper marketMapper;

	@Override
	public List<MarketItem> getMarketItemList() {
		// TODO Auto-generated method stub
		return marketMapper.getMarketItemList();
	}

	@Override
	public MarketItem getItemDetail(String pointItemKeyNum) {
		// TODO Auto-generated method stub
		return marketMapper.getItemDetail(pointItemKeyNum);
	}

	@Override
	public PageInfo<PurchaseItem> getPurchaseListById(String memberId, Pageable pageable) {
		// TODO Auto-generated method stub
		
		int rowCnt = marketMapper.getCntOfPurchaseListById(memberId);
		System.out.println("=======> " + rowCnt);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("pageable", pageable);
		List<PurchaseItem> purchaseList = marketMapper.getPurchaseListById(params);
		
		return new PageInfo<>(purchaseList, pageable, rowCnt);
	}
	

}
