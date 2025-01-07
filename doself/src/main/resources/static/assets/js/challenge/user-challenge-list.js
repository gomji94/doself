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
    // 카드 클릭 이벤트 핸들러
    $('.card').on('click', 'card-modal', function () {
        const challengeId = $(this).data('challenge-code');   // 챌린지 코드 가져오기
		console.log("challengeId: ", challengeId);
		
		$.ajax({
            url: `/challenge/list/view`, // 서버 URL
            type: 'GET',
            data: { ChallengeCode: challengeId },
            success: function (response) {
				$('#card-modal h2').text(response.challengeName); // 예: 챌린지 이름
				// 오버레이 내용 업데이트
				$('card-modal-overlay').html(response);
				$('#card-modal-overlay').fadeIn(); // 모달 오버레이 표시
                $('#card-modal').fadeIn(); // 모달 표시
            },
            error: function () {
                alert('챌린지 정보를 가져오는 데 실패했습니다.');
            }
        });
    });

    // 모달 닫기 버튼 클릭 시
    $('#card-modal-close, #card-modal-overlay').on('click', function () {
        $('#card-modal-overlay').fadeOut(); // 오버레이 숨김
        $('#card-modal').fadeOut(); // 모달 숨김
    });
});

    // 서버에서 상세 데이터 가져오기
   /* function fetchChallengeDetails(challengeId) {
        $.ajax({
            url: `/challenge/list/view`, // 백엔드 URL
            type: 'GET',
            data: { ChallengeCode: challengeId },
            success: function (data) {
                // 데이터를 기반으로 모달 업데이트
                $('#card-modal h2').text(data.challengeName); // 예: 챌린지 이름
                //$('#image-preview').attr('src', data.challengePicture); // 예: 이미지 URL
                //$('#challenge-tag').text(data.challengeLeaderName); // 리더 이름
                $('#info-content-detail').html(`
                    <p>📌 챌린지 소개 📌</p>
                    <p>🗓 챌린지 일정: ${data.challengeStartDate} ~ ${data.challengeEndDate}</p>
                    <p>🎯 난이도: ${data.challengeTopicLevel}</p>
                    <p>📝 진행 내용: ${data.challengeContent}</p>
                    <p>🤗‍ 참여 인원: ${data.challengeCurrentMember} / ${data.challengeMaxMember}</p>
                `);
                $('#card-modal-overlay').fadeIn(); // 모달 오버레이 표시
                $('#card-modal').fadeIn(); // 모달 표시
            },
            error: function () {
                alert('챌린지 정보를 가져오는 데 실패했습니다.');
            }
        });
    }*/

/**
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
*/