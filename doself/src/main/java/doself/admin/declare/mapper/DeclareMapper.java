package doself.admin.declare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;

@Mapper
public interface DeclareMapper {
	
	//신고요청 접수 조회
	List<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate);
	
	//부정회원관리 조회
	List<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate);
}
