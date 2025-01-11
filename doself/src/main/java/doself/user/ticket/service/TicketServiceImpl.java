package doself.user.ticket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.mapper.TicketMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TicketServiceImpl implements TicketService{

	private final TicketMapper ticketMapper;
	
	@Override
	public List<TicketItem> getTicketList() {
		
		
		return ticketMapper.getTicketList();
	}
	
}
