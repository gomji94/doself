// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "nirr.nirr_category") {
        options = ["영양제", "음식"];
    } else if (searchType === "sc.sc_status") {
        options = ["접수", "승인", "반려"];
    }

    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}

// 검색한데이터 url매핑
function filterData() {
    const searchType = document.getElementById("searchType").value;
    const searchKeyword = document.getElementById("searchKeyword").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const url = `/admin/nutrition/list?searchType=${searchType}&searchKeyword=${encodeURIComponent(searchKeyword)}&startDate=${startDate}&endDate=${endDate}`;

    // 검색 결과를 새로고침
    window.location.href = url;
}