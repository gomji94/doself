package doself.util;

import java.util.List;

import lombok.Data;

@Data
public class CardPageInfo<T> {
	
	private List<T> contents;
	private CardPageable cardPageable;
	private int totalRowCount;
	private int currentPage;
	private int lastPage;
	private int startPageNum;
	private int endPageNum;
	
	public CardPageInfo(List<T> contents, CardPageable cardPageable, int totalRowCount){
		this.contents = contents;
		this.cardPageable = cardPageable;
		this.totalRowCount = totalRowCount;
		pageNumProcess();
	}
	public void pageNumProcess() {
		int currentPage = cardPageable.getCurrentPage();
		int rowPerPage = cardPageable.getRowPerPage();
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
