package doself.user.ticket.domain;

import lombok.Data;

@Data
public class RefundRequest {
	
	private String refundRequestPkValue; // 환불 테이블 pk값
	private String ticketNum;    //티켓번호
	private String memberId;     //아이디
	
}
