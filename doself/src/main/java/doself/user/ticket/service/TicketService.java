package doself.user.ticket.service;

import java.util.List;

import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.util.PageInfo;
import doself.util.Pageable;


public interface TicketService {
	
	// 티켓 아이템 조회
	List<TicketItem> getTicketList();

	// 티켓 상세내역 조회
	PageInfo<TicketPurchase> getTicketHistory(String memberId, Pageable pageable, String startDate, String endDate);

	// 티켓 정보
	TicketPurchase getPurchaseById(String memberId);

	//티켓 결제 상세내역 조회
	
	
	
}
