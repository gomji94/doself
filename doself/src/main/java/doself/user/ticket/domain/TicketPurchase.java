package doself.user.ticket.domain;

import lombok.Data;

@Data
public class TicketPurchase {
	
	private String memeberId;		    //회원아이디
	private String ticketPurchaseDate;  //티켓구매일
	private String ticketCategory;      //티켓분류
	private int    PaymentAmount;       //결제금액
	private String PaymentMethod;       //결제수단
	private String PaymentStatus;       //결제상태
	
}
