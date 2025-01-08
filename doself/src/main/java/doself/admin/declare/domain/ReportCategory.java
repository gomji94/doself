package doself.admin.declare.domain;

import lombok.Data;

@Data
public class ReportCategory {
	
	private String rcCode;
	private String rcName;
	private int rcSanctionPeriod;
}
