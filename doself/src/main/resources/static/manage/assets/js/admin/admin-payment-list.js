
// 예시 데이터
const challengeData = [
    { ctphNum: "ctph_001", mbrId: "id002", mbrName: "김나연", ctphDate: "2024-08-21 11:45:00", ctpCode: "개설 티켓", ctpPrice: "9800", pmcName: "카드", ctphStatus: "결제완료" },
    { ctphNum: "ctph_002", mbrId: "id006", mbrName: "윤여정", ctphDate: "2024-08-23 12:53:00", ctpCode: "참여 티켓", ctpPrice: "3800", pmcName: "카카오페이", ctphStatus: "결제완료" },
    { ctphNum: "ctph_003", mbrId: "id012", mbrName: "고민시", ctphDate: "2024-08-23 03:02:00", ctpCode: "참여 티켓", ctpPrice: "3800", pmcName: "네이버페이", ctphStatus: "결제완료" },
    { ctphNum: "ctph_004", mbrId: "id008", mbrName: "조여준", ctphDate: "2024-08-23 07:25:00", ctpCode: "참여 티켓", ctpPrice: "3800", pmcName: "토스페이", ctphStatus: "결제완료" }

];

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                                <td><a href="#" class="userIdDetails">${item.ctphNum}</a></td>
                                <td>${item.mbrId}</td>
                                <td>${item.mbrName}</td>
                                <td>${item.ctphDate}</td>
                                <td>${item.ctpCode}</td>
                                <td>${item.ctpPrice}</td>
                                <td>${item.pmcName}</td>
                                <td>${item.ctphStatus}</td>                               
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
        const matchesSearchType = searchType === "mbrName" ? item.mbrName.includes(searchKeyword) :
            searchType === "ctpCode" ? item.ctpCode.includes(searchKeyword) :
                searchType === "ctphStatus" ? item.ctphStatus.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.ctphDate >= startDate) &&
            (!endDate || item.ctphDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
