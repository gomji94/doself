
// 예시 데이터
const challengeData = [
    { mllNum: "mll_001", mbrId: "id001", mbrName: "문지훈", mgCode: "관리자", mllIp: "127.0.1", mllDate: "2024-09-26 08:50:10" },
    { mllNum: "mll_002", mbrId: "id002", mbrName: "김나연", mgCode: "회원", mllIp: "127.0.2", mllDate: "2024-09-27 02:55:10" },
    { mllNum: "mll_003", mbrId: "id001", mbrName: "문지훈", mgCode: "관리자", mllIp: "127.0.1", mllDate: "2024-09-27 04:30:14" },
    { mllNum: "mll_004", mbrId: "id002", mbrName: "김나연", mgCode: "회원", mllIp: "127.0.2", mllDate: "2024-09-27 04:30:14" }

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
                                <td><a href="#" class="userIdDetails">${item.mllNum}</a></td>
                                <td>${item.mbrId}</td>
                                <td>${item.mbrName}</td>
                                <td>${item.mgCode}</td>
                                <td>${item.mllIp}</td>
                                <td>${item.mllDate}</td>                                 
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
    if (searchType === "mgCode") {
        options = ["관리자", "회원", "부정회원"];
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
        const matchesSearchType = searchType === "mbrId" ? item.mbrId.includes(searchKeyword) :
            searchType === "mbrName" ? item.mbrName.includes(searchKeyword) :
                searchType === "mgCode" ? item.mgCode.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.mllDate >= startDate) &&
            (!endDate || item.mllDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
