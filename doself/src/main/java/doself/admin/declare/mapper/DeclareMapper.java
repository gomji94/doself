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
	int getCntDeclareList();
	
	//부정회원관리 조회
	List<DeclareUser> getDeclareUserList(Map<String, Object> searchMap);
	//부정회원관리 개수
	int getCntDeclareUserList();
}
