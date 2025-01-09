package doself.admin.point.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class PointUserHistory {
	
	private String pumhNum;
	private String peplNum;
	private String mbrId;
	private String pumhDate;
	
	private Member memberInfo;
	private Point pointInfo;
}
