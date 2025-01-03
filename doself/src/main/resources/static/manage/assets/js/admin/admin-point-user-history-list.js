
// 예시 데이터
const challengeData = [];

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                            <td>${item.pumhNum}</td>
                            <td>${item.peplNum}</td>
                            <td>${item.mbrId}</td>
                            <td>${item.pumhDate}</td>                                
                        </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="4">검색 결과가 없습니다.</td></tr>`;
    }
}

// 데이터 필터링 함수
function filterData() {
    const startDate = document.getElementById("startDate").value; // 시작 날짜
    const endDate = document.getElementById("endDate").value; // 종료 날짜        

    // 필터링된 데이터
    const filteredData = challengeData.filter(item => {
        const withinDateRange = (!startDate || item.pumhDate >= startDate) &&
            (!endDate || item.pumhDate <= endDate);

        return withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
