
// 예시 데이터
const challengeData = [
    { mbrId: "id001", mbrName: "문지훈", mgCode: "관리자", mbrEmail: "mjh6958@gmail.com", mbrBirthDate: "1994-12-17", mbrPhoneNum: "010-6352-0461", mbrGender: "남", acNum: "청년(19~34)", openingTicketCount: 0, participationTicketCount: 0, mbrPoint: 0 },
    { mbrId: "id002", mbrName: "김나연", mgCode: "회원", mbrEmail: "rlaskdus66@gmail.com", mbrBirthDate: "1991-05-19", mbrPhoneNum: "010-3830-7751", mbrGender: "여", acNum: "청년(19~34)", openingTicketCount: 0, participationTicketCount: 0, mbrPoint: 1000 },
    { mbrId: "id003", mbrName: "문진현", mgCode: "회원", mbrEmail: "wlsgus4296@gmail.com", mbrBirthDate: "1992-08-15", mbrPhoneNum: "010-9655-2721", mbrGender: "남", acNum: "청년(19~34)", openingTicketCount: 0, participationTicketCount: 0, mbrPoint: 1000 },
    { mbrId: "id004", mbrName: "서혜원", mgCode: "회원", mbrEmail: "ims1228802@gamil.com", mbrBirthDate: "1993-12-31", mbrPhoneNum: "010-9999-9999", mbrGender: "여", acNum: "청년(19~34)", openingTicketCount: 0, participationTicketCount: 0, mbrPoint: 3000 }
];

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
                            <td>${item.mbrId}</td>
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
