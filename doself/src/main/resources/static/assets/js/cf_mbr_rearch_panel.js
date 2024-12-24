document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector(".sidebar");
    const searchPanel = document.querySelector(".mbr-search-panel");
    const searchIcon = document.getElementById("cf_mbr_search-panel");
    const main = document.querySelector(".main");
    const progressContainer = document.querySelector(".progress-container");

    // 검색 아이콘 클릭 이벤트
    searchIcon.addEventListener("click", () => {
        // 사이드바 축소/확장
        sidebar.classList.toggle("shrink");
        searchPanel.classList.toggle("active");

        // 검색 패널 표시/숨김
        if (sidebar.classList.contains("shrink")) {
            searchPanel.classList.add("active");
            progressContainer.classList.add("hidden");
            main.classList.add("move");
        } else {
            searchPanel.classList.remove("active");
            progressContainer.classList.remove("hidden");
            main.classList.remove("move");
        }
    });
});