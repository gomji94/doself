package doself.admin.declare.service;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface DeclareService {
	
	//신고접수요청 조회
	PageInfo<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//부정회원관리 조회
	PageInfo<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
}
