package doself.admin.declare.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;
import doself.admin.declare.mapper.DeclareMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeclareServiceImpl implements DeclareService{
	
	private final DeclareMapper declareMapper;
	
	//신고요청 접수 조회
	@Override
	public List<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return declareMapper.getDeclareList(searchType, searchKeyword, startDate, endDate);
	}

	//부정회원 관리 조회
	@Override
	public List<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return declareMapper.getDeclareUserList(searchType, searchKeyword, startDate, endDate);
	}
	
}
