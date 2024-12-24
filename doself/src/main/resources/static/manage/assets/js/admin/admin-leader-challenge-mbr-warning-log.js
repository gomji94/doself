
// 예시 데이터
const challengeData = [
    { lcmwlNum: "lcmwl_001", cgName: "가보자고", cgmNum: "한소희", cmwcNum: "참여율 저조", olcCode: "challenge_member_feed", lcmwlBcNum: "cmf_028", lcmwlDate: "2024-08-28 10:31:22" },
    { lcmwlNum: "lcmwl_002", cgName: "가보자고", cgmNum: "한소희", cmwcNum: "참여율 저조", olcCode: "challenge_member_feed", lcmwlBcNum: "cmf_047", lcmwlDate: "2024-08-29 10:34:22" },
    { lcmwlNum: "lcmwl_003", cgName: "가보자고", cgmNum: "한소희", cmwcNum: "참여율 저조", olcCode: "challenge_member_feed", lcmwlBcNum: "cmf_070", lcmwlDate: "2024-08-30 10:42:22" }
];

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                            <td><a href="#" class="userIdDetails">${item.lcmwlNum}</a></td>
                            <td>${item.cgName}</td>
                            <td>${item.cgmNum}</td>
                            <td>${item.cmwcNum}</td>
                            <td>${item.olcCode}</td>
                            <td>${item.lcmwlBcNum}</td>
                            <td>${item.lcmwlDate}</td>                                 
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
        const matchesSearchType = searchType === "cgName" ? item.cgName.includes(searchKeyword) :
            searchType === "csName" ? item.cgmNum.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.lcmwlDate >= startDate) &&
            (!endDate || item.lcmwlDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
