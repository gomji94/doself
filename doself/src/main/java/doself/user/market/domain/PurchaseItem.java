package doself.user.market.domain;

import lombok.Data;

@Data
public class PurchaseItem {
	
	private String memberId;
	private String itemName;
	private String itemCode;
	private String itemPrice;
	private String itemPurchaseDate;
	
	// 상품코드 테이블에 추가해야함
}
