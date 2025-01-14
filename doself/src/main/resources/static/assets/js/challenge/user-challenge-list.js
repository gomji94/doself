// --- challenge card page change ---
$(document).ready(function () {
    $('.page-link').on('click', function (event) {
        event.preventDefault();
        const url = $(this).attr('href');
        window.location.href = url;
    });
});


// --- open create challenge modal ---
$(document).ready(function () {
    // 생성 버튼 클릭 시 모달 열기
    $('#createChallengeOpenButton').on('click', function () {
        $('#createChallengeModalOverlay').fadeIn(300); // 모달 오버레이 표시
        $('#createChallengeModal').fadeIn(300); // 모달 표시
    });

    // 모달 닫기 버튼 클릭 시
    $('#modal-close').on('click', function () {
        $('#createChallengeModalOverlay').fadeOut(300); // 모달 오버레이 숨기기
        $('#createChallengeModal').fadeOut(300); // 모달 숨기기
    });

    // 오버레이 클릭 시 모달 닫기
    $('#createChallengeModalOverlay').on('click', function (e) {
        if ($(e.target).is('#createChallengeModalOverlay')) {
            $(this).fadeOut(300);
            $('#createChallengeModal').fadeOut(300);
        }
    });

    // ESC 키 누를 시 모달 닫기
    $(document).on('keydown', function (e) {
        if (e.key === 'Escape') {
            $('#createChallengeModalOverlay').fadeOut(300);
            $('#createChallengeModal').fadeOut(300);
        }
    });
});



// --- create challenge input duplicate & validation ---
$(document).ready(function () {
    const challengeNameInput = $('#challengeName');
    const challengeNameError = $('#challengeNameError');

    challengeNameInput.on('blur', function () {
        const challengeName = challengeNameInput.val().trim();

        if (!challengeName) {
            challengeNameError.text('이름을 입력해주세요').show();
            return;
        }

        // 중복 확인 AJAX 요청
        $.ajax({
            url: '/challenge/checkDuplicateName',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ challengeName: challengeName }),
            success: function (response) {
                if (!response.available) {
                    challengeNameError.text('이미 사용 중인 이름입니다. 다른 이름을 입력해주세요.').show();
                } else {
                    challengeNameError.hide();
                }
            },
            error: function () {
                challengeNameError.text('서버 오류가 발생했습니다. 다시 시도해주세요.').show();
            }
        });
    });

    // 입력 중에는 에러 메시지 숨기기
    challengeNameInput.on('input', function () {
        challengeNameError.hide();
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


// --- create challenge image preview & form submit ---
$(document).ready(function () {
	// 파일 선택 창 열기
    $('#createChallengeUploadButton').on('click', function () {
        $('#createChallengeFileInput').click(); // 파일 선택 창 열기
    });

    // 파일 선택 시 미리보기
    $('#createChallengeFileInput').on('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#createChallengePreviewImage').attr('src', e.target.result); // 이미지 미리보기 설정
                $('#createChallengePreviewContainer').show(); // 미리보기 컨테이너 표시
            };
            reader.readAsDataURL(file); // 파일 읽기
        }
    });

    // 폼 제출 이벤트
    $('#addChallenge').on('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);

        // 디버깅용: FormData 내용 확인
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        $.ajax({
            url: '/challenge/list/createchallengerequest',
            method: 'POST',
            processData: false,
            contentType: false,
            data: formData,
            success: function () {
                alert('챌린지가 성공적으로 생성되었습니다.');
                location.reload();
            },
            error: function (xhr) {
                console.error('Error:', xhr.responseText);
                alert('챌린지 생성 중 오류가 발생했습니다.');
            }
        });
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
                $("#challenge-name").text(data.challengeName);
                $("#image-preview").attr("src", data.challengeImage);
                $("#profile").attr("src", data.challengeLeaderImage);
                $("#leader-link").text(data.challengeLeaderId);

                $("#info-content-detail").html(`
                    <p>📌 챌린지 소개 📌</p>
                    <p>🗓 챌린지 일정 : ${formatDate(data.challengeStartDate)} ~ ${formatDate(data.challengeEndDate)}</p>
                    <p>🎯 난이도 : ${data.challengeTopicLevel}</p>
                    <p>📝 진행 내용 : ${data.challengeLevelContent}</p>
                    <p>🤗‍ 참여 인원 : ${data.challengeCurrentMember} / ${data.challengeMaxMember}</p>
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
    const emojiButton = $('#emojiButton');
    const emojiDropdown = $('<div class="emoji-dropdown"></div>'); // 이모지 드롭다운 생성

    // 간단한 이모지 리스트
    const emojis = ['😀', '😂', '🥰', '👍', '🎉'];
    emojis.forEach((emoji) => {
        const emojiElement = $('<span class="emoji"></span>').text(emoji);
        emojiElement.on('click', function () {
            $('#content').val($('#content').val() + emoji); // 이모지 추가
            emojiDropdown.hide(); // 드롭다운 닫기
        });
        emojiDropdown.append(emojiElement);
    });

    // 이모지 드롭다운 표시
    emojiButton.on('click', function () {
        emojiDropdown.toggle();
    });

    $('body').append(emojiDropdown); // 드롭다운을 body에 추가
    emojiDropdown.hide(); // 초기 숨김
});

*/