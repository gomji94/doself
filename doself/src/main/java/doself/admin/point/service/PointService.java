package doself.admin.point.service;

import java.util.List;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;

public interface PointService {

	//포인트 상품목록 조회
	List<Point> getPointList();
	
	//포인트 사용내역 조회
	List<PointUserHistory> getPointUserHistoryList();
}
