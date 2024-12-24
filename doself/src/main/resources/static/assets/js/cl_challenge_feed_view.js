document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector(".sidebar");
    const searchPanel = document.querySelector(".search-panel");
    const searchIcon = document.querySelector(".sidebar #cf_mbr_search-panel");
    const main = document.querySelector(".main");
    const progressContainer = document.querySelector(".progress-container");

    const mbrSearchPanel = document.querySelector(".mbr-search-panel");
    const mbrSearchIcon = document.getElementById("cf_mbr_search-panel");

    let activePanel = null; // 현재 활성화된 패널을 저장하는 변수

    // 검색 아이콘 클릭 이벤트
    searchIcon.addEventListener("click", (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
        togglePanel(searchPanel);
    });

    // 조회 아이콘 클릭 이벤트
    mbrSearchIcon.addEventListener("click", (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
        togglePanel(mbrSearchPanel);
    });

    // 패널 활성화/비활성화 공통 함수
    function togglePanel(panel) {
        const isActive = panel.classList.contains("active");
        closeAllPanels();

        if (!isActive) {
            sidebar.classList.add("shrink");
            panel.classList.add("active");
            progressContainer.classList.add("hidden");
            main.classList.add("move");
            activePanel = panel;
        } else {
            activePanel = null;
        }
    }

    // 다른 곳 클릭 시 패널 닫기
    document.addEventListener("click", (e) => {
        if (
            activePanel &&
            !activePanel.contains(e.target) && // 패널 내부 클릭 여부
            e.target !== searchIcon &&
            e.target !== mbrSearchIcon
        ) {
            closeAllPanels();
        }
    });

    // 모든 패널을 닫는 함수
    function closeAllPanels() {
        searchPanel.classList.remove("active");
        mbrSearchPanel.classList.remove("active");
        sidebar.classList.remove("shrink");
        progressContainer.classList.remove("hidden");
        main.classList.remove("move");
        activePanel = null;
    }

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
