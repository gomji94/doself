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