package doself.user.ticket.service;

import java.util.List;

import doself.user.ticket.domain.Order;
import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.domain.TicketPurchaseInfo;
import doself.util.PageInfo;
import doself.util.Pageable;


public interface TicketService {
	
	// 티켓 아이템 조회
	List<TicketItem> getTicketList();

	// 티켓 정보
	TicketPurchase getPurchaseById(String memberId);
	
	// 티켓 결제내역 조회
	PageInfo<TicketPurchase> getTicketHistory(String memberId, Pageable pageable, String startDate, String endDate);

	// 티켓 결제내역 
	PageInfo<TicketPurchase> getPurchaseListBySearch(String memberId, Pageable pageable, String dayFilter);

	//티켓 결제상세내역 조회	
	TicketPurchaseInfo getPurchaseDetail(String memberId, String paymentNum);

	// 티켓 주문 생성
	boolean createTicketOrder(Order order);
	
	
}
