// --- challenge card page change ---
$(document).ready(function () {
    $('.page-link').on('click', function (event) {
        event.preventDefault();
        const url = $(this).attr('href');
        window.location.href = url;
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
$(document).on("click", ".card", function () {
    const challengeCode = $(this).data("code"); // 카드의 데이터 코드 가져오기

    // AJAX 요청
    $.ajax({
        url: `/challenge/list/view?challengeCode=${challengeCode}`,
        method: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                // 동적으로 HTML 요소 업데이트
                $("#challenge-name").text(data.challengeName || "챌린지 이름 없음");
                $("#image-preview").attr("src", data.challengeImage || "default-image-url.png");
                $("#profile").attr("src", data.challengeLeaderImage || "default-profile-url.png");
                $("#leader-link").text(data.challengeLeaderId || "리더 정보 없음");

                $("#info-content-detail").html(`
                    <p>📌 챌린지 소개 📌</p>
                    <p>🗓 챌린지 일정: ${formatDate(data.challengeStartDate)} ~ ${formatDate(data.challengeEndDate)}</p>
                    <p>🎯 난이도: ${data.challengeTopicLevel || "난이도 정보 없음"}</p>
                    <p>📝 진행 내용: ${data.challengeLevelContent || "진행 내용 없음"}</p>
                    <p>🤗‍ 참여 인원: ${data.challengeCurrentMember || 0} / ${data.challengeMaxMember || 0}</p>
                    <p>📢 필독 📢</p>
                    <p>친목질, 종교권유, 이성만남목적, 정치질 🙅‍♀️</p>
                    <p>※ 공지 안 지키면 경고 없이 경고합니다</p>
                    <p>(3회 누적 시, 자동 탈퇴 처리)</p>
                    <p>${data.challengeContent || ""}</p>
                `);

                // 에러 메시지 숨기기
                $("#error-message").hide();

                // 해당 모달만 표시
                $("#card-modal-overlay").css("display", "block");
                $("#card-modal").css("display", "block");
            } else {
                // 데이터가 없을 경우 에러 메시지 표시
                $("#error-message").text("챌린지 정보를 불러올 수 없습니다.").show();
            }
            console.log("AJAX Response:", data);
            console.log("Challenge Name:", data.challengeName);
            console.log("Challenge Image:", data.challengeImage);
        },
        error: function (err) {
            console.error("데이터 로드 실패:", err);
            $("#error-message").text("데이터를 불러오는데 실패했습니다.").show();
        }
    });
});

// 모달 닫기 이벤트
$(document).on("click", "#card-modal-close, #card-modal-overlay", function () {
    // 해당 모달 닫기
    $("#card-modal-overlay").css("display", "none");
    $("#card-modal").css("display", "none");
});

// 날짜 형식 변환 함수
function formatDate(dateString) {
    if (!dateString) return "날짜 정보 없음";
    const date = new Date(dateString);
    return `${date.getFullYear()}-${(date.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
}




/*
$(document).ready(function () {
    $('.card').on('click', function () {
        const getChallengeCode = $(this).data('code'); // 클릭된 카드의 데이터 속성에서 challengeCode 가져오기
		const request = $.ajax({
			url : '/challenge/list/view',
			method : 'GET',
			data : { challengeCode: getChallengeCode },
			dataType: 'json'
		});
		request.done((data) => {
			if(data) {
				// 모달 보이기
				$('#card-modal-overlay').fadeIn();
				$('#card-modal').fadeIn();
				
				// 데이터가 성공적으로 로드되면 모달 내용 업데이트
		        $('#card-modal #card-body').html(data);
			}
		});
		request.fail((jqXHR, textStatus, error)=>{
		console.log(error)
		});
	});
    // 모달 닫기 버튼 클릭 시
    $('#card-modal-close, #card-modal-overlay').on('click', function () {
        $('#card-modal-overlay').fadeOut(); // 오버레이 숨김
        $('#card-modal').fadeOut(); // 모달 숨김
    });
});
*/

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