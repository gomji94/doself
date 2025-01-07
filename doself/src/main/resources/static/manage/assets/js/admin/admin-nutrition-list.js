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
