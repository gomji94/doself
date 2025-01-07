package doself.user.members.domain;

import lombok.Data;

@Data
public class PointList {
	
	private String memberId;
	private String pointTotalDate;  //적립,사용날자
	private String pointDetail; 	//내용
	private String pointUseChk;     //사용여부
	private String unUsePoint;      //잔여포인트
	
	
	
	
}
