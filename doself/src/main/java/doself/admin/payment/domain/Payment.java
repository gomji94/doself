package doself.admin.payment.domain;

import lombok.Data;

@Data
public class Payment {
	
	private String ctphNum;
	private String mbrId;
	private String tpmNum;
	private String ctphDate;
	private String ctpCode;
	private String ctphStatus;
	private String ctphReturnStatus;
}
