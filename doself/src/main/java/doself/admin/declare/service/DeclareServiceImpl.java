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
		
		declareMapper.createDeclareUser(searchMap);
		declareMapper.modifyScCode(declare);
		declareMapper.modifyMgCode(declare);		
	}

	//신고유형별 제제기간 가져오기
	@Override
	public int getDeclarePeriod(String rcCode) {
		
		return declareMapper.getDeclarePeriod(rcCode);
	}
	
	
}
