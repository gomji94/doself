// 가상 데이터 생성
const mockData = Array.from({ length: 70 }, (_, i) => ({
    no: i + 1,
    category: ['공지사항', '자유', '정보', '질문', '유머'][Math.floor(Math.random() * 5)],
    title: `게시글 제목 ${i + 1}`,
    name: `작성자 ${i + 1}`,
    date: `2024-12-${String((i % 30) + 1).padStart(2, '0')}`,
    like: Math.floor(Math.random() * 100), // 좋아요 수 랜덤 생성
}));

// 초기값 설정
const rowsPerPage = 10; // 한 페이지당 표시할 행 수
let currentPage = 1; // 현재 페이지
const maxVisiblePages = 5; // 페이지 네이션에서 보여줄 최대 페이지 수
let filteredData = mockData; // 검색 및 필터링된 데이터

// 데이터를 렌더링하는 함수
function renderTable(data, page = 1) {
    const tbody = document.querySelector('#main-container__board tbody');
    tbody.innerHTML = ''; // 기존 데이터 제거

    // 현재 페이지의 데이터 슬라이싱
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const currentData = data.slice(start, end);

    // 데이터 렌더링
    currentData.forEach(row => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${row.no}</td>
            <td>${row.category}</td>
            <td>${row.title}</td>
            <td>${row.name}</td>
            <td>${row.date}</td>
            <td>${row.like}</td>
        `;
        tbody.appendChild(tr);
    });

    // 페이지네이션 업데이트
    renderPagination(data.length, page);
}

// 페이지네이션 렌더링 함수
function renderPagination(totalItems, currentPage) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = ''; // 기존 페이지네이션 제거

    const totalPages = Math.ceil(totalItems / rowsPerPage);
    const currentGroup = Math.ceil(currentPage / maxVisiblePages); // 현재 페이지 그룹
    const startPage = (currentGroup - 1) * maxVisiblePages + 1; // 현재 그룹의 첫 페이지
    const endPage = Math.min(currentGroup * maxVisiblePages, totalPages); // 현재 그룹의 마지막 페이지

    // 이전 버튼
    if (currentPage > 1) {
        const prevButton = document.createElement('button');
        prevButton.textContent = 'Prev';
        prevButton.onclick = () => changePage(currentPage - 1);
        pagination.appendChild(prevButton);
    }

    // 페이지 번호 버튼
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        pageButton.classList.toggle('active', i === currentPage);
        pageButton.onclick = () => changePage(i);
        pagination.appendChild(pageButton);
    }

    // 다음 버튼
    if (currentPage < totalPages) {
        const nextButton = document.createElement('button');
        nextButton.textContent = 'Next';
        nextButton.onclick = () => changePage(currentPage + 1);
        pagination.appendChild(nextButton);
    }
}

// 페이지 변경 함수
function changePage(page) {
    currentPage = page;
    renderTable(filteredData, currentPage);
}

// 검색 필터 적용 함수
function applyFilters() {
    const dateFilter = document.getElementById('date-filter__dropdown').value;
    const searchFilter = document.getElementById('search-filter__dropdown').value;
    const searchInput = document.getElementById('search-box__input').value.toLowerCase();

    filteredData = mockData.filter(item => {
        let isValid = true;

        // 날짜 필터 적용
        if (dateFilter === 'one-week') {
            isValid = new Date(item.date) >= new Date('2024-11-26');
        } else if (dateFilter === 'one-month') {
            isValid = new Date(item.date) >= new Date('2024-11-01');
        } else if (dateFilter === 'three-month') {
            isValid = new Date(item.date) >= new Date('2024-09-01');
        }

        // 검색 필터 적용
        if (searchFilter === 'title' && !item.title.toLowerCase().includes(searchInput)) {
            isValid = false;
        } else if (searchFilter === 'content' && !item.title.toLowerCase().includes(searchInput)) {
            isValid = false; // 내용 검색은 제목으로 임시 적용
        } else if (searchFilter === 'id' && !item.name.toLowerCase().includes(searchInput)) {
            isValid = false;
        }

        return isValid;
    });

    currentPage = 1; // 필터 적용 시 첫 페이지로 이동
    renderTable(filteredData, currentPage);
}

// 초기화
document.getElementById('search-box__button').addEventListener('click', applyFilters);
renderTable(mockData);