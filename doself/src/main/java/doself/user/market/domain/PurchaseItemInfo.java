package doself.user.market.domain;

import lombok.Data;

@Data
public class PurchaseItemInfo {
	
	private String memberId;
	private String pointItemKeyNum;
	private int inputPointValue;
	private String purchaseItemCode;
	private String requestTableLastPkNum;
	
}
