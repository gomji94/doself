package doself.user.members.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.members.domain.FeedList;
import doself.user.members.domain.Members;
import doself.user.members.domain.PointList;
import doself.user.members.domain.TicketList;
import doself.user.ticket.domain.Order;

@Mapper
public interface MembersMapper {

	//회원 수정정보 조회
	public Members getMemberInfoById(String memberId);
	
	//회원 수정 
	public int modifyMember(Members member);

	//회원 탈퇴
	public int removeMemberById(String memberId);

	//회원 티켓정보 조회
	public List<TicketList> getTicketListById(Map<String, Object> paramMap);
	
	//회원 티켓이력 총 갯수
	int getCntTicketHistory(String memberId,String startDate, String endDate);
	
	//회원 포인트정보 조회
	public List<PointList> getPointListById(Map<String, Object> paramMap);
	
	//회원 포인트이력 총 갯수
	int getCntPointHistory(String memberId, String startDate, String endDate);
	
	//회원 티켓 갯수 조회
	int getMemebrTicketCntByIdandTicketCode(Order order);
	
	//결제 후 회원 티켓 갯수 업데이트
	int modifyMemberTicketCnt(Order order);
	
	//회원 피드리스트 조회
	public List<FeedList> getMemberFeedListById(String memberId);
	
}
