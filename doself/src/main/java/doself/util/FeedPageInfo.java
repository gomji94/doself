package doself.util;

import java.util.List;

import lombok.Data;

@Data
public class FeedPageInfo<T> {
    private List<T> contents;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    public FeedPageInfo(List<T> contents, int currentPage, int rowPerPage, long totalElements) {
        this.contents = contents;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalElements / rowPerPage);
        this.totalElements = totalElements;
    }
    
    public boolean isLastPage() {
        return currentPage == totalPages;
    }
}
