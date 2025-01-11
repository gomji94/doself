package doself.user.ticket.domain;

import lombok.Data;

@Data
public class TicketItem {
	
	private String ticketCode;         //티켓분류코드
	private String ticketCategory;     //티켓분류
	private int ticketPrice;           //티켓가격
	private String ticketExplanation;  //티켓설명
	
}
