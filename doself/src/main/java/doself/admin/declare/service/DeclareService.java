package doself.admin.declare.service;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.domain.DeclareUser;
import doself.admin.nutrition.domain.Nutrition;
import doself.admin.nutrition.domain.NutritionInfo;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface DeclareService {
	
	//신고접수요청 조회
	PageInfo<Declare> getDeclareList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//부정회원관리 조회
	PageInfo<DeclareUser> getDeclareUserList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//특정 신고접수요청 조회
	Declare getModifyDeclareByRrNum(String rrNum);
	//반려
	void modifyDeclare(Declare declare);

	//부정회원 추가
	void createDeclareUser(Declare declare, int declarePeriod);
	//신고유형별 제제기간 가져오기
	int getDeclarePeriod(String rcCode);
}
