package doself.user.ticket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import doself.common.mapper.CommonMapper;
import doself.user.members.mapper.MembersMapper;
import doself.user.ticket.domain.Order;
import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.domain.TicketPurchaseInfo;
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
	private final CommonMapper commonMapper;
	private final MembersMapper membersMapper;
	
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

	@Override
	public TicketPurchase getPurchaseById(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TicketPurchaseInfo getPurchaseDetail(String memberId, String paymentNum) {
		return ticketMapper.getPurchaseDetail(paymentNum);
	}

	@Override
	public boolean createTicketOrder(Order order) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		// pmc_code 설정
		String paymentMethod = order.getPaymentMethod();
		switch (paymentMethod) {
			case "card" -> {
				order.setPaymentMethod("pmc_001");
			}
		}
		
		// 티켓결제수단 insert
		String formattedpaymentMethodNum = commonMapper.getPrimaryKey("tpm_", "ticket_payment_method", "tpm_num");
		order.setPaymentMethodNum(formattedpaymentMethodNum);
		int payMethodResult = ticketMapper.createTicketPaymentMethod(order);
		
		// 티켓 결제이력 insert
		if (payMethodResult > 0) {
			TicketItem ticketItem = ticketMapper.getTicketInfoByTicketKey(order.getTicketCode());
			order.setTicketPriceCode(ticketItem.getTicketPriceCode());
			
			int payHistoryResult = ticketMapper.createTicketOrder(order);
			if (payHistoryResult > 0) {
				
				// 티켓 관리 insert
				String formattedticketManagePkNum = commonMapper.getPrimaryKey("ctm_", "challenge_ticket_management", "ctm_code");
				int ticketManageResult = ticketMapper.createTicketManagement(formattedticketManagePkNum, order.getOrderPkValue(), order.getOrdererId());
				
				if (ticketManageResult > 0) {
					
					int modifyTicketCnt = membersMapper.getMemebrTicketCntByIdandTicketCode(order) + 1;
					order.setUpdatedTicketCnt(modifyTicketCnt);
					membersMapper.modifyMemberTicketCnt(order);
					result = true;
				}
				
			}
		}
		
		return result;
	}
	


	
}
