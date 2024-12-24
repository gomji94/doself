
// 예시 데이터
const challengeData = [
    { nirrNum: "nirr_001", mbrId: "id002", nirrContent: "루테인", nirrDate: "2024-09-27 02:55:10", nirrCategory: "영양제", scCode: "승인", prrAdmin: "id001", nirrProcessingDate: "2024-09-27 03:21:05", nirrReasonContent: "" },
    { nirrNum: "nirr_002", mbrId: "id003", nirrContent: "음식", nirrDate: "2024-09-27 04:55:10", nirrCategory: "영양제", scCode: "접수", prrAdmin: "id001", nirrProcessingDate: "2024-09-27 07:55:02", nirrReasonContent: "" },
    { nirrNum: "nirr_003", mbrId: "id004", nirrContent: "비타민D", nirrDate: "2024-09-27 04:55:30", nirrCategory: "영양제", scCode: "반려", prrAdmin: "id001", nirrProcessingDate: "2024-09-27 07:58:6", nirrReasonContent: "이미 등록된 영양제 정보" },
    { nirrNum: "nirr_004", mbrId: "id015", nirrContent: "마약김밥", nirrDate: "2024-09-28 09:37:58", nirrCategory: "음식", scCode: "반려", prrAdmin: "id001", nirrProcessingDate: "2024-09-29 06:26:29", nirrReasonContent: "출처없는 정보" }

];

// 결과를 테이블에 출력하는 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = ""; // 기존 데이터 초기화

    if (data.length > 0) {
        data.forEach(item => {
            const row = `<tr>
                            <td><a href="#" class="userIdDetails">${item.nirrNum}</a></td>
                            <td>${item.mbrId}</td>
                            <td>${item.nirrContent}</td>
                            <td>${item.nirrDate}</td>
                            <td>${item.nirrCategory}</td>
                            <td>${item.scCode}</td>
                            <td>${item.prrAdmin}</td>
                            <td>${item.nirrProcessingDate}</td>
                            <td>${item.nirrReasonContent}</td>                                
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
    if (searchType === "nirrCategory") {
        options = ["영양제", "음식"];
    } else if (searchType === "scCode") {
        options = ["접수", "승인", "반려"];
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

    // 데이터 초기 렌더링
    document.addEventListener("DOMContentLoaded", () => {
        renderTable(challengeData);
    });

    // 필터링된 데이터
    const filteredData = challengeData.filter(item => {
        const matchesSearchType = searchType === "nirrCategory" ? item.nirrCategory.includes(searchKeyword) :
            searchType === "scCode" ? item.scCode.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.nirrDate >= startDate) &&
            (!endDate || item.nirrDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
