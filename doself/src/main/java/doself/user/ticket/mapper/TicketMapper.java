package doself.user.ticket.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.ticket.domain.TicketItem;

@Mapper
public interface TicketMapper {
	
	List<TicketItem> getTicketList();
	
}
