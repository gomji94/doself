// --- 피드 옵션 모달 ---
$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('.option-button').on('click', function () {
		const feedElement = $(this).closest('.feed'); // 현재 피드 요소
        const isOwner = feedElement.data('is-owner'); // 본인 피드 여부

        if (isOwner) {
            // 본인 피드 옵션 모달 표시
            $('.feed-option-modal-wrap').fadeIn();
        } else {
            // 다른 멤버 피드 옵션 모달 표시
            $('.other-members-option-modal-wrap').fadeIn();
        }
    });

    // 닫기 버튼 클릭 시 모달창 닫기
    $('.feed-option-modal-wrap .close').on('click', function () {
        $('.feed-option-modal-wrap').fadeOut(); // 모달창 비활성화
    });
	
	$('.other-members-option-modal-wrap .close').on('click', function () {
        $('.other-members-option-modal-wrap').fadeOut(); // 다른 멤버 피드 모달창 닫기
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('.feed-option-modal-wrap').on('click', function (e) {
        if ($(e.target).is('.feed-option-modal-wrap')) {
            $(this).fadeOut();
        }
    });
	
	$('.other-members-option-modal-wrap').on('click', function (e) {
	    if ($(e.target).is('.other-members-option-modal-wrap')) {
	        $(this).fadeOut();
	    }
	});
	
	// 타멤버 피드 옵션에서 "신고" 클릭 시 신고 모달 열기
    $('#feed-declaration-modal').on('click', function () {
        $('.other-members-option-modal-wrap').fadeOut(); // 기존 모달 닫기
        $('#feed-declaration-modal-overlay').fadeIn(); // 신고 모달 열기
    });

    // 신고 모달 닫기 버튼 클릭
    $('#feed-declaration-modal-overlay .close').on('click', function () {
        $('#feed-declaration-modal-overlay').fadeOut(); // 신고 모달 비활성화
    });

    // 신고 모달 바깥 클릭 시 닫기
    $('#feed-declaration-modal-overlay').on('click', function (e) {
        if ($(e.target).is('#feed-declaration-modal-overlay')) {
            $(this).fadeOut();
        }
    });
});

// --- 상세 피드 옵션 모달 ---
$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('.df-option-button').on('click', function () {
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

// --- 피드 좋아요 증감 ---
$(document).ready(function () {
    $(document).on('click', '.likeBtn', function (event) {
        event.preventDefault(); // 기본 동작 방지

        const likeImg = $(this).find('.likeImg'); // 버튼 내부의 likeImg 요소 선택
        const likedSrc = 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png'; // "좋아요" 이미지 경로
        const defaultSrc = 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png'; // 기본 이미지 경로

        const feedElement = $(this).closest('.feed'); // 현재 버튼이 포함된 피드 요소
        const feedDescription = feedElement.find('#feed-likes'); // 피드의 좋아요 수 표시 요소
        const feedNum = feedElement.attr('id').split('-')[1]; // 피드 ID 가져오기
        const isLiked = $(this).attr('data-liked') === 'true'; // 현재 좋아요 상태 확인
        const newLikedStatus = !isLiked; // 새 좋아요 상태

        // AJAX 요청으로 좋아요 상태 업데이트
        $.ajax({
            url: '/feed/like', // 서버 엔드포인트
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                feedNum: feedNum,
                liked: newLikedStatus
            }),
            success: () => {
                let currentLikes = parseInt(feedDescription.text().match(/\d+/)[0], 10); // 현재 좋아요 수 파싱

                if (newLikedStatus) {
                    // 좋아요 상태로 변경
                    likeImg.attr('src', likedSrc).css({ 'width': '24.5px', 'height': 'auto' });
                    currentLikes++; // 좋아요 수 증가
                } else {
                    // 기본 상태로 복구
                    likeImg.attr('src', defaultSrc);
                    currentLikes--; // 좋아요 수 감소
                }

                feedDescription.text(`좋아요 ${currentLikes}개`); // UI 업데이트
                $(this).attr('data-liked', newLikedStatus.toString()); // 새로운 상태 저장
            },
            error: (error) => {
                console.error('좋아요 상태 업데이트 실패:', error);
                alert('좋아요 상태 업데이트에 실패했습니다. 다시 시도해주세요.');
            }
        });
    });
});

// --- 피드 댓글 모달 ---
$(document).ready(function () {
    const $searchInput = $('#searchFood');
    const $resultsContainer = $('#searchResults');

    // 검색 입력 이벤트
    $searchInput.on('input', function () {
        const query = $(this).val().trim();

        if (query.length < 2) {
            $resultsContainer.hide();
            return;
        }

        // 서버에서 데이터 요청
        $.ajax({
            url: '/feed/search-food',
            type: 'GET',
            data: { query: query },
            success: function (data) {
                if (data && data.length > 0) {
                    // 검색 결과 표시
                    $resultsContainer.empty().show();
                    data.forEach(item => {
                        $resultsContainer.append(`<div class="dropdown-item">${item}</div>`);
                    });
                } else {
                    $resultsContainer.hide();
                }
            },
            error: function (error) {
                console.error('Error fetching food suggestions:', error);
                $resultsContainer.hide();
            }
        });
    });

    // 결과 선택 이벤트
    $resultsContainer.on('click', '.dropdown-item', function () {
        const selectedValue = $(this).text();
        $searchInput.val(selectedValue);
        $resultsContainer.hide();
    });

    // 입력 필드 외 클릭 시 결과 숨기기
    $(document).on('click', function (e) {
        if (!$(e.target).closest('.input-group').length) {
            $resultsContainer.hide();
        }
    });
});

// --- 피드 생성 모달 ---
$(document).ready(function () {
    // 피드 추가 버튼 클릭 이벤트
    $('#feed-create').on('click', function () {
        // 모달 표시
        $('#feed-create-modal-overlay').fadeIn(300); // 모달 오버레이 표시
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('#feed-create-modal-closeBtn').on('click', function () {
        $('#feed-create-modal-overlay').fadeOut(300); // 모달 오버레이 숨기기
    });

    // 모달 오버레이 클릭 시 모달 닫기
    $('#feed-create-modal-overlay').on('click', function (e) {
        if ($(e.target).is('#feed-create-modal-overlay')) {
            $(this).fadeOut(300);
        }
    });
});

// --- 피드 추가 유효성 검사 및 추가 ---
$(document).ready(function () {
    $('#feed-create-submit-btn').on('click', function () {
        // 값 가져오기
		const feedPicture = $('#feed-create-file-input')[0].files[0];
	    const feedContent = $('#feed-create-d-feed-content').val();
		const searchedFood = $('#searchFood').val().trim(); // 음식 이름 검색 값
	    const feedFoodIntake = $('#serving').val();
	    const mealCategoryCode = $('#meal-type').val();
	    const feedOpenStatus = $('input[name="visibility"]:checked').val() === 'public' ? 1 : 0;

        // 유효성 검사
        if (!feedPicture || feedPicture === '') {
            alert('사진을 업로드해주세요.');
            return;
        }

        if (!feedContent || feedContent.trim().length === 0) {
            alert('피드 내용을 작성해주세요.');
            return;
        }

		if (!searchedFood || searchedFood.length < 2) {
            alert('음식 이름을 검색하고 선택해주세요.');
            return;
        }
		
        if (!feedFoodIntake) {
            alert('섭취 인분을 선택해주세요.');
            return;
        }

        if (!mealCategoryCode) {
            alert('식사 분류를 선택해주세요.');
            return;
        }

		if (!feedOpenStatus) {
		    alert('공개 여부를 선택해주세요.');
		    return;
		}

        // 유효성 검사가 통과되었을 경우
		const formData = new FormData();
	    formData.append('feedPicture', feedPicture);
	    formData.append('feedContent', feedContent);
		formData.append('searchedFood', searchedFood); // 검색한 음식 이름 추가
	    formData.append('feedFoodIntake', feedFoodIntake);
	    formData.append('mealCategoryCode', mealCategoryCode);
	    formData.append('feedOpenStatus', feedOpenStatus);
		
		console.log(feedPicture)

        // AJAX 요청으로 피드 추가
		$.ajax({
		        url: '/feed/createFeed',
		        type: 'POST',
		        data: formData,
		        processData: false,
		        contentType: false,
		        success: function (response) {
		            alert(response);
		            location.reload(); // 페이지 새로고침
		        },
		        error: function (error) {
		            alert('피드 생성에 실패했습니다.');
					
	            console.error(error);
				console.log('feedPicture:', feedPicture);
				console.log('feedContent:', feedContent);
				console.log('searchedFood', searchedFood);
				console.log('feedFoodIntake:', feedFoodIntake);
				console.log('mealCategoryCode:', mealCategoryCode);
				console.log('feedOpenStatus:', feedOpenStatus);
            },
        });
    });
});

// --- 파일 업로드와 글자 수 카운팅 기능 ---
$(document).ready(function () {
    // 이미지 업로드 버튼 클릭 시 파일 선택 트리거
    $('#feed-create-upload-btn').on('click', function () {
        $('#feed-create-file-input').trigger('click');
    });

    // 파일 업로드 시 이미지 미리보기
    $('#feed-create-file-input').on('change', function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#feed-create-image-preview')
                    .attr('src', e.target.result)
                    .css('display', 'block'); // 미리보기 이미지 표시
                $('#plate').css('display', 'none'); // 빈 접시 이미지 숨김
            };
            reader.readAsDataURL(file);
        }
    });

    // 글자 수 카운팅
    const maxLength = 2000; // 최대 글자 수
    $('#feed-create-d-feed-content').on('input', function () {
        const textLength = $(this).val().length; // 현재 글자 수
        $('#feed-create-text-count').text(textLength); // 카운터 업데이트

        // 글자 수 초과 시 스타일 변경
        if (textLength > maxLength) {
            $('#feed-create-text-count').css('color', 'red');
        } else {
            $('#feed-create-text-count').css('color', '');
        }
    });
});

// --- 피드 수정 모달 ---
$(document).ready(function () {
    // 수정 버튼 클릭 시
    $('#feed-modify-modal').on('click', function () {
        // 클릭된 피드 데이터 가져오기
        const feedElement = $(this).closest('.feed');

        // 피드 데이터 추출
        const feedImage = feedElement.data('meal-picture'); // 이미지 URL
        const feedContent = feedElement.find('.feed-description p:nth-child(2)').text(); // 내용
        const feedServing = feedElement.find('.add-comment input[type="number"]').val(); // 섭취 인분
        const mealCategoryCode = feedElement.find('.input-group select').val(); // 식사 분류
        const feedVisibility = feedElement.find('.radio-group input[name="visibility"]:checked').val(); // 공개 여부

        // 수정 모달에 데이터 삽입
        $('#feed-modify-image-preview').attr('src', feedImage);
        $('#feed-modify-content').val(feedContent);
        $('#feed-modify-serving').val(feedServing);
        $('#feed-modify-meal-type').val(mealCategoryCode);
        $(`.radio-group input[name="visibility"][value="${feedVisibility}"]`).prop('checked', true);

        // 글자수 카운터 업데이트
        const textLength = feedContent.length;
        $('#feed-modify-text-count').text(textLength);

        // 모달 열기
        $('#feed-modify-modal-overlay').fadeIn(300);
    });

    // 모달 닫기
    $('#feed-modify-modal-closeBtn').on('click', function () {
        $('#feed-modify-modal-overlay').fadeOut(300);
    });

    // 모달 외부 클릭 시 닫기
    $('#feed-modify-modal-overlay').on('click', function (e) {
        if ($(e.target).is('#feed-modify-modal-overlay')) {
            $(this).fadeOut(300);
        }
    });

    // 파일 업로드 버튼
    $('#feed-modify-upload-btn').on('click', function () {
        $('#feed-modify-file-input').trigger('click');
    });

    // 파일 업로드 시 이미지 미리보기
    $('#feed-modify-file-input').on('change', function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#feed-modify-image-preview').attr('src', e.target.result).show();
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
    const mealPicture = feed.getAttribute('data-meal-picture') || '/default-food.png';
	const mealWeight = feed.getAttribute('data-meal-weight') || '0';
	const mealCalories = feed.getAttribute('data-meal-calories') || '0';
    const mealCarbohydrates = feed.getAttribute('data-meal-carbohydrates') || '0';
    const mealProtein = feed.getAttribute('data-meal-protein') || '0';
    const mealFat = feed.getAttribute('data-meal-fat') || '0';

    const imgElement = document.querySelector('#analysis-img img').src = mealPicture;
    if (imgElement) imgElement.src = mealPicture;

	const weightElement = document.querySelector('#weight');
	if (weightElement) weightElement.textContent = `${mealWeight} g`;
    
	const caloriesElement = document.querySelector('#calories');
    if (caloriesElement) caloriesElement.textContent = `${mealCalories} kcal`;

    const carbElement = document.querySelector('#carb');
    if (carbElement) carbElement.textContent = `${mealCarbohydrates} kcal`;

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
        threshold: 0.7 // 피드가 70% 이상 보일 때 감지
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
        const imgElement = document.querySelector('#analysis-img img').src = mealPicture;
        if (imgElement) imgElement.src = mealPicture;

		const weightElement = document.querySelector('#weight');
		if (weightElement) weightElement.textContent = `${mealWeight} g`;
		
        const caloriesElement = document.querySelector('#calories');
        if (caloriesElement) caloriesElement.textContent = `${mealCalories} kcal`;

        const carbElement = document.querySelector('#carb');
        if (carbElement) carbElement.textContent = `${mealCarbohydrates} kcal`;

        const proteinElement = document.querySelector('#protein');
        if (proteinElement) proteinElement.textContent = `${mealProtein} g`;

        const fatElement = document.querySelector('#fat');
        if (fatElement) fatElement.textContent = `${mealFat} g`;
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