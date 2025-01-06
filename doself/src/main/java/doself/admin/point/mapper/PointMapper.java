package doself.admin.point.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;

@Mapper
public interface PointMapper {
	
	//포인트 상품목록 조회
	List<Point> getPointList();
	
	//포인트 사용내역 조회
	List<PointUserHistory> getPointUserHistoryList(String startDate, String endDate);
	
	//특정 포인트 상품 조회
	Point getPointInfoByPeplNum(String peplNum);
	
	//특정 포인트 상품 수정
	int modifyPoint(Point point);
}
