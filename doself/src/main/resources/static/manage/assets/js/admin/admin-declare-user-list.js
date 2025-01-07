// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "rc.rc_name") {
        options = ["불법 컨텐츠", "사기 및 사칭", "폭력적 및 유해 콘텐츠", "욕설 및 비방", "스팸 및 광고", "허위 사실 유포"];
    }
    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}
