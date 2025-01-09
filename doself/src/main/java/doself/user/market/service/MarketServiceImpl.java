package doself.user.market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.common.mapper.CommonMapper;
import doself.user.market.domain.MarketItem;
import doself.user.market.domain.PurchaseItem;
import doself.user.market.domain.PurchaseItemInfo;
import doself.user.market.mapper.MarketMapper;
import doself.util.CodeGenerator;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
	
	private final MarketMapper marketMapper;
	private final CommonMapper commonMapper;

	@Override
	public List<MarketItem> getMarketItemList() {
		// TODO Auto-generated method stub
		return marketMapper.getMarketItemList();
	}
	
	@Override
	public int getMemberPointById(String memberId) {
		// TODO Auto-generated method stub
		return marketMapper.getMemberPointById(memberId);
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
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("pageable", pageable);
		List<PurchaseItem> purchaseList = marketMapper.getPurchaseListById(params);
		
		return new PageInfo<>(purchaseList, pageable, rowCnt);
	}

	@Override
	public int createPurchaseItem(PurchaseItemInfo purchaseItemInfo) {
		// TODO Auto-generated method stub
		String itemKey = purchaseItemInfo.getPointItemKeyNum();
		String memberId = purchaseItemInfo.getMemberId();
		int currentMemberPoint = marketMapper.getMemberPointById(memberId);
		int calculatedPoint = currentMemberPoint - purchaseItemInfo.getInputPointValue();
		
		if (!(itemKey.equals("pepl_001") || itemKey.equals("pepl_002"))) {
			String generatedItemCode = CodeGenerator.generateCode(itemKey);
			purchaseItemInfo.setPurchaseItemCode(generatedItemCode);
		}
		
		String formattedKeyNum = commonMapper.getPrimaryKey("pumh_", "point_use_management_history", "pumh_num");
		purchaseItemInfo.setRequestTableLastPkNum(formattedKeyNum);
		
		int result = marketMapper.createPurchaseItem(purchaseItemInfo);
		
		if (result > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", memberId);
			params.put("calculatedPoint", calculatedPoint);
			marketMapper.modifyMemberPoint(params);
		}
		
		return result;
	}

	

}
