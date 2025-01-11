package doself.user.members.service;

import java.util.List;
import java.util.Map;

import doself.user.members.domain.Members;
import doself.user.members.domain.PointList;
import doself.user.members.domain.TicketList;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface MembersService {

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	// 회원수정
	public void modifyMemberById(String memberId, List<String> memberEmail, List<String> memberPhone, Members memberInfo);
	
	//회원 검증
	public Map<String, Object> matchedMember(String memberId, String memberPw);

	// 회원탈퇴
	public void removeMemberById(String memberId);

	//회원 티켓내역 조회
	public PageInfo<TicketList> getTicketHistory(String memberId, Pageable pageable, String startDate, String endDate);

	//회원 포인트내역 조회
	public PageInfo<PointList> getPointHistory(String memberId, Pageable pageable, String startDate, String endDate);


	


	
	
	
	
	
}
