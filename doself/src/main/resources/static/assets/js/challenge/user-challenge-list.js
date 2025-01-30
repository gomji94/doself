// --- challenge card page change ---
$(document).ready(function () {
    $('.page-link').on('click', function (event) {
        event.preventDefault();
        const url = $(this).attr('href');
        window.location.href = url;
    });
});


// --- create challenge submit form ---
$(document).ready(function () {
    // --- 모달 열기/닫기 ---
    const modalOverlay = $('#createChallengeModalOverlay');
    const modalContainer = $('#createChallengeModal');
    const modalClose = $('#modal-close');

    // 모달 열기
    $('#createChallengeOpenButton').on('click', function () {
        modalOverlay.fadeIn(300);
        modalContainer.fadeIn(300);
    });

    // 모달 닫기
    function closeModal() {
        modalOverlay.fadeOut(300);
        modalContainer.fadeOut(300);
        resetForm(); // 폼 초기화
    }

    modalClose.on('click', closeModal);
    modalOverlay.on('click', function (e) {
        if ($(e.target).is(modalOverlay)) {
            closeModal();
        }
    });

    $(document).on('keydown', function (e) {
        if (e.key === 'Escape') {
            closeModal();
        }
    });

    // --- 폼 초기화 ---
    function resetForm() {
        $('#addChallenge')[0].reset(); // 폼 내용 초기화
        $('#createChallengePreviewImage').attr('src', '').hide(); // 이미지 미리보기 초기화
        $('#createChallengePreviewContainer').hide(); // 미리보기 컨테이너 숨기기
        $('#challengeNameError, #challengeLevelError, small').hide(); // 에러 메시지 숨기기
        $('#text-count').text('0'); // 글자수 초기화
    }

    // --- 챌린지 이름 중복 확인 및 유효성 검증 ---
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
            data: JSON.stringify({ challengeName }),
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

    challengeNameInput.on('input', function () {
        challengeNameError.hide();
    });

    // --- 글자수 카운터 ---
    const content = $('#content');
    const textCount = $('#text-count');
    const maxLength = 500;

    content.on('input', function () {
        const currentLength = content.val().length;
        textCount.text(currentLength);
        textCount.css('color', currentLength > maxLength ? 'red' : '');
    });

    // --- 난이도와 시작일 검증 ---
    const levelSelect = $('#selectLevel');
    const levelError = $('#challengeLevelError');
    const startDateInput = $('#challengeStartDate');
    const startDateError = $('#challengeStrartDateError');

    levelSelect.on('change', function () {
        if (!levelSelect.val()) {
            levelError.show();
        } else {
            levelError.hide();
        }
    });

    startDateInput.on('change', function () {
        if (!startDateInput.val()) {
            startDateError.show();
        } else {
            startDateError.hide();
        }
    });

    // --- 폼 제출 유효성 검증 ---
    $('#addChallenge').on('submit', function (e) {
        let isValid = true;

        if (!levelSelect.val()) {
            levelError.show();
            isValid = false;
        }
        if (!startDateInput.val()) {
            startDateError.show();
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault();
            alert('필수 입력 값을 작성해주세요');
        }
    });

    // --- 파일 업로드 및 미리보기 ---
    $('#createChallengeUploadButton').on('click', function () {
        $('#files').click();
    });

    $('#files').on('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#createChallengePreviewImage').attr('src', e.target.result).show();
                $('#createChallengePreviewContainer').show();
            };
            reader.readAsDataURL(file);
        } else {
            $('#createChallengePreviewImage').attr('src', '').hide();
            $('#createChallengePreviewContainer').hide();
        }
    });

    // --- 폼 제출 ---
    $('#addChallenge').on('submit', function (e) {
        e.preventDefault();
        const formData = new FormData(this);

        $.ajax({
            url: '/challenge/list/createchallengerequest',
            method: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
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
$(document).on('click', '.card', function () {
	$('#card-modal').find('input, p').val('').text('').attr('src', '');
    const challengeCode = $(this).data("code"); // 카드의 데이터 코드 가져오기

    // AJAX 요청
    $.ajax({
        url: `/challenge/list/view?challengeCode=${challengeCode}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data) {
                // 동적으로 HTML 요소 업데이트
                $('#challenge-name').text(data.challengeName);
                $('#image-preview').attr("src", data.challengeImage);
                //$('#profile').attr("src", data.challengeLeaderImage);
                $('#leader-link').text(data.challengeLeaderId);

                $('#info-content-detail').html(`
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
                $('#error-message').hide();

                // 해당 모달만 표시
                $('#card-modal-overlay').css('display', 'block');
                $('#card-modal').css('display', 'block');
            } else {
                // 데이터가 없을 경우 에러 메시지 표시
                $('#error-message').text('챌린지 정보를 불러올 수 없습니다.').show();
            }
            console.log('AJAX Response:', data);
            console.log('Challenge Name:', data.challengeName);
            console.log('Challenge Image:', data.challengeImage);
        },
        error: function (err) {
            console.error('데이터 로드 실패:', err);
            $('#error-message').text('데이터를 불러오는데 실패했습니다.').show();
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


// --- challenge card code save ---
$(document).on('click', '.card', function () {
    const challengeCode = $(this).data('code');
    $('#card-modal').data('challengeCode', challengeCode); // 모달에 코드 저장
});


// --- challenge 
$(document).on('click', '#participationChallenge', function () {
	// 모달에 저장된 challengeCode 가져오기
    let challengeCode = $('#card-modal').data('challengeCode');
    const challengeMemberId = $('#leader-link').text().trim(); // 세션에서 가져오기

    // 서버에서 상태 코드 가져오기
    $.ajax({
        url: `/challenge/list/view`,
        method: 'GET',
        data: { challengeCode: challengeCode },
        success: function (response) {
            const challengeStatusCode = response.challengeStatus; // 서버 응답에서 상태 코드 추출

            // 참여 요청
            $.ajax({
                url: '/challenge/list/view/participation',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    challengeCode: response.challengeCode,
                    challengeMemberId: response.challengeMemberId,
                    challengeStatusCode: response.challengeStatusCode // 상태 코드 포함
                }),
                success: function (response) {
					if (response.success) {
			            alert(response.message); // 참여 완료
						location.reload();
			        } else {
			            alert(response.message); // 이미 참여중인 경우 알림
			        }
                },
                error: function () {
                    alert('참여 처리 중 오류가 발생했습니다. 다시 시도해주세요.');
                }
            });
        },
        error: function () {
            alert('상태 코드를 가져오는 중 오류가 발생했습니다.');
        }
    });
});



$(document).ready(function () {
    const emojiButton = $('#emojiButton');
    const emojiDropdown = $('<div class="emoji-dropdown"></div>'); // 이모지 드롭다운 생성

    // 평균적으로 많이 사용되는 50개 이모지 리스트
    const emojis = [
        '😀', '😃', '😄', '😁', '😆', '😅', '😂', '🤣', '😊', '😇',
        '😍', '😘', '🥰', '😗', '😙', '😚', '🤩', '🤗', '😜', '😝',
        '😛', '🤑', '🤪', '😎', '🤓', '😏', '😒', '🙄', '😞', '😔',
        '😟', '😕', '☹️', '🙁', '😣', '😖', '😫', '😩', '🥺', '😢',
        '😭', '😤', '😠', '😡', '🤬', '🤯', '😳', '🥵', '🥶', '😱'
    ];

    // 이모지 리스트 생성
    emojis.forEach((emoji) => {
        const emojiElement = $('<span class="emoji"></span>').text(emoji);
        emojiElement.on('click', function () {
            $('#content').val($('#content').val() + emoji); // 이모지 추가
            emojiDropdown.hide(); // 드롭다운 닫기
        });
        emojiDropdown.append(emojiElement);
    });

    // 드롭다운 스타일 적용 (버튼 아래 표시)
    emojiDropdown.css({
        'position': 'absolute',
        'background': 'white',
        'border': '1px solid #ccc',
        'padding': '10px',
        'box-shadow': '2px 2px 10px rgba(0,0,0,0.6)',
        'display': 'grid',
        'grid-template-columns': 'repeat(10, 1fr)', // 10개씩 가로 정렬
        'gap': '5px',
        'border-radius': '5px',
        'z-index': '1000',
        'width': '287px',
        'max-height': '200px',
        'overflow-y': 'auto'
    });

    $('body').append(emojiDropdown); // body에 추가
    emojiDropdown.hide(); // 초기 숨김

    // 버튼 클릭 시 드롭다운 위치 설정 및 표시
    emojiButton.on('click', function () {
        let offset = emojiButton.offset();
        emojiDropdown.css({
            'top': offset.top + emojiButton.outerHeight() + 5 + 'px', // 버튼 아래 배치
            'left': offset.left + 'px'
        });
        emojiDropdown.toggle(); // 표시/숨김 토글
    });

    // 외부 클릭 시 드롭다운 닫기
    $(document).on('click', function (e) {
        if (!emojiButton.is(e.target) && !emojiDropdown.is(e.target) && emojiDropdown.has(e.target).length === 0) {
            emojiDropdown.hide();
        }
    });
});
