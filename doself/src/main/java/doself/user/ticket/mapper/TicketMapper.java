package doself.user.ticket.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.ticket.domain.TicketItem;
import doself.user.ticket.domain.TicketPurchase;
import doself.user.ticket.domain.TicketPurchaseInfo;

@Mapper
public interface TicketMapper {
	
	// 티켓 아이템 조회
	List<TicketItem> getTicketList();
	
	//회원 티켓정보 조회
	//TicketPurchase getPurchaseById(String memberId);
	
	// 티켓 내역 total column
	int getCntTicketHistory(String memberId, String startDate, String endDate);

	// 티켓 상세내역 조회
	List<TicketPurchase> getTicketListById(Map<String, Object> paramMap);

	List<TicketPurchase> getPurchaseListBySearch(Map<String, Object> params);
	
	int getCntOfPurchaseBySearch(String memberId, String dayFilter);
	
	// 티켓 상세정보 조회
	TicketPurchaseInfo getPurchaseDetail(String paymentNum);
	
	// 단일 티켓 정보 조회
	TicketItem getTicketInfoByTicketKey(String ticketKey);
	
}
