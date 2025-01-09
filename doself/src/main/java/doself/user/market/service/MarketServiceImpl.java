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
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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
		
		// 회원 포인트 조회 및 차감 포인트 계산
		int currentMemberPoint = marketMapper.getMemberPointById(memberId);
		int calculatedPoint = currentMemberPoint - purchaseItemInfo.getItemPrice();
		
		// 참여티켓 또는 개설티켓이 아닐시 상품 코드 생성
		if (!(itemKey.equals("pepl_001") || itemKey.equals("pepl_002"))) {
			String generatedItemCode = CodeGenerator.generateCode(itemKey);
			purchaseItemInfo.setPurchaseItemCode(generatedItemCode);
		}
		
		// DB insert 키값 생성
		String formattedKeyNum = commonMapper.getPrimaryKey("pumh_", "point_use_management_history", "pumh_num");
		purchaseItemInfo.setRequestTableLastPkNum(formattedKeyNum);
		
//		// 구매내역 insert
//		int result = marketMapper.createPurchaseItem(purchaseItemInfo);
//		
//		// 결과에 따라 포인트 차감 업데이트
//		if (result > 0) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("memberId", memberId);
//			params.put("calculatedPoint", calculatedPoint);
//			marketMapper.modifyMemberPoint(params);
//		}
		
		return 0;
	}

	

}
