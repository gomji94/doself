
// 예시 데이터
const challengeData = [
    { rphNum: "rph_001", cgName: "가보자고", mbrId: "조여준", rhNum: "멤버 챌린지별 2위", rphRewardSelectedDate: "2024-09-09", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-09" },
    { rphNum: "rph_002", cgName: "가보자고", mbrId: "문진현", rhNum: "멤버 챌린지별 3위", rphRewardSelectedDate: "2024-09-09", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-09" },
    { rphNum: "rph_003", cgName: "가보자고", mbrId: "김나연", rhNum: "멤버 챌린지별 1위", rphRewardSelectedDate: "2024-09-09", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-09" },
    { rphNum: "rph_004", cgName: "간식먹자", mbrId: "김해숙", rhNum: "멤버 챌린지별 1위", rphRewardSelectedDate: "2024-09-17", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-17" },
    { rphNum: "rph_005", cgName: "간식먹자", mbrId: "박서준", rhNum: "멤버 챌린지별 2위", rphRewardSelectedDate: "2024-09-17", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-17" },
    { rphNum: "rph_006", cgName: "간식먹자", mbrId: "서혜원", rhNum: "멤버 챌린지별 3위", rphRewardSelectedDate: "2024-09-17", rphAdmin: "문지훈", rphRewardPaymentDate: "2024-09-17" }
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
                                <td>${item.rphNum}</td>
                                <td>${item.cgName}</td>
                                <td>${item.mbrId}</td>
                                <td>${item.rhNum}</td>
                                <td>${item.rphRewardSelectedDate}</td>
                                <td>${item.rphAdmin}</td>
                                <td>${item.rphRewardPaymentDate}</td>                               
                            </tr>`;
            resultTable.innerHTML += row;
        });
    } else {
        resultTable.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
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
    } else if (searchType === "rhNum") {
        options = ["멤버 챌린지별 1위", "멤버 챌린지별 2위", "멤버 챌린지별 3위", "챌린지 월간 1위", "챌린지 월간 2위", "챌린지 월간 3위"];
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
            searchType === "mbrId" ? item.mbrId.includes(searchKeyword) :
                searchType === "rhNum" ? item.rhNum.includes(searchKeyword) : true;

        const withinDateRange = (!startDate || item.rphRewardSelectedDate >= startDate) &&
            (!endDate || item.rphRewardSelectedDate <= endDate);

        return matchesSearchType && withinDateRange;
    });
    // 필터링된 결과 테이블에 출력
    renderTable(filteredData);
}
// 페이지가 로드되었을 때 전체 데이터 표시
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});
