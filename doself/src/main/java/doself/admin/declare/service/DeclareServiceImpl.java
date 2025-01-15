package doself.admin.declare.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;
import doself.admin.declare.mapper.DeclareMapper;
import doself.common.mapper.CommonMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeclareServiceImpl implements DeclareService{
	
	private final DeclareMapper declareMapper;
	private final CommonMapper commonMapper;
	
	//신고요청 접수 조회
	@Override
	public PageInfo<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "rcName" 	-> searchType = "rc.rc_name";	
			case "scStatus" 	-> searchType = "sc.sc_status";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = declareMapper.getCntDeclareList(searchMap);		
		List<Declare> declareList = declareMapper.getDeclareList(searchMap);
		
		return new PageInfo<>(declareList, pageable, rowCnt);
	}

	//부정회원 관리 조회
	@Override
	public PageInfo<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "mbrName" 	-> searchType = "m.mbr_name";
			case "rcName" 		-> searchType = "rc.rc_name";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = declareMapper.getCntDeclareUserList(searchMap);		
		List<DeclareUser> declareUserList = declareMapper.getDeclareUserList(searchMap);
		
		return new PageInfo<>(declareUserList, pageable, rowCnt);
	}

	// 특정 신고접수요청 조회
	@Override
	public Declare getModifyDeclareByRrNum(String rrNum) {
		
		return declareMapper.getModifyDeclareByRrNum(rrNum);
	}

	//반려 사유
	@Override
	public void modifyDeclare(Declare declare) {
		
		declareMapper.modifyDeclare(declare);
	}

	//부정회원 추가
	@Override
	public void createDeclareUser(Declare declare, int declarePeriod) {
		String newKey = commonMapper.getPrimaryKey("rmm_", "report_member_management", "rmm_num");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("declare", declare);
		searchMap.put("newKey", newKey);
		searchMap.put("declarePeriod", declarePeriod);
		
		// 부정회원관리에 회원정보 insert
		declareMapper.createDeclareUser(searchMap);
		// 신고접수 처리상태 승인으로 변경
		declareMapper.modifyScCode(declare);
		// 멤버등급 부정회원으로 변경
		declareMapper.modifyMgCode(declare);
		
		// 부정회원 등록될때 참여중인 챌린지 탈퇴 처리
		// 참여중인 챌린지가 있는지 조회
		String isData = declareMapper.isDataParticipationChallnege(declare);
		
		// 참여중인 챌린지가 있으면 챌린지 참여자 insert
		if(isData != null || isData != "") {
			Map<String, Object> memberMap = new HashMap<String, Object>();
			// 부정회원 된 멤버 아이디값
			String mbrId= declare.getMbrId();
			// 챌린지 참여자 기본키 생성
			String challengeMemberKey = commonMapper.getPrimaryKey("cgm_", "challenge_group_member", "cgm_num");
			memberMap.put("challengeMemberKey", challengeMemberKey);
			memberMap.put("mbrId", mbrId);
			memberMap.put("cgNum", isData);
			declareMapper.createChallengeMember(memberMap);
		}
	}

	//신고유형별 제제기간 가져오기
	@Override
	public int getDeclarePeriod(String rcCode) {
		
		return declareMapper.getDeclarePeriod(rcCode);
	}

	// 매일자정 제제만료일 확인하여 부정회원 > 일반회원 처리
	@Override
	public void everydayCheck() {
		
		List<DeclareUser> SanctionEndList = declareMapper.getSanctionEndList();
		
		for(DeclareUser ele : SanctionEndList) {
			declareMapper.everydayCheck(ele.getMbrId());		
		}
	}
	
	
}
