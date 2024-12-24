
// 예시 데이터
const challengeData = [
    { cmssNum: "cmss_001", cgNum: "가보자고", mbrId: "김나연", cmssDate: "2024-08-27", cmssUploadFeedCount: 3, cmssParticipationCheck: 0, cmssTodayAchievementRate: "100%" },
    { cmssNum: "cmss_002", cgNum: "가보자고", mbrId: "문진현", cmssDate: "2024-08-27", cmssUploadFeedCount: 3, cmssParticipationCheck: 0, cmssTodayAchievementRate: "100%" },
    { cmssNum: "cmss_003", cgNum: "가보자고", mbrId: "윤여정", cmssDate: "2024-08-27", cmssUploadFeedCount: 2, cmssParticipationCheck: 1, cmssTodayAchievementRate: "66.67%" },
    { cmssNum: "cmss_004", cgNum: "가보자고", mbrId: "조여준", cmssDate: "2024-08-27", cmssUploadFeedCount: 3, cmssParticipationCheck: 0, cmssTodayAchievementRate: "100%" }

];

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                                    <td><a href="#" class="userIdDetails">${item.cmssNum}</a></td>
                                    <td>${item.cgNum}</td>
                                    <td>${item.mbrId}</td>
                                    <td>${item.cmssDate}</td>
                                    <td>${item.cmssUploadFeedCount}</td>
                                    <td>${item.cmssParticipationCheck}</td>
                                    <td>${item.cmssTodayAchievementRate}</td>                               
                                </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="13">검색 결과가 없습니다.</td></tr>`;
    }
}

// 데이터 필터링 함수
function filterData() {
    const searchType = document.getElementById("searchType").value; // 선택된 조건
    const searchKeyword = document.getElementById("searchKeyword").value.trim(); // 입력된 검색어
    const startDate = document.getElementById("startDate").value; // 시작 날짜
    const endDate = document.getElementById("endDate").value; // 종료 날짜                                    

    // 필터링된 데이터
    const filteredData = challengeData.filter(item => {
        const matchesSearchType = searchType === "cgName" ? item.cgNum.includes(searchKeyword) :
            searchType === "mbrId" ? item.mbrId.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.cmssDate >= startDate) &&
            (!endDate || item.cmssDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});