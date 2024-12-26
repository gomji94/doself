
// 예시 데이터
const challengeData = [
    { prlNum: "prl_001", prrNum: "prr_001", mbrName: "조여준", prlDate: "2024-10-07 12:06:00", prlAmount: 3800, tpmDetailInfo: "토스페이", prrStatus: "처리완료" }
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
                            <td><a href="#" class="userIdDetails">${item.prlNum}</a></td>
                            <td>${item.prrNum}</td>
                            <td>${item.mbrName}</td>
                            <td>${item.prlDate}</td>
                            <td>${item.prlAmount}</td>
                            <td>${item.tpmDetailInfo}</td>                                                                                              
                            <td>${item.prrStatus}</td>                                
                        </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
    }
}

// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "prrStatus") {
        options = ["접수", "처리완료"];
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
        const matchesSearchType = searchType === "mbrName" ? item.mbrName.includes(searchKeyword) :
            searchType === "prrStatus" ? item.prrStatus.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.prlDate >= startDate) &&
            (!endDate || item.prlDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
