
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
                            <td>${item.cgNum}</td>
                            <td>${item.ctlName}</td>
                            <td>${item.cgLeaderName}</td>
                            <td>${item.cgName}</td>
                            <td>${item.cgCreationDate}</td>
                            <td>${item.cgCurrentMbrCount}</td>
                            <td>${item.cgMaxMbrCount}</td>
                            <td>${item.cgStartDate}</td>
                            <td>${item.cgEndDate}</td>
                            <td>${item.cgLike}</td>
                            <td>${item.csName}</td>
                            <td>${item.mbrId}</td>
                            <td>${item.csRewardCheck}</td>                              
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
    if (searchType === "csName") {
        options = ["완료", "진행중", "진행중(리더 양도)", "대기중", "챌린지 최소인원 미달 종료", "챌린지 중단(남은 멤버가 없을 경우)"];
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
        const matchesSearchType = searchType === "cgName" ? item.cgName.includes(searchKeyword) :
            searchType === "csName" ? item.csName.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.cgEndDate >= startDate) &&
            (!endDate || item.cgEndDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
