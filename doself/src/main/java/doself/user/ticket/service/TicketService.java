package doself.user.ticket.service;

import java.util.List;

import doself.user.ticket.domain.TicketItem;


public interface TicketService {
	
	// 티켓 아이템 조회
	List<TicketItem> getTicketList();
	
	
	
	
}
