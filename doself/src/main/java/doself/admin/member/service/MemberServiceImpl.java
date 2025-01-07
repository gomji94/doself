package doself.admin.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.member.domain.Member;
import doself.admin.member.domain.MemberLog;
import doself.admin.member.mapper.MemberMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	
	//멤버 검색 조회
	@Override
	public PageInfo<Member> getMemberList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "mbrId" 	-> searchType = "m.mbr_id";
			case "mbrName" 	-> searchType = "m.mbr_name";
			case "mgName" 	-> searchType = "mg.mg_name";
			case "acName" 	-> searchType = "ac.ac_name";		
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = memberMapper.getCntMemberList();		
		List<Member> memberList = memberMapper.getMemberList(searchMap);
		
		return new PageInfo<>(memberList, pageable, rowCnt);
    }

	//멤버 로그 검색 조회
	@Override
	public List<MemberLog> getMemberLogList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return memberMapper.getMemberLogList(searchType, searchKeyword, startDate, endDate);
	}

	
}
