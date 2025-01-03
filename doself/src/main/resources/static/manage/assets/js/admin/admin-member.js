// 데이터 초기 렌더링
document.addEventListener("DOMContentLoaded", () => {
    renderTable(challengeData);
});

// 테이블 렌더링 함수
function renderTable(data) {
    const resultTable = document.getElementById("resultTable");
    resultTable.innerHTML = "";
    data.forEach(item => {
        const row = `
                        <tr>
                            <td><a href="/admin/member/modifymember" class="userIdDetails">${item.mbrId}</a></td>
                            <td>${item.mbrName}</td>
                            <td>${item.mgCode}</td>
                            <td>${item.mbrEmail}</td>
                            <td>${item.mbrBirthDate}</td>
                            <td>${item.mbrPhoneNum}</td>
                            <td>${item.mbrGender}</td>
                            <td>${item.acNum}</td>
                            <td>${item.openingTicketCount}</td>
                            <td>${item.participationTicketCount}</td>
                            <td>${item.mbrPoint}</td>
                        </tr>`;
        resultTable.innerHTML += row;
    });
}

// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "mgCode") {
        options = ["관리자", "회원", "부정회원"];
    } else if (searchType === "acNum") {
        options = ["유년기(1~6)", "유소년(7~13)", "청소년(14~18)", "청년(19~34)", "중년(35~49)", "장년(50~64)", ""];
    }

    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}

// 데이터 필터링 함수
function filterData() {
    const searchType = document.getElementById("searchType").value;
    const searchKeyword = document.getElementById("searchKeyword").value.trim();
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const filteredData = challengeData.filter(item => {
        const matches = searchType === "mbrId" ? item.mbrId.includes(searchKeyword) :
            searchType === "mbrName" ? item.mbrName.includes(searchKeyword) :
                searchType === "mgCode" ? item.mgCode.includes(searchKeyword) :
                    searchType === "acNum" ? item.acNum.includes(searchKeyword) : true;

        const withinDate = (!startDate || item.mbrBirthDate >= startDate) && (!endDate || item.mbrBirthDate <= endDate);
        return matches && withinDate;
    });

    renderTable(filteredData);
}
