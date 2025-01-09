package doself.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardPageable {
	private int currentPage = 1;
	private int offset = 0;
	private int rowPerPage = 12;  // 카드는 페이지당 12개로 설정
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		offsetProcess();
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
		offsetProcess();
	}
	public void offsetProcess() {
		this.offset = (currentPage - 1) * rowPerPage;
	}
	
}
