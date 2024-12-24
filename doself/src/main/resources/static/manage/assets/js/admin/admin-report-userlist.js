
// 예시 데이터
const challengeData = [
    { rmmNum: "rmm_001", rrNum: "rr_001", mbrId: "id005", rcCode: "rc005", rmmSanctionStartDate: "2024-08-03 02:30:27", rmmSanctionPeriod: 7, rmmSanctionEndDate: "2024-08-10 02:30:27", rmmAdmin: "id001" }    
];

// 데이터 초기 렌더링
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                                <td><a href="#" class="userIdDetails">${item.rmmNum}</a></td>
                                <td>${item.rrNum}</td>
                                <td>${item.mbrId}</td>
                                <td>${item.rcCode}</td>
                                <td>${item.rmmSanctionStartDate}</td>
                                <td>${item.rmmSanctionPeriod}</td>
                                <td>${item.rmmSanctionEndDate}</td>                                    
                                <td>${item.rmmAdmin}</td>                                    
                            </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="8">검색 결과가 없습니다.</td></tr>`;
    }
}

// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "rcCode") {
        options = ["불법 컨텐츠", "사기 및 사칭", "폭력적 및 유해 콘텐츠", "욕설 및 비방", "스팸 및 광고", "허위 사실 유포"];
    }
    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}


// 데이터 필터링 함수
function filterData() {
    const searchType = document.getElementById("searchType").value; // 선택된 조건
    const searchKeyword = document.getElementById("searchKeyword").value.trim(); // 입력된 검색어
    const startDate = document.getElementById("startDate").value; // 시작 날짜
    const endDate = document.getElementById("endDate").value; // 종료 날짜        

    // 필터링된 데이터
    const filteredData = challengeData.filter(item => {
        const matchesSearchType = searchType === "rcCode" ? item.rcCode.includes(searchKeyword) :
            searchType === "mbrId" ? item.mbrId.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.rmmSanctionStartDate >= startDate) &&
            (!endDate || item.rmmSanctionEndDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
