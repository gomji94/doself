package doself.admin.payment.domain;

import lombok.Data;

@Data
public class TicketPrice {
	
	private String ctpCode;
	private int ctpPrice;
	private String ctcCategory;
}
