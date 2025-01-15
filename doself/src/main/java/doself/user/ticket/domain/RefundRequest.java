package doself.user.ticket.domain;

import lombok.Data;

@Data
public class RefundRequest {
	
	private String paymentNum;   //결제번호
	private String refundNum;    //환불접수번호
	private String ticketNum;    //티켓번호
	private String memberId;     //아이디
	private String refundDate;   //환불접수일자
	
}
