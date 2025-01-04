package doself.admin.declare.service;

import java.util.List;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;

public interface DeclareService {
	
	//신고접수요청 조회
	List<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//부정회원관리 조회
	List<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate);
}
