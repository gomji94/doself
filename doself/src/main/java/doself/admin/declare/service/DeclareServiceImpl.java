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
	public List<Declare> getDeclareList() {
		
		return declareMapper.getDeclareList();
	}

	//부정회원 관리 조회
	@Override
	public List<DeclareUser> getDeclareUserList() {
		
		return declareMapper.getDeclareUserList();
	}
	
}
