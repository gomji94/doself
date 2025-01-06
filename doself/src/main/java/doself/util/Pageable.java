package doself.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Pageable {
	private int currentPage = 1;
	private int offset = 0;
	private int rowPerPage = 10;
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		offsetProcess();
	}
	public void offsetProcess() {
		int offset = (currentPage - 1) * rowPerPage;
		this.offset = offset;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
		offsetProcess();
	}
	
}
