// --- feed option button ---
$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('.option-button').on('click', function () {
        $('.feed-option-modal-wrap').fadeIn(); // 모달창 활성화
    });

    // 닫기 버튼 클릭 시 모달창 닫기
    $('.feed-option-modal-wrap .close').on('click', function () {
        $('.feed-option-modal-wrap').fadeOut(); // 모달창 비활성화
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('.feed-option-modal-wrap').on('click', function (e) {
        if ($(e.target).is('.feed-option-modal-wrap')) {
            $(this).fadeOut();
        }
    });
});

// --- feed like button event ---
$(document).ready(function () {
    $(document).on('click', '.likeBtn', function (event) {
        event.preventDefault(); // 기본 동작 방지

        const likeImg = $(this).find('.likeImg'); // 버튼 내부의 likeImg 요소 선택
        const likedSrc = 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png'; // "좋아요" 이미지 경로
        const defaultSrc = 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png'; // 기본 이미지 경로

        // 현재 상태 확인 및 업데이트
        const isLiked = $(this).attr('data-liked') === 'true';

        if (!isLiked) {
            likeImg.attr('src', likedSrc)
					.css({ 'width': '24.5px', 'height': 'auto' }); // "좋아요" 이미지로 변경
            $(this).attr('data-liked', 'true'); // 상태 업데이트
        } else {
            likeImg.attr('src', defaultSrc); // 기본 이미지로 복원
            $(this).attr('data-liked', 'false'); // 상태 복원
        }

        // 디버깅용 로그 출력
        console.log(`현재 상태: ${$(this).attr('data-liked')}`);
        console.log(`현재 이미지 경로: ${likeImg.attr('src')}`);
    });
});

// --- feed comment modal ---
$(document).ready(function () {
    // 댓글 버튼 클릭 이벤트
    $('.commentBtn').on('click', function () {
        // feed-comment.html 모달 표시
        const commentModal = $('.feed-comment-modal-overlay'); // 댓글 모달 오버레이
        commentModal.fadeIn(300); // 부드럽게 모달 표시
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.feed-comment-modalCloseBtn').on('click', function () {
        $('.feed-comment-modal-overlay').fadeOut(300); // 부드럽게 모달 숨기기
    });

    // 모달 오버레이 클릭 시 모달 닫기
    $('.feed-comment-modal-overlay').on('click', function (e) {
        if ($(e.target).is('.feed-comment-modal-overlay')) {
            $(this).fadeOut(300);
        }
    });
});

// --- modify feed modal ---
$(document).ready(function () {
    // 모달 열기
    $('#feed-modify-modal').on('click', function () {
      $('#feed-modify-modal-overlay').fadeIn(200);
    });
  
    // 모달 닫기 (닫기 버튼 클릭 시)
    $('#feed-modify-modal-closeBtn').on('click', function () {
      $('#feed-modify-modal-overlay').fadeOut(200);
    });
  
    // 모달 닫기 (오버레이 클릭 시)
    $('#feed-modify-modal-overlay').on('click', function (e) {
      if ($(e.target).is('#feed-modify-modal-overlay')) {
        $(this).fadeOut(200);
      }
    });
  });

// --- modify feed ---
$(document).ready(function() {
    // 이미지 업로드 및 미리보기
    const uploadBtn = $('#feed-modify-upload-btn');
    const fileInput = $('#feed-modify-file-input');
    const imagePreview = $('#image-preview');
    const previewContainer = $('#feed-modify-preview-container');

    uploadBtn.on('click', function() {
        fileInput.trigger('click'); // 파일 선택 창 열기
    });

    fileInput.on('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.attr('src', e.target.result); // 미리보기 설정
                previewContainer.show(); // 미리보기 보이기
            };
            reader.readAsDataURL(file);
        }
    });

    // 글자수 카운터
    const content = $('#feed-modify-content');
    const textCount = $('#feed-modify-text-count');
    const maxLength = 2000;

    content.on('input', function() {
        const currentLength = content.val().length;
        textCount.text(currentLength);

        // 글자수 초과 시 스타일 변경
        if (currentLength > maxLength) {
            textCount.css('color', 'red');
        } else {
            textCount.css('color', '');
        }
    });
});

// 오른쪽 사이드바 업데이트
function updateRightSidebar(feed) {
    const mealPicture = feed.getAttribute('data-meal-picture') || '/images/default-food.png';
	const mealWeight = feed.getAttribute('data-meal-weight') || '0';
	const mealCalories = feed.getAttribute('data-meal-calories') || '0';
    const mealCarbohydrates = feed.getAttribute('data-meal-carbohydrates') || '0';
    const mealProtein = feed.getAttribute('data-meal-protein') || '0';
    const mealFat = feed.getAttribute('data-meal-fat') || '0';

    const imgElement = document.querySelector('#analysis-img img');
    if (imgElement) imgElement.src = mealPicture;

	const weightElement = document.querySelector('#weight');
	if (weightElement) weightElement.textContent = `${mealWeight} kcal`;
    
	const caloriesElement = document.querySelector('#calories');
    if (caloriesElement) caloriesElement.textContent = `${mealCalories} kcal`;

    const carbElement = document.querySelector('#carb');
    if (carbElement) carbElement.textContent = `${mealCarbohydrates} g`;

    const proteinElement = document.querySelector('#protein');
    if (proteinElement) proteinElement.textContent = `${mealProtein} g`;

    const fatElement = document.querySelector('#fat');
    if (fatElement) fatElement.textContent = `${mealFat} g`;
}

// Intersection Observer 설정
document.addEventListener('DOMContentLoaded', () => {
    const feeds = document.querySelectorAll('.feed'); // 모든 피드 요소 선택
    const observerOptions = {
        root: null, // 뷰포트를 기준으로 감지
        threshold: 0.5 // 피드가 50% 이상 보일 때 감지
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                updateRightSidebar(entry.target); // 피드가 뷰포트 안에 들어오면 Right Sidebar 업데이트
            }
        });
    }, observerOptions);

    // 각 피드 요소에 Observer 연결
    feeds.forEach(feed => observer.observe(feed));
});

// --- 오른쪽 사이드바 영양 정보 테이블 업데이트 ---
document.querySelectorAll('.feed').forEach(feed => {
    feed.addEventListener('click', function () {
        // 데이터 속성 읽기
        const mealPicture = this.getAttribute('data-meal-picture') || '/images/default-food.png';
        const mealWeight = this.getAttribute('data-meal-weight') || '0';
        const mealCalories = this.getAttribute('data-meal-calories') || '0';
        const mealCarbohydrates = this.getAttribute('data-meal-carbohydrates') || '0';
        const mealProtein = this.getAttribute('data-meal-protein') || '0';
        const mealFat = this.getAttribute('data-meal-fat') || '0';

        // 데이터 업데이트
        const imgElement = document.querySelector('#analysis-img img');
        if (imgElement) imgElement.src = mealPicture;

		const weightElement = document.querySelector('#weight');
		if (weightElement) weightElement.textContent = `${mealWeight} kcal`;
		
        const caloriesElement = document.querySelector('#calories');
        if (caloriesElement) caloriesElement.textContent = `${mealCalories} kcal`;

        const carbElement = document.querySelector('#carb');
        if (carbElement) carbElement.textContent = `${mealCarbohydrates} g`;

        const proteinElement = document.querySelector('#protein');
        if (proteinElement) proteinElement.textContent = `${mealProtein} g`;

        const fatElement = document.querySelector('#fat');
        if (fatElement) fatElement.textContent = `${mealFat} g`;

        // 디버깅용 콘솔 출력
        console.log('Updated Meal Info:', { mealPicture, mealWeight, mealCalories, mealCarbohydrates, mealProtein, mealFat });
    })
});

// 원형 그래프 데이터와 설정
/*const pieCtx = document.getElementById('pieChart').getContext('2d');
const pieChart = new Chart(pieCtx, {
  type: 'pie',
  data: {
    labels: ['칼로리', '탄수화물', '단백질', '지방'],
    datasets: [{
      data: [554.025, 0, 289.1, 262.125], // 각각의 비율
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0']
    }]
  },
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
    }
  }
});*/