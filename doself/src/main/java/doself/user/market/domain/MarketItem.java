package doself.user.market.domain;

import lombok.Data;

@Data
public class MarketItem {
	private String pointItemKeyNum;
	private String pointItemName;
	private int pointItemPrice;
	private String pointItemImg;
	private String pointExplanation;
	private int memberPoint;
}
