package doself.user.ticket.domain;

import lombok.Data;

@Data
public class Order {
	
	private String orderPkValue;
	private String ordererId;
	private String ticketCode;
	private String ticketPriceCode;
	private String paymentMethodNum;
	private String paymentMethod;
	private String paymentCardName;
	private String paymentCardNum;
	private String paymentUniqueValue;
	private String paymentDate;
	private int updatedTicketCnt;
	
}
