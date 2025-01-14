package doself.user.ticket.domain;

import lombok.Data;

@Data
public class PurchaseSearch {
	
	private String dateFilter;
	private Integer days;
}
