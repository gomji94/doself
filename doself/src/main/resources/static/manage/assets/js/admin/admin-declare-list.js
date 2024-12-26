
// 예시 데이터
const challengeData = [
    { rrNum: "rr_001", rcCode: "스팸 및 광고", mbrId: "김나연", rrBcNum: "fb_001", rrContent: "게시글에 광고 링크 공유", scCode: "접수", rrDate: "2024-08-02 05:30:27", rrProcessingPeriod: "2024-08-03 02:30:27", rrAdmin: "id001", rrReasonContent: ""},
    { rrNum: "rr_002", rcCode: "폭력적 및 유해 콘텐츠", mbrId: "엄승남", rrBcNum: "fbc_003", rrContent: "노잼", scCode: "접수", rrDate: "2024-08-03 05:30:28", rrProcessingPeriod: "2024-08-03 07:30:27", rrAdmin: "id001", rrReasonContent: "신고규정위반아님"},
    { rrNum: "rr_003", rcCode: "사기 및 사칭", mbrId: "김윤석", rrBcNum: "feed_004", rrContent: "기분나쁨", scCode: "접수", rrDate: "2024-10-02 12:32:40", rrProcessingPeriod: "2024-10-05 02:32:40", rrAdmin: "id001", rrReasonContent: "증거불충분"},
    { rrNum: "rr_004", rcCode: "욕설 및 비방", mbrId: "류준열", rrBcNum: "fc_005", rrContent: "부러움", scCode: "접수", rrDate: "2024-10-04 11:32:41", rrProcessingPeriod: "2024-10-05 02:32:41", rrAdmin: "id001", rrReasonContent: "신고교정위반아님"}
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
                                <td><a href="/admin/declare/modifyrequest" class="userIdDetails">${item.rrNum}</a></td>
                                <td>${item.rcCode}</td>
                                <td>${item.mbrId}</td>
                                <td>${item.rrBcNum}</td>
                                <td>${item.rrContent}</td>
                                <td>${item.scCode}</td>
                                <td>${item.rrDate}</td>
                                <td>${item.rrProcessingPeriod}</td>
                                <td>${item.rrAdmin}</td>                                    
                                <td>${item.rrReasonContent}</td>                                    
                            </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="10">검색 결과가 없습니다.</td></tr>`;
    }
}

// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "rcCode") {
        options = ["불법 컨텐츠", "사기 및 사칭", "폭력적 및 유해 콘텐츠", "욕설 및 비방", "스팸 및 광고", "허위 사실 유포"];
    } else if (searchType === "scCode") {
        options = ["접수", "반려", "승인"];
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
        const matchesSearchType = searchType === "rcCode" ? item.rcCode.includes(searchKeyword) :
            searchType === "scCode" ? item.scCode.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.rrDate >= startDate) &&
            (!endDate || item.rrDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
