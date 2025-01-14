package doself.user.ticket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.mapper.TicketMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TicketServiceImpl implements TicketService{

	private final TicketMapper ticketMapper;
	
	// 티켓 아이템 조회
	@Override
	public List<TicketItem> getTicketList() {
		return ticketMapper.getTicketList();
	}
	
	// 티켓 상세내역 조회
	@Override
	public PageInfo<TicketPurchase> getTicketHistory(String memberId, Pageable pageable, String startDate, String endDate) {
		int rowCnt = ticketMapper.getCntTicketHistory(memberId,startDate,endDate);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("rowPerPage", pageable.getRowPerPage());
		paramMap.put("offset", pageable.getOffset());
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		List<TicketPurchase> ticketList = ticketMapper.getTicketListById(paramMap);
		return new PageInfo<>(ticketList, pageable, rowCnt);
	}

	//티켓 상세내역 검색조회
	@Override
	public PageInfo<TicketPurchase> getPurchaseListBySearch(String memberId, Pageable pageable, String dayFilter) {
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("rowPerPage", pageable.getRowPerPage());
		params.put("offset", pageable.getOffset());
		params.put("dayFilter", dayFilter);
		
		int rowCnt = ticketMapper.getCntOfPurchaseBySearch(memberId, dayFilter);
		
		List<TicketPurchase> ticketList = ticketMapper.getPurchaseListBySearch(params);
		return new PageInfo<>(ticketList, pageable, rowCnt);
	}
	
	// 사용자 티켓정보
	@Override
	public TicketPurchase getPurchaseById(String memberId) {
		return null;
	}

	
}
