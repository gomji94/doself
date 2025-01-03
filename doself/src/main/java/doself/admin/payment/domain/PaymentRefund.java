package doself.admin.payment.domain;

import lombok.Data;

@Data
public class PaymentRefund {

	private String prlNum;
	private String prrNum;
	private String mbrId;
	private String prlDate;	
	private int prlAmount;
}
