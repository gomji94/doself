package doself.user.ticket.domain;

import lombok.Data;

@Data
public class TicketPurchaseInfo {

	private String memeberId;		    //회원아이디
	private String ticketCategory;      //티켓분류(이름)
	private String ticketNum;           //티켓코드	
	private int    paymentAmount;       //결제금액
	private String ticketPurchaseDate;  //티켓구매일
	private String paymentNum;          //결제번호
	private String orderNum;            //주문번호
	private String paymentMethod;       //결제수단
	private String paymentStatus;       //결제상태
	private String paymentReturnStatus; //환불상태
	
}
