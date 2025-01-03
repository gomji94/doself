
// 예시 데이터
const challengeData = [];

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
                            <td>${item.cpslNum}</td>
                            <td>${item.cpslYear}</td>
                            <td>${item.cpslMonthly}</td>
                            <td>${item.cgName}</td>
                            <td>${item.mbrId}</td>
                            <td>${item.cpslCumulativePerformanceRate}</td>
                            <td>${item.cpslCumulativeParticipationRate}</td>
                            <td>${item.cpslCumulativePerformanceScore}</td>
                            <td>${item.cpslCumulativeParticipationScore}</td>
                            <td>${item.cpslLikeScore}</td>
                            <td>${item.cpslCommentsCount}</td>
                            <td>${item.cpslTotalScore}</td>
                            <td>${item.cpslRank}</td>                               
                        </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="13">검색 결과가 없습니다.</td></tr>`;
    }
}

// 검색조건에 따라 자동완성값 변경
/* function updateSearchSuggestions() {
            const searchType = document.getElementById("searchType").value;
            const suggestions = document.getElementById("searchSuggestions");
            suggestions.innerHTML = ""; // 기존 데이터 초기화

            let options = [];
            if (searchType === "csName") {
                options = ["관리자", "회원", "부정회원"];
            } 

            options.forEach(value => {
                const option = document.createElement("option");
                option.value = value;
                suggestions.appendChild(option);
            });
        } */

// 데이터 필터링 함수
function filterData() {
    const searchType = document.getElementById("searchType").value; // 선택된 조건
    const searchKeyword = document.getElementById("searchKeyword").value.trim(); // 입력된 검색어
    const startDate = document.getElementById("startDate").value; // 시작 날짜
    const endDate = document.getElementById("endDate").value; // 종료 날짜

    // 필터링된 데이터
    const filteredData = challengeData.filter(item => {
        const matchesSearchType = searchType === "cgName" ? item.cgName.includes(searchKeyword) :
            searchType === "csName" ? item.csName.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.cpslYear + '-' + item.cpslMonthly >= startDate) &&
            (!endDate || item.cpslYear + '-' + item.cpslMonthly <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
