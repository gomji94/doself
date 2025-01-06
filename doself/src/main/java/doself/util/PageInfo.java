package doself.util;

import java.util.List;

import lombok.Data;

@Data
public class PageInfo<T> {
	
	private List<T> contents;
	private Pageable pageable;
	private int totalRowCount;
	private int currentPage;
	private int lastPage;
	private int startPageNum;
	private int endPageNum;
	
	public PageInfo(List<T> contents, Pageable pageable, int totalRowCount){
		this.contents = contents;
		this.pageable = pageable;
		this.totalRowCount = totalRowCount;
		pageNumProcess();
	}
	public void pageNumProcess() {
		int currentPage = pageable.getCurrentPage();
		int rowPerPage = pageable.getRowPerPage();
		int lastPage = (int) Math.ceil((double)totalRowCount/rowPerPage);
		
		// 1 ~ 10p
		int startPageNum = 1;
		int endPageNum = lastPage < 10 ? lastPage : 10;
		
		if(currentPage > 6 && lastPage > 9) {
			startPageNum = currentPage - 5; // 2
			endPageNum = currentPage + 4; // 11
			if(endPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}
		this.lastPage = lastPage;
		this.startPageNum = startPageNum;
		this.endPageNum = endPageNum;
		this.currentPage = currentPage;
	}
}
