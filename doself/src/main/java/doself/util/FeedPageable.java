package doself.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedPageable {
    private int currentPage = 1;
    private int offset = 0;
    private int rowPerPage = 10;

    public FeedPageable() {
        offsetProcess();
    }

    public FeedPageable(int currentPage, int rowPerPage) {
        this.currentPage = currentPage;
        this.rowPerPage = rowPerPage;
        offsetProcess();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        offsetProcess();
    }

    public void setRowPerPage(int rowPerPage) {
        this.rowPerPage = rowPerPage;
        offsetProcess();
    }

    private void offsetProcess() {
        this.offset = (currentPage - 1) * rowPerPage;
    }
}
