package doself.user.market.domain;

import lombok.Data;

@Data
public class PurchaseItem {
	
	private String memberId;
	private String itemName;
	private String itemCode;
	private int itemPrice;
	private String itemPurchaseDate;
	
}
