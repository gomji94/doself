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
    // 생성 버튼 클릭 시 모달 열기
    $('#create-challenge-open-btn').on('click', function () {
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


// --- challenge detail info modal ---
$(document).ready(function () {
    // card-1 클릭 시 모달 열기
    $('#card-1').on('click', function () {
        $('#card-modal-overlay').fadeIn(); // 오버레이 표시
        $('#card-modal').fadeIn(); // 모달 표시
    });

    // 모달 닫기 버튼 클릭 시
    $('#card-modal-close').on('click', function () {
        $('#card-modal-overlay').fadeOut(); // 오버레이 숨김
        $('#card-modal').fadeOut(); // 모달 숨김
    });

    // 오버레이 클릭 시 모달 닫기
    $('#card-modal-overlay').on('click', function () {
        $('#card-modal-overlay').fadeOut(); // 오버레이 숨김
        $('#card-modal').fadeOut(); // 모달 숨김
    });
});