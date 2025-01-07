package doself.user.members.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.members.domain.Members;
import doself.user.members.domain.TicketList;
import doself.util.Pageable;

@Mapper
public interface MembersMapper {
	// 회원탈퇴
	public int removeMemberById(String memberId);

	//회원 정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 수정
	public int modifyMember(Members member);
	
	public boolean passwordChk(String memberId, String oldMemberPw);
	
	public boolean updatePassword(String memberId, String newMemberPw);

	// 특정회원 탈퇴
	public int removeLoginHistoryById(String memberId);

	//회원 티켓정보 조회
	public List<TicketList> getTicketListById(Map<String, Object> paramMap);
	
	//회원 티켓이력 총 갯수
	int getCntTicketHistory(String memberId,String startDate, String endDate);
	
	
	
	
	
}
