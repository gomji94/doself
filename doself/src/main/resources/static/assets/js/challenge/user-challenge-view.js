// --- open participate challenge feed ---
$(".card").on("click", function () {
    const url = $(this).attr("href"); // Thymeleaf에서 생성된 href 읽기
    if (url) {
        window.location.href = url; // 페이지 이동
    } else {
        console.error("카드에 href 속성이 없습니다.");
    }
});


// --- feed more load scroll(10 limit) ---
document.getElementById("loadMore").addEventListener("click", function () {
    const loadMoreBtn = this;
    const currentPage = parseInt(loadMoreBtn.dataset.currentPage, 10);
    const challengeCode = document.getElementById("challengeCode").value;

    // 다음 페이지 요청
    fetch(`/challenge/feed/view?challengeCodeValue=${challengeCode}&currentPage=${currentPage + 1}`, {
        method: "GET",
        headers: {
            "Content-Type": "text/html",
        },
    })
        .then(response => {
            if (!response.ok) throw new Error("서버 응답 오류");
            return response.text(); // 서버에서 HTML Fragment를 반환
        })
        .then(html => {
            const tempDiv = document.createElement("div");
            tempDiv.innerHTML = html;

            // 새로 받은 피드 데이터 추가
            const newFeeds = tempDiv.querySelectorAll(".feed");
            if (newFeeds.length === 0) {
                console.log("더 이상 불러올 피드가 없습니다.");
                loadMoreBtn.style.display = "none"; // 더보기 버튼 숨김
                return;
            }

            const feedContainer = document.getElementById("feedContainer");
            newFeeds.forEach(feed => feedContainer.appendChild(feed));

            // 페이지 정보 업데이트
            loadMoreBtn.dataset.currentPage = currentPage + 1;
            console.log(`현재 페이지: ${currentPage + 1}`);

            // 마지막 페이지인지 확인
            const isLastPage = newFeeds.length < 10; // 추가된 피드가 10개 미만이면 마지막 페이지로 간주
            if (isLastPage) {
                loadMoreBtn.style.display = "none"; // 더보기 버튼 비활성화
                console.log("마지막 페이지에 도달했습니다.");
            }
        })
        .catch(err => console.error("로딩 중 오류:", err));
});

// --- hidden load more button ---
window.addEventListener("DOMContentLoaded", function () {
    const loadMoreBtn = document.getElementById("loadMore");
    const feedCount = document.querySelectorAll(".feed").length;

    // 피드 개수가 10개 미만이면 '더보기' 버튼 숨김
    if (feedCount < 10) {
        loadMoreBtn.style.display = "none";
    }
});


// --- aside member list modal(+warning madal) ---
$('#cf_mbr_search-panel').on('click', '.open-memberlist-modal', function () {
    const challengeCode = $(this).data('challengeCode'); // 챌린지 코드 가져오기
    console.log("Challenge Code:", challengeCode); // 디버깅용 로그

    if (!challengeCode) {
        console.error("챌린지 코드가 비어있습니다.");
        return;
    }

    // Ajax 요청으로 데이터 가져오기
	$.ajax({
	    url: '/challenge/feed/memberlist',
	    type: 'GET',
	    data: { challengeCode: challengeCode },
	    success: function (response) {
	        console.log("Response received:", response); // JSON 데이터 확인
	        const memberListContainer = $('#cf-mbr-modal .challenge-mbr-list');
	        memberListContainer.empty(); // 기존 내용을 제거

	        if (response && response.length > 0) {
	            response.forEach(member => {
	                const memberHtml = `
	                    <div class="mbr-id">
	                        <img src="${member.memberProfileImage || '/images/default-profile.png'}" alt="프로필">
	                        <p class="user-icon">${member.memberId}</p>
	                        <span class="mbr-warning">경고</span>
	                        <button type="button">강퇴</button>
	                    </div>
	                `;
	                memberListContainer.append(memberHtml);
	            });
	        } else {
	            memberListContainer.html('<p>참여중인 멤버가 없습니다.</p>');
	        }
			// 오버레이와 모달 표시
	        $('#cf-mbr-modal-overlay').fadeIn();
	    },
    });
});

// 멤버 경고 클릭 이벤트
$('.mbr-warning').on('click', function () {
    // 열려 있는 모달 숨기기
    $('#cf-mbr-modal-overlay').fadeOut();

    // 경고 모달 표시
    $('#cf-warning-modal-overlay').fadeIn();
});

// 오버레이 바깥쪽 클릭 시 모달 닫기
$(document).on('click', function (e) {
    if ($(e.target).is('#cf-mbr-modal-overlay') || $(e.target).is('#cf-warning-modal-overlay')) {
        $(e.target).fadeOut();
    }
});

// 모달 닫기 버튼 클릭 시
$('#cf-mbr-modal-overlay').on('click', '#cf-mbr-modal-close', function () {
    $('#cf-mbr-modal-overlay').fadeOut();
});

$('#cf-warning-modal-overlay').on('click', '#cf-warning-modal-overlay', function () {
    $('#cf-warning-modal-overlay').fadeOut();
});

// ESC 키를 눌렀을 때 모달 닫기
$(document).on('keydown', function (e) {
    if (e.key === "Escape") {
        $('#cf-mbr-modal-overlay').fadeOut();
        $('#cf-warning-modal-overlay').fadeOut();
    }
});


// --- create challenge submit form ---
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


// --- create feed ---
$(document).ready(function () {
    // --- 모달 열기/닫기 ---
    const modalOverlay = $('#cf-modal-overlay');
    const modalContainer = $('.cf-modal-container');
    const modalClose = $('#cf-modal-closeBtn');

    // 모달 열기
	$('#createChallengeFeed').on('click', function (e) {
        e.preventDefault(); // 기본 동작 막기
        $('#cf-modal-overlay').fadeIn(300);
        $('.cf-modal-container').fadeIn(300);
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
        $('#AddChallengeFeed')[0].reset(); // 폼 내용 초기화
        $('#createChallengeFeedPreviewImage').attr('src', '').hide(); // 이미지 미리보기 초기화
        $('.cf-preview-box').hide(); // 미리보기 박스 숨기기
        $('#cf-content').text(''); // 글자 초기화
    }

    // --- 글자수 카운트 ---
    const content = $('#cf-content');
    const textCount = $('#cf-text-count');
    const maxLength = 2000;

    content.on('input', function () {
        const currentLength = content.val().length;
        textCount.text(currentLength);
        textCount.css('color', currentLength > maxLength ? 'red' : '');
    });

    // --- 파일 업로드 및 미리보기 ---
	$('#cf-upload-btn').on('click', function () {
        $('#feedFiles').click();
    });

    $('#feedFiles').on('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#createChallengeFeedPreviewImage').attr('src', e.target.result).show();
                $('#createChallengeFeedPreviewContainer').show();
            };
            reader.readAsDataURL(file);
        } else {
            $('#createChallengeFeedPreviewImage').attr('src', '').hide();
            $('#createChallengeFeedPreviewContainer').hide();
        }
    });
});


// --- feed option button ---
$(document).ready(function () {
    // 옵션 버튼 클릭 이벤트 (수정/삭제 모달 열기)
    $('.option-button').click(function () {
        const challengeFeedCode = $(this).attr('data-challenge-feed-code');

        if (!challengeFeedCode) {
            alert('챌린지 피드 코드를 찾을 수 없습니다.');
            return;
        }
		
        // 모달에 데이터 설정
        $('#cl-modify-modal').attr('data-challenge-feed-code', challengeFeedCode);
        $('#cl-delete-modal').attr('data-challenge-feed-code', challengeFeedCode);

        // 모달 열기
        $('.feed-option-modal-wrap').fadeIn();
    });

    // 수정 버튼 클릭 이벤트
    $('#cl-modify-modal').click(function () {
        // data-challenge-feed-code 속성 값 가져오기
        const challengeFeedCode = $(this).attr('data-challenge-feed-code');
        //const challengeFeedCode = $('#challengeFeedCode').val();

        if (!challengeFeedCode) {
			console.log('challengeFeedCode:', challengeFeedCode);
            alert('챌린지 피드 코드가 없습니다.');
			$('.popup-wrap').css('display', 'none');
            return;
        }

        // AJAX 요청으로 데이터 가져오기
        $.ajax({
            url: '/challenge/feed/modifychallengefeed',
            method: 'GET',
            data: { challengeFeedCode: challengeFeedCode },
            success: function (data) {
				$('.popup-wrap').css('display', 'none');
				
				//console.log(data);
				
                // 수정 모달 열기
				$('#challengeFeedCode').val(data.challengeFeedCode); 
				$('#challengeCode').val(data.challengeCode); 
				$('#challengeFeedFileIdx').val(data.challengeFeedFileIdx); 
										  
				$('#challengeModifyFeedPreviewImage').attr('src', data.challengeFeedFilePath);
				$('#modifyChallengeFeedMemberId').text(data.challengeMemberId);
				$('#modifyChallengeFeedMemberProfile').attr('src', data.challengeMemberProfilePath);
				
				$('#cf-modify-content').val(data.challengeFeedContent);
	            $('#serving').val(data.challengeFeedFoodIntake);
	            $('#meal-type').val(data.challengeMealCategory);
			   
                $('#cf-modify-modal-overlay').fadeIn();
            },
            error: function (err) {
                alert('데이터를 불러오는 데 실패했습니다.');
                console.error('Error:', err);
            },
			
        });
    });

    // 이미지 업로드 및 미리보기
    $('#cf-modify-upload-btn').click(function () {
        $('#feedModifyFiles').click();
    });

    $('#feedModifyFiles').change(function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#challengeModifyFeedPreviewImage').attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });
	
    // 글자수 카운터
    const content = $('#cf-modify-content');
    const textCount = $('#cf-modify-text-count');
    const maxLength = 2000;

    content.on('input', function () {
        const currentLength = content.val().length;
        textCount.text(currentLength);

        // 글자수 초과 시 스타일 변경
        if (currentLength > maxLength) {
            textCount.css('color', 'red');
        } else {
            textCount.css('color', '');
        }
    });

    // 수정 폼 제출 이벤트
	$('#ModifyChallengeFeedForm').on('submit', function (e) {
        e.preventDefault();
        // 폼 데이터를 jQuery로 전송
        $(this).unbind('submit').submit();
    });

    // 삭제 버튼 클릭 이벤트
	$(document).on("click", "#cl-delete-modal", function () {
	    const challengeFeedCode = $(this).data("challenge-feed-code");

	    if (!challengeFeedCode) {
	        alert("챌린지 피드 코드를 찾을 수 없습니다.");
	        return;
	    }

	    if (confirm("정말 삭제하시겠습니까?")) {
	        $.ajax({
	            url: "/challenge/feed/deletechallengefeedrequest",
	            type: "POST",
	            data: { challengeFeedCode: challengeFeedCode },
	            success: function () {
	                alert("삭제되었습니다.");
	                window.location.reload();
	            },
	            error: function () {
	                alert("삭제 중 문제가 발생했습니다.");
	            }
	        });
	    }
	});

    // 모달 닫기
	$(document).on('click', function (e) {
        if ($(e.target).is('.feed-option-modal-wrap, #optionCencleButton, #cf-modify-modal-overlay')) {
            $('.feed-option-modal-wrap, #cf-modify-modal-overlay').fadeOut(); // 클릭된 오버레이 닫기
        }
    });

	// ESC 키로 모달 닫기
    $(document).on('keydown', function (e) {
        if (e.key === 'Escape') {
            $('.feed-option-modal-wrap, #cf-modify-modal-overlay').fadeOut();
        }
    });

    // 수정 모달 닫기
    $('#cf-modify-modal-closeBtn').click(function () {
        $('#cf-modify-modal-overlay').fadeOut();
    });
});


// --- feed like button event ---
$('.likeBtn').click(function (event) {
    event.preventDefault();

    const likeBtn = $(this);
    const likeImg = likeBtn.find('.likeImg');
    const feedDescription = likeBtn.closest('.feed').find('#feed-likes');
    const challengeFeedCode = likeBtn.data('feed-code');
    const isLiked = likeBtn.attr('data-liked') === 'true';

    // AJAX 요청
    $.ajax({
        url: '/challenge/feed/like',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ challengeFeedCode: challengeFeedCode }),
        success: () => {
            let currentLikes = parseInt(feedDescription.text().match(/\d+/)[0], 10);

            // UI 업데이트
            if (isLiked) {
                likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png');
                currentLikes--;
            } else {
                likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png')
                       .css({ 'width': '24.7px', 'height': 'auto' });
                currentLikes++;
            }

            feedDescription.text(`좋아요 ${currentLikes}개`);
            likeBtn.attr('data-liked', (!isLiked).toString());
        },
        error: (error) => {
            console.error('좋아요 상태 업데이트 실패:', error);
            alert('좋아요 상태 업데이트에 실패했습니다. 다시 시도해주세요.');
        },
    });
});


/*$(document).on('click', '.likeBtn', function (event) {
    event.preventDefault();

    const likeBtn = $(this);
    const likeImg = likeBtn.find('.likeImg');
    const feedDescription = likeBtn.closest('.feed').find('#feed-likes');
    const challengeFeedCode = likeBtn.data('feed-code');
    const isLiked = likeBtn.attr('data-liked') === 'true';

    // AJAX 요청
    $.ajax({
        url: '/challenge/feed/like',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            challengeFeedCode: challengeFeedCode,
        }),
        success: () => {
            let currentLikes = parseInt(feedDescription.text().match(/\d+/)[0], 10);

            // UI 업데이트
            if (isLiked) {
                likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png');
                currentLikes--;
            } else {
                likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png')
					   .css({ 'width': '24.7px', 'height': 'auto' });
                currentLikes++;
            }

            feedDescription.text(`좋아요 ${currentLikes}개`);
            likeBtn.attr('data-liked', (!isLiked).toString());
        },
        error: (error) => {
            console.error('좋아요 상태 업데이트 실패:', error);
            alert('좋아요 상태 업데이트에 실패했습니다. 다시 시도해주세요.');
        },
    });
});*/


/*$(document).ready(function () {
	// 초기 렌더링 시 좋아요 상태 반영
    if (isLiked) {
        likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png')
               .css({ 'width': '24.7px', 'height': 'auto' });
    }
	
    $(document).on('click', '.likeBtn', function (event) {
        event.preventDefault();

        const likeBtn = $(this); // 현재 버튼
        const likeImg = likeBtn.find('.likeImg'); // 버튼 내부의 likeImg 요소
        const feedElement = likeBtn.closest('.feed'); // 현재 피드 요소
        const feedDescription = feedElement.find('#feed-likes'); // 좋아요 수 표시
        const challengeFeedCode = likeBtn.attr('data-feed-code'); // 피드 코드
        const isLiked = likeBtn.attr('data-liked') === 'true'; // 현재 좋아요 상태
        const newLikedStatus = !isLiked; // 새 좋아요 상태
		

        console.log('좋아요 버튼 클릭:', { challengeFeedCode, isLiked, newLikedStatus }); // 디버깅용 로그

        // AJAX 요청으로 서버에 좋아요 상태 전달
        $.ajax({
            url: '/challenge/feed/like',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                'challengeFeedCode': challengeFeedCode,
                liked: newLikedStatus,
            }),
            success: () => {
                let currentLikes = parseInt(feedDescription.text().match(/\d+/)[0], 10); // 좋아요 수 파싱

                // UI 업데이트
                if (newLikedStatus) {
                    likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png') // 좋아요 이미지
						   .css({ 'width': '24.7px', 'height': 'auto' });
                    currentLikes++;
                } else {
                    likeImg.attr('src', 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png'); // 기본 이미지
                    currentLikes--;
                }

                feedDescription.text(`좋아요 ${currentLikes}개`); // 좋아요 수 갱신
                likeBtn.attr('data-liked', newLikedStatus.toString()); // 상태 업데이트

                console.log('좋아요 상태 업데이트 완료:', { currentLikes, newLikedStatus });
            },
            error: (error) => {
                console.error('좋아요 상태 업데이트 실패:', error);
                alert('좋아요 상태 업데이트에 실패했습니다. 다시 시도해주세요.');
            },
        });
    });
});*/


/*.css({ 'width': '24.7px', 'height': 'auto' });*/


// --- feed comment submit ---
$(document).ready(function () {
    // 댓글 작성 버튼 클릭 시 폼 검증
    $("#addChallengeFeedCommentButton").on("click", function (e) {
        const commentContent = $("#addChallengeFeedCommentContent").val().trim();
        
        if (!commentContent) {
            alert("댓글을 입력해주세요.");
            e.preventDefault(); // 폼 전송 방지
            return false;
        }
        
        $("#AddChallengeFeedCommentForm").submit();
    });
});


// --- feed comment modal --- TODO 작업중
$(document).on('click', '.commentBtn', function () {
	const challengeCode = $('#challengeCode').val();
    const challengeFeedCode = $(this).data('challenge-code');
	const pictureFileImage = $(this).data('picture-file-image');
    //console.log("challengeFeedCode:", challengeFeedCode); // 디버깅용 로그
	//console.log("pictureFileImage:", pictureFileImage);
	
	console.log("challengeCode:", challengeCode);

    if (!challengeFeedCode) {
        alert("피드 코드가 없습니다.");
        return;
    }

    $.ajax({
        url: '/challenge/feed/comment',
        type: 'GET',
        data: { challengeFeedCode: challengeFeedCode },
        success: function (response) {
            console.log("댓글 데이터 로드 성공:", response);
			
			let imagePath = pictureFileImage;
			$('#image-preview').attr('src', imagePath);
			let comments = response;
            let commentHtml = '';
			
            if (!response || response.length === 0) {
                alert("댓글이 없습니다.");
                return;
            }

			if (comments && comments.length > 0) {
                comments.forEach(comment => {
					const isAuthor = comment.loggedInMemberId === comment.challengeFeedCommentAuthor; // 작성자 여부 확인
					console.log(comment.loggedInMemberId, comment.challengeFeedCommentAuthor, isAuthor);
					
                    commentHtml += `
                    <section data-comment-id="${comment.challengeFeedCommentCode}">
                        <div class="cf-comment-user-block">
                            <div class="cf-comment-content-block">
                                <img class="comment-profile" src="${comment.challengeCommentAuthorImage}" alt="프로필">
                                <a href="#" class="cf-comment-user-link" id="feedCommentAuthorId">${comment.challengeFeedCommentAuthor}</a>
                                <div class="cf-comment-feed-comment">
                                   <span>${comment.challengeFeedCommentContent}</span>
								   <div class="cf-mofify-comment-feed-comment" style="display: none;">
					                  <span th:text="${comment.challengeFeedCommentContent}" class="comment-text"></span>
									  <input type="text" class="comment-edit-input" value="${comment.challengeFeedCommentContent}" style="display: none;" />
                                   </div>
                                </div>
                            </div>
						    <div class="comment-actions" id="modifyAndDeleteCommentButton" style="display: ${isAuthor ? 'block' : 'none'};">
							    <button type="button" class="edit-btn" data-comment-id="${comment.challengeFeedCommentCode}" data-content="${comment.challengeFeedCommentContent}">수정</button>
                                <button type="button" class="delete-btn" data-comment-id="${comment.challengeFeedCommentCode}">삭제</button>
                            </div>
                        </div>
                    </section>`;
                });
            } else {
                commentHtml = '<p>댓글이 없습니다. 첫 댓글을 작성해보세요!</p>';
            };

	        $('.cf-user-comment-container').html(commentHtml);
	        $('#feedCommentModalOverlay').fadeIn(300);
        },
		error: function (error) {
            console.error("댓글 데이터 로드 실패:", error);
            alert("댓글 데이터를 가져오는 중 오류가 발생했습니다.");
        }
    });
});
	// 댓글 수정 버튼 클릭 이벤트
	$(document).on('click', '.edit-btn', function () {
		const parentSection = $(this).closest('section'); // 현재 댓글 섹션
	    const commentEditContainer = parentSection.find('.cf-mofify-comment-feed-comment');
	    const commentText = parentSection.find('.comment-text');
	    const originalContent = $(this).data('content');

	    // 텍스트 숨기고, 수정 input 표시
		commentEditContainer.css('display', 'block');
		commentText.hide();
	    commentEditContainer.show();
		$('.cf-mofify-comment-feed-comment').css('display', 'block');
		$('.comment-edit-input').css('display', 'block');
		
	    commentEditContainer.find('.comment-edit-input').val(originalContent).show();

		// 다른 댓글의 수정 상태 초기화
	    $('.cf-mofify-comment-feed-comment').not(commentEditContainer).hide();
	    $('.comment-text').not(commentText).show();
	    $('.save-btn').text('수정').addClass('edit-btn').removeClass('save-btn');
	    $('.cancel-btn').text('삭제').addClass('delete-btn').removeClass('cancel-btn');

	    // 버튼 상태 변경 (수정 -> 저장, 삭제 -> 취소)
		
	    $(this).text('저장').addClass('save-btn').removeClass('edit-btn');
	    parentSection.find('.delete-btn').text('취소').addClass('cancel-btn').removeClass('delete-btn');
	});

    // 댓글 저장 버튼 클릭 이벤트
    $(document).on('click', '.save-btn', function () {
		const parentDiv = $(this).closest('.cf-comment-user-block');
	    const commentEditContainer = parentDiv.find('.cf-mofify-comment-feed-comment');
	    const commentText = parentDiv.find('.comment-text');
	    const commentId = $(this).data('comment-id');
	    const newContent = commentEditContainer.find('.comment-edit-input').val();
		
        if (!newContent.trim()) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

		$.ajax({
	        url: '/challenge/feed/modifycommentrequest',
	        type: 'POST',
	        data: { 'challengeFeedCommentCode': commentId, 'challengeFeedCommentContent': newContent },
			dataType : 'json',
	        success: function (isModify) {
				if(isModify) {
		            //alert("댓글이 수정되었습니다.");
		            commentText.text(newContent).show(); // 수정된 텍스트를 표시
		            commentEditContainer.hide(); // 수정 input 숨기기
					location.reload();
				}

	            // 버튼 상태 복구
	            $('.save-btn').text('수정').addClass('edit-btn').removeClass('save-btn');
	            $('.cancel-btn').text('삭제').addClass('delete-btn').removeClass('cancel-btn');
	        },
	        error: function () {
	            alert("댓글 수정 중 오류가 발생했습니다.");
	        }
	    });

        $(this).text('수정').addClass('edit-btn').removeClass('save-btn');
    });
	
	// 댓글 취소 버튼 클릭 이벤트
	$(document).on('click', '.cancel-btn', function () {
	    const parentDiv = $(this).closest('.cf-comment-user-block');
	    const commentEditContainer = parentDiv.find('.cf-mofify-comment-feed-comment');
	    const commentText = parentDiv.find('.comment-text');
	    const originalContent = $(this).siblings('.save-btn').data('content'); // 원래 댓글 내용 가져오기

	    // DB의 댓글 내용으로 초기화
	    commentEditContainer.find('.comment-edit-input').val(originalContent);

	    // 기존 텍스트 표시 및 수정 input 숨기기
	    commentText.show();
	    commentEditContainer.hide();

	    // 버튼 상태 복구
	    $('.save-btn').text('수정').addClass('edit-btn').removeClass('save-btn');
	    $(this).text('삭제').addClass('delete-btn').removeClass('cancel-btn');
	});

    // 댓글 삭제 버튼 클릭 이벤트
    $(document).on('click', '.delete-btn', function () {
        const challengeFeedCommentCode = $(this).data('comment-id');
        if (confirm('댓글을 삭제하시겠습니까?')) {
            $.ajax({
                url: '/challenge/feed/deletecommentrequest',
                type: 'POST',
                data: { 'challengeFeedCommentCode': challengeFeedCommentCode },
				dataType : 'json',
                success: function (isDelete) {					
					if(isDelete){						
	                    alert("댓글이 삭제되었습니다.");
	                   // $(`button[data-comment-id='${challengeFeedCommentCode}']`).closest('section').remove();
					}
					location.reload();
					
                },
                error: function (error) {
                    console.error("댓글 삭제 실패:", error);
                    alert("댓글 삭제 중 오류가 발생했습니다.");
                }
            });
        }
    });

    // 댓글 등록 버튼 클릭 이벤트
    $(document).on('click', '.comment-btn, #feedCommentModalButton', function () {
		
        let challengeFeedCode = $('.commentBtn').data('challenge-code');
        let commentContent = $('input[placeholder="댓글 달기..."]').val();
		if($(this).hasClass("comment-btn")) {
			challengeFeedCode = $(this).attr('data-challenge-code');
			commentContent = $(this).prev().val();
		}
        if (!commentContent.trim()) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        $.ajax({
            url: '/challenge/feed/createcommentrequest',
            type: 'POST',
            data: {
                challengeFeedCode: challengeFeedCode,
                challengeFeedCommentContent: commentContent
            },
			dataType : 'json',
            success: function (isCreate) {
				if(isCreate) {
	                alert("댓글이 등록되었습니다.");
	                $('input[placeholder="댓글 달기..."]').val('');
	                $('#feedCommentModalOverlay').fadeOut(300);
				}
                location.reload();
            },
            error: function (error) {
				location.reload();
                console.error("댓글 등록 실패:", error);
                alert("댓글 등록이 완료되지 않았습니다.");
            }
        });
    });
	
	// 모달 닫기 버튼 클릭 이벤트
	$(document).on('click', '.feedCommentModalCloseBtn', function () {
	    $('#feedCommentModalOverlay').fadeOut(300); // 모달 닫기
	});

	// 오버레이 클릭 이벤트
	$(document).on('click', '#feedCommentModalOverlay', function (e) {
	    if ($(e.target).is('#feedCommentModalOverlay')) {
	        $('#feedCommentModalOverlay').fadeOut(300); // 모달 닫기
	    }
	});

	// ESC 키 누르기 이벤트
	$(document).on('keydown', function (e) {
	    if (e.key === 'Escape') {
	        $('#feedCommentModalOverlay').fadeOut(300); // 모달 닫기
	    }
	});



// --- challenge progress circle ---
$(document).ready(function () {
    const greenImg = $('#green-img');
    const progressText = $('.progress-text'); // 진행률 텍스트
    const progressCircle = $('.foreground-circle'); // 원형 그래프
    const progressPercentage = parseFloat(greenImg.data('progress')) || 0; // 진행률 데이터

    function updateProgress(targetPercentage) {
        let currentPercentage = 0;

        function updateAnimation() {
            if (currentPercentage < targetPercentage) {
                currentPercentage += 0.5; // 증가 폭 (더 빠르게 차오르도록 설정)
                
                // green-img가 왼쪽부터 차오르는 효과
                greenImg.css({
                    'clip-path': `inset(0 ${100 - currentPercentage}% 0 0)`, // 잘리는 영역 변경
                    'width': '100%', // 부모 가로 크기 유지
                    'height': '100%' // 부모 세로 크기 유지
                });

                // 원형 그래프 진행률 계산
                const circleOffset = 314 - (314 * currentPercentage) / 100;
                progressCircle.css('stroke-dashoffset', circleOffset);

                // 텍스트 업데이트
                progressText.text(`${currentPercentage.toFixed(1)}%`);
                
                requestAnimationFrame(updateAnimation); // 애니메이션 프레임 요청
            } else {
                // 최종 값 설정
                greenImg.css({
                    'clip-path': `inset(0 ${100 - targetPercentage}% 0 0)`
                });
                progressCircle.css('stroke-dashoffset', 314 - (314 * targetPercentage) / 100);
                progressText.text(`${targetPercentage}%`);
            }
        }

        updateAnimation(); // 애니메이션 시작
    }

    // 애니메이션 실행
    updateProgress(progressPercentage);
});


// --- participants top3 member update ---
let isUpdatingParticipants = false; // 상태 변수 추가

function updateTopParticipants(challengeCode) {
    if (isRequestInProgress) return; // 이미 요청 중이면 실행 안 함
    isRequestInProgress = true;

    $.ajax({
        url: '/challenge/feed/top-participants',
        type: 'GET',
        data: { challengeCode },
        success: function (response) {
            const participantsList = $('.participants-list ul');
            participantsList.empty();
            response.forEach(member => {
                participantsList.append(`
                    <li>
                        <img src="${member.memberProfile}" alt="멤버 프로필">
                        <p>${member.memberId}</p>
                        <div class="progress-bar">
                            <div class="progress-fill" style="width: ${member.score}%"></div>
                        </div>
                        <span>${member.score}%</span>
                    </li>
                `);
            });
        },
        error: function (error) {
            console.error('Error updating participants:', error);
        },
        complete: function () {
            isRequestInProgress = false; // 요청 완료 후 플래그 초기화
        }
    });
}


// --- D+, D- calculate ---
function updateDates(challengeCode) {
	if (isUpdatingParticipants) return; // 중복 호출 방지
    $.ajax({
        url: '/challenge/feed/dates',
        type: 'GET',
        data: { challengeCode },
        success: function (response) {
            $('.info-box .info-item:nth-child(1) p:last-child').text(response.dPlus);  // 투데이
            $('.info-box .info-item:nth-child(3) p:last-child').text(response.dMinus); // 남은기간
        },
        error: function (error) {
            console.error('Error updating dates:', error);
        },
		complete: function () {
            isUpdatingParticipants = false; // 요청 완료 후 상태 초기화
        }
    });
}