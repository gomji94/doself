document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector(".sidebar");
    const searchPanel = document.querySelector(".search-panel");
    const searchIcon = document.querySelector(".sidebar ul:first-child");
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

    // 검색 입력 필드에 따른 동작 (예시 데이터 표시)
    const searchInput = document.getElementById("search-input");
    const searchResults = document.getElementById("search-results");

    searchInput.addEventListener("input", (e) => {
        const query = e.target.value.toLowerCase();
        searchResults.innerHTML = ""; // 이전 검색 결과 초기화

        if (query) {
            // 더미 데이터 필터링
            const results = ["피드", "음식", "영양제", "챌린지"].filter((item) =>
                item.toLowerCase().includes(query)
            );

            // 결과 렌더링
            results.forEach((result) => {
                const li = document.createElement("li");
                li.textContent = result;
                searchResults.appendChild(li);
            });
        }
    });
});