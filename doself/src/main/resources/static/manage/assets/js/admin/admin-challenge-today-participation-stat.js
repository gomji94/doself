
// 예시 데이터
const challengeData = [
    { ctpsNum: "ctps_001", cgName: "가보자고", ctpsDate: "2024-08-27", ctpsParticipation: "100.0%", ctpsAchievementRate: "80.0%" },
    { ctpsNum: "ctps_002", cgName: "가보자고", ctpsDate: "2024-08-28", ctpsParticipation: "80.0%", ctpsAchievementRate: "60.0%" },
    { ctpsNum: "ctps_003", cgName: "가보자고", ctpsDate: "2024-08-29", ctpsParticipation: "90.0%", ctpsAchievementRate: "76.67%" },
    { ctpsNum: "ctps_004", cgName: "가보자고", ctpsDate: "2024-08-30", ctpsParticipation: "100.0%", ctpsAchievementRate: "83.33%" }

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
                            <td><a href="#" class="userIdDetails">${item.ctpsNum}</a></td>
                            <td>${item.cgName}</td>
                            <td>${item.ctpsDate}</td>
                            <td>${item.ctpsParticipation}</td>
                            <td>${item.ctpsAchievementRate}</td>                                
                        </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="13">검색 결과가 없습니다.</td></tr>`;
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

        const withinDateRange = (!startDate || item.ctpsDate >= startDate) &&
            (!endDate || item.ctpsDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});