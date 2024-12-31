
// 예시 데이터
const challengeData = [
    { mcslNum: "mcsl_001", mcslYear: "2024", mcslMonthly: "9", cgName: "가보자고", mcslCumulativePerformanceRate: "79.1%", mcslCumulativeParticipationRate: "93.1%", mcslLevelScore: 30, mcslCumulativePerformanceScore: 10, mcslCumulativeParticipationScore: 15, mcslLikeScore: 0, mcslTotalScore: 55, mcslRank: 1 },
    { mcslNum: "mcsl_002", mcslYear: "2024", mcslMonthly: "9", cgName: "간식먹자", mcslCumulativePerformanceRate: "61.14%", mcslCumulativeParticipationRate: "78.74%", mcslLevelScore: 20, mcslCumulativePerformanceScore: 5, mcslCumulativeParticipationScore: 10, mcslLikeScore: 0, mcslTotalScore: 35, mcslRank: 2 }
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
                            <td>${item.mcslNum}</td>
                            <td>${item.mcslYear}</td>
                            <td>${item.mcslMonthly}</td>
                            <td>${item.cgName}</td>
                            <td>${item.mcslCumulativePerformanceRate}</td>
                            <td>${item.mcslCumulativeParticipationRate}</td>
                            <td>${item.mcslLevelScore}</td>
                            <td>${item.mcslCumulativePerformanceScore}</td>
                            <td>${item.mcslCumulativeParticipationScore}</td>
                            <td>${item.mcslLikeScore}</td>
                            <td>${item.mcslTotalScore}</td>
                            <td>${item.mcslRank}</td>                                
                        </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="12">검색 결과가 없습니다.</td></tr>`;
    }
}

// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "cgName") {
        options = ["가보자고", "간식먹자"];
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
        const matchesSearchType = searchType === "cgName" ? item.cgName.includes(searchKeyword) : true;
        const withinDateRange = (!startDate || item.mcslYear + '-' + item.mcslMonthly >= startDate) &&
            (!endDate || item.mcslYear + '-' + item.mcslMonthly <= endDate);
        return matchesSearchType && withinDateRange;
    });

    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}

// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
