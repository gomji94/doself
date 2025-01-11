package doself.user.ticket.mapper;

import java.util.List;

import doself.user.ticket.domain.TicketItem;

public interface TicketMapper {
	
	List<TicketItem> getTicketList();
	
}
