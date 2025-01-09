package doself.user.members.domain;

import lombok.Data;

@Data
public class TicketList {
	
	private String memberId;
	private String ticketBuyDate;
	private String ticketName;
	private String ticketPaymentMethod;
	private String ticketUseChk;
	private String ticketReturnChk;
	private String ticketUsedCnt;
	private String ticketNotUsedCnt;
	
	
	
	
}
