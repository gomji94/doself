// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "cg.cg_name") {
        options = ["가보자고", "간식먹자"];
    } else if (searchType === "CONCAT_WS(' ', rh.rh_target, rh.rh_reward_type, rh.rh_rank)") {
        options = ["멤버 챌린지별 1위", "멤버 챌린지별 2위", "멤버 챌린지별 3위", "챌린지 월간 1위", "챌린지 월간 2위", "챌린지 월간 3위"];
    }

    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}

// 검색한데이터 url매핑
function filterData() {
    const searchType = document.getElementById("searchType").value;
    const searchKeyword = document.getElementById("searchKeyword").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const url = `/admin/challenge/rewardlist?searchType=${searchType}&searchKeyword=${encodeURIComponent(searchKeyword)}&startDate=${startDate}&endDate=${endDate}`;

    // 검색 결과를 새로고침
    window.location.href = url;
}