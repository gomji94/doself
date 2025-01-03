package doself.user.market.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.market.domain.MarketItem;
import doself.user.market.mapper.MarketMapper;
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
	

}
