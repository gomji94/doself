package doself.admin.point.service;

import java.util.List;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface PointService {

	//포인트 상품목록 조회
	List<Point> getPointList();
	
	//포인트 사용내역 조회
	PageInfo<PointUserHistory> getPointUserHistoryList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	//포인트 상품 추가
	void createPointList(Point point);
	
	//특정 포인트 상품 조회
	Point getPointInfoByPeplNum(String peplNum);
	
	//특정 포인트 상품 정보 수정
	void modifyPoint(Point point);
	
	//포인트상품 삭제
	void deletePointList(String peplNum);
	//포인트상품 마지막번호 찾은후 다음키생성
	String pointLastNum();
	
}
