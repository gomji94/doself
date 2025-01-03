package doself.admin.declare.service;

import java.util.List;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;

public interface DeclareService {
	
	//신고접수요청 조회
	List<Declare> getDeclareList();
	
	//부정회원관리 조회
	List<DeclareUser> getDeclareUserList();
}
