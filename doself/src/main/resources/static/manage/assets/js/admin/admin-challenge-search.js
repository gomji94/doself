
// 예시 데이터
const challengeData = [
    { cgNum: "cg_001", ctlName: "1일 3개 다이어트 식단 업로드 어려움", cgLeaderName: "김나연", cgName: "가보자고", cgCreationDate: "2024-08-22 12:00:00", cgCurrentMbrCount: 10, cgMaxMbrCount: 12, cgStartDate: "2024-08-27 12:00:00", cgEndDate: "2024-09-09 12:00:00", cgLike: 0, csName: "완료", mbrId: "id002", csRewardCheck: "지급" },
    { cgNum: "cg_002", ctlName: "1일 2개 다이어트 식단 업로드 보통", cgLeaderName: "박준형", cgName: "간식먹자", cgCreationDate: "2024-09-01 12:00:00", cgCurrentMbrCount: 7, cgMaxMbrCount: 10, cgStartDate: "2024-09-04 12:00:00", cgEndDate: "2024-09-17 12:00:00", cgLike: 0, csName: "완료", mbrId: "id017", csRewardCheck: "지급" },
    { cgNum: "cg_003", ctlName: "1일 3개 다이어트 식단 업로드 어려움", cgLeaderName: "박준형", cgName: "야채먹자", cgCreationDate: "2024-10-01 12:00:00", cgCurrentMbrCount: 4, cgMaxMbrCount: 15, cgStartDate: "0000-00-00 00:00:00", cgEndDate: "0000-00-00 00:00:00", cgLike: 0, csName: "챌린지 최소인원 미달 종료", mbrId: "id017", csRewardCheck: "" },
    { cgNum: "cg_004", ctlName: "1일 1개 no 밀가루 식단 업로드 쉬움", cgLeaderName: "조여준", cgName: "운동하자", cgCreationDate: "2024-10-06 12:00:00", cgCurrentMbrCount: 5, cgMaxMbrCount: 10, cgStartDate: "0000-00-00 00:00:00", cgEndDate: "0000-00-00 00:00:00", cgLike: 0, csName: "진행중(리더 양도)", mbrId: "id008", csRewardCheck: "" }

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
                            <td><a href="#" class="userIdDetails">${item.cgNum}</a></td>
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
