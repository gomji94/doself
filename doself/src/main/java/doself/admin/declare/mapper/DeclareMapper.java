package doself.admin.declare.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;

@Mapper
public interface DeclareMapper {
	
	//신고요청 접수 조회
	List<Declare> getDeclareList(Map<String, Object> searchMap);
	//신고요청 접수 개수
	int getCntDeclareList(Map<String, Object> searchMap);
	
	//부정회원관리 조회
	List<DeclareUser> getDeclareUserList(Map<String, Object> searchMap);
	//부정회원관리 개수
	int getCntDeclareUserList(Map<String, Object> searchMap);
	
	//특정 신고요청 조회
	Declare getModifyDeclareByRrNum(String rrNum);
	
	//반려사유작성
	int modifyDeclare(Declare declare);
	
	//부정회원추가
	void createDeclareUser(Map<String, Object> searchMap);
	
	//신고유형별 제제기간 가져오기
	int getDeclarePeriod(String rcCode);
	
	// 부정회원추가후 처리상태 변경
	int modifyScCode(Declare declare);
}
