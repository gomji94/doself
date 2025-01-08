// --- aside member list modal ---
$(document).ready(function () {
	// 챌린지 멤버 조회 클릭 이벤트
    $('#cf_mbr_search-panel').on('click', '.open-memberlist-modal', function () {
        const challengeCode = $(this).data('challenge-code'); // 챌린지 코드 가져오기
        console.log("Challenge Code:", challengeCode); // 디버깅용 로그

        // Ajax 요청으로 데이터 가져오기
        $.ajax({
            url: '/challenge/feed/memberlist', // 서버 URL
            type: 'GET',
            data: { challengeCode: challengeCode },
            success: function (response) {
                console.log("Response received:", response); // 응답 데이터 확인
				// 기존 내용을 지운 후 업데이트
                $('.cf-mbr-modal-overlay').empty().html(response);
                // 오버레이와 모달 표시
                $('.cf-mbr-modal-overlay').css('display', 'block');
                //$('#cf-mbr-modal-overlay').fadeIn(); // 오버레이 활성화
                $('#cf-mbr-modal').fadeIn(); // 모달 활성화
            }
        });
    });

    // 모달 닫기 이벤트
    $('#cf-mbr-modal-overlay').on('click', '#cf-mbr-modal-close', function () {
        $('#cf-mbr-modal-overlay').fadeOut(); // 오버레이 닫기
    });

    // 오버레이 클릭 시 닫기
    $('#cf-mbr-modal-overlay').on('click', function (e) {
        if ($(e.target).is('#cf-mbr-modal-overlay')) {
            $(this).fadeOut(); // 오버레이 닫기
        }
    });

    // feed-warning-modal-overlay 열기
    $('.mbr-warning').on('click', function () {
        // 다른 모달 숨기기
        $('.modal-overlay').addClass('modal-hidden');
        $('#cf-warning-modal-overlay').removeClass('modal-hidden').fadeIn();
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('#cf-warning-modal-overlay').on('click', function (e) {
        if ($(e.target).is('#cf-warning-modal-overlay')) {
            $(this).fadeOut();
        }
    });

    // cf-warning-modal-overlay 닫기
    $('#cf-warning-modal-close').on('click', function () {
        $('#cf-warning-modal-overlay').fadeOut();
    });
});


// --- create challenge ---
$(document).ready(function() {
    const uploadBtn = $('#cl-create-upload-btn'); // 파일 불러오기 버튼
    const fileInput = $('#cl-create-file-input'); // 파일 input 요소
    const imagePreview = $('#cl-create-preview-image'); // 미리보기 이미지
    const previewContainer = $('#cl-create-preview-container'); // 미리보기 컨테이너

    // 파일 불러오기 버튼 클릭 이벤트
    uploadBtn.on('click', function() {
        fileInput.click(); // 파일 선택 창 열기
    });

    // 파일 선택 시 이벤트
    fileInput.on('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.attr('src', e.target.result); // 이미지 소스를 미리보기로 설정
                previewContainer.show(); // 미리보기 컨테이너 보이기
            };
            reader.readAsDataURL(file); // 파일을 DataURL로 읽기
        }
    });

    // 글자수 카운터
    const content = $('#content'); // 텍스트 입력 박스
    const textCount = $('#text-count'); // 글자 수 카운터
    const maxLength = 500; // 최대 글자 수

    content.on('input', function() {
        const currentLength = content.val().length;
        textCount.text(currentLength);

        if (currentLength > maxLength) {
            textCount.css('color', 'red'); // 초과 시 색상 변경
        } else {
            textCount.css('color', ''); // 기본 색상
        }
    });
});


// --- create challenge modal ---
$(document).ready(function () {
    // 모달 열기
    $("#cl-create").on("click", function () {
        $("#cl-create-modal-overlay").fadeIn(300); // 부드럽게 표시
    });

    // 모달 닫기 (닫기 버튼 클릭)
    $("#modal-close").on("click", function () {
        $("#cl-create-modal-overlay").fadeOut(300); // 부드럽게 숨기기
    });

    // 모달 닫기 (오버레이 클릭)
    $("#cl-create-modal-overlay").on("click", function (e) {
        if ($(e.target).is("#cl-create-modal-overlay")) { // 오버레이 클릭 시만 닫기
            $(this).fadeOut(300);
        }
    });
});


// --- create feed ---
$(document).ready(function() {
    // 이미지 업로드 및 미리보기
    const uploadBtn = $('.cf-upload-btn');
    const imagePreview = $('#cf-image-preview');

    uploadBtn.on('click', function() {
        const input = $('<input>', {
            type: 'file',
            accept: 'image/*'
        });

        input.on('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function() {
                    imagePreview.attr('src', reader.result);
                    imagePreview.show();
                };
                reader.readAsDataURL(file);
            }
        });

        input.trigger('click');
    });

    // 글자수 카운터
    const content = $('#cf-content');
    const textCount = $('#cf-text-count');

    content.on('input', function() {
        textCount.text(content.val().length);
    });
});


// --- create feed modal ---
$(document).ready(function () {
    // 모달 열기
    $("#cf-create").on("click", function () {
        $("#cf-modal-overlay").fadeIn(300); // 부드럽게 모달 열기
    });

    // 모달 닫기 (닫기 버튼)
    $("#cf-modal-closeBtn").on("click", function () {
        $("#cf-modal-overlay").fadeOut(300); // 부드럽게 모달 닫기
    });

    // 모달 닫기 (오버레이 클릭)
    $("#cf-modal-overlay").on("click", function (e) {
        if ($(e.target).is("#cf-modal-overlay")) {
            $(this).fadeOut(300); // 오버레이 배경 클릭 시 닫기
        }
    });
});


// --- modify feed ---
$(document).ready(function() {
    // 이미지 업로드 및 미리보기
    const uploadBtn = $('#cf-modify-upload-btn');
    const fileInput = $('#cf-modify-file-input');
    const imagePreview = $('#image-preview');
    const previewContainer = $('#cf-modify-preview-container');

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
    const content = $('#cf-modify-content');
    const textCount = $('#cf-modify-text-count');
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


// --- modify feed modal ---
$(document).ready(function () {
    // 모달 열기
    $('#cl-modify-modal').on('click', function () {
      $('#cf-modify-modal-overlay').fadeIn(200);
    });
  
    // 모달 닫기 (닫기 버튼 클릭 시)
    $('#cf-modify-modal-closeBtn').on('click', function () {
      $('#cf-modify-modal-overlay').fadeOut(200);
    });
  
    // 모달 닫기 (오버레이 클릭 시)
    $('#cf-modify-modal-overlay').on('click', function (e) {
      if ($(e.target).is('#cf-modify-modal-overlay')) {
        $(this).fadeOut(200);
      }
    });
  });


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
        const commentModal = $('.cf-comment-modal-overlay'); // 댓글 모달 오버레이
        commentModal.fadeIn(300); // 부드럽게 모달 표시
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.cf-comment-modalCloseBtn').on('click', function () {
        $('.cf-comment-modal-overlay').fadeOut(300); // 부드럽게 모달 숨기기
    });

    // 모달 오버레이 클릭 시 모달 닫기
    $('.cf-comment-modal-overlay').on('click', function (e) {
        if ($(e.target).is('.cf-comment-modal-overlay')) {
            $(this).fadeOut(300);
        }
    });
});


// --- challenge progress circle ---
$(document).ready(function() {
    let currentProgress = 0;
    const targetProgress = 83; // 목표 값
    const progressCircle = $('.foreground-circle');
    const progressPercent = $('#progress-percent-txt');

    // 그래프 업데이트 함수
    function updateProgress() {
        if (currentProgress < targetProgress) {
            currentProgress++;
            const offset = 314 - (314 * currentProgress) / 100; // 계산된 stroke-dashoffset
            progressCircle.css('stroke-dashoffset', offset);
            progressPercent.text(`${currentProgress}%`);
            requestAnimationFrame(updateProgress); // 부드러운 애니메이션
        }
    }

    // 페이지 로드 시 그래프 시작
    updateProgress();
});


// --- challenge progress img ---
$(document).ready(function() {
    const greenImg = $('#green-img'); // 초록 그래프 이미지
    const progressText = $('.progress-text'); // 진행률 텍스트
    let progressPercentage = 0; // 현재 진행률

    function updateProgress(targetPercentage) {
        const interval = setInterval(() => {
            if (progressPercentage < targetPercentage) {
                progressPercentage++;

                // 초록 그래프의 가로 크기 조정 (왼쪽부터 차오르게 설정)
                $('.front-progress-bar').css({
                    'clip-path': `inset(0 ${100 - progressPercentage}% 0 0)`, // 왼쪽부터 차오르는 애니메이션
                    'width': '100%', // 부모의 가로를 유지
                    'height': '100%' // 세로 크기 유지
                });

                // 진행률 텍스트 업데이트
                progressText.text(`${progressPercentage}%`);
            } else {
                clearInterval(interval); // 목표치 도달 시 애니메이션 정지
            }
        }, 10); // 애니메이션 속도 조정
    }

    // 테스트용 목표 달성률 설정 (83%)
    updateProgress(83);
});
