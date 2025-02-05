// --- 피드 옵션 모달 ---
$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('.option-button').on('click', function () {
        const feedElement = $(this).closest('.feed'); // 현재 피드 요소
        const isOwner = feedElement.data('is-owner'); // 본인 피드 여부
        const feedCode = $(this).data('feed-code'); // 현재 피드 코드 가져오기
		const mbrId = feedElement.attr('data-mbr-id');
        const feedUrl = `/feed/view?feedCode=${feedCode}`; // 링크 생성
		const feedContent = feedElement.attr('data-feed-content');
		
		if (!feedCode || !mbrId || !feedContent) {
            alert('피드 정보를 가져오지 못했습니다.');
            return;
        }

        // 신고 버튼에 피드 정보 설정
        $("#feed-declaration-modal").attr("data-feed-num", feedCode);
        $("#feed-declaration-modal").attr("data-feed-id", mbrId);
        $("#feed-declaration-modal").attr("data-feed-content", feedContent);
		
		// 피드 URL을 버튼에 설정
		if (isOwner) {
            $('#my-feed-link-copy a').attr('data-feed-url', feedUrl);
            $('.feed-option-modal-wrap').fadeIn();
        } 
        // 타멤버 피드 옵션 모달
        else {
            $('#other-feed-link-copy a').attr('data-feed-url', feedUrl);
            $('.other-members-option-modal-wrap').fadeIn();
        }
		
        if (isOwner) {
            $('.feed-option-modal-wrap').fadeIn();
        } else {
            $('.other-members-option-modal-wrap').fadeIn();
        }
    });

    // 타멤버 피드 옵션에서 "신고" 클릭 시 신고 모달 열기
    $(document).on("click", "#feed-declaration-modal", function () {
        let feedNum = $("#feed-declaration-modal").attr("data-feed-num"); // 피드 번호 가져오기
        let mbrId = $("#feed-declaration-modal").attr("data-feed-id"); // 신고 대상 회원 ID
		let feedContent = $("#feed-declaration-modal").attr("data-feed-content"); // 신고 대상 피드 내용 가져오기

        if (!feedNum || !mbrId || !feedContent) {
            alert("신고할 피드 정보가 없습니다.");
            return;
        }

        // 신고 모달에 데이터 설정
        $("#feed-declaration-modal-overlay").fadeIn(); // 신고 모달 띄우기
    });

    // 본인 피드 링크 복사
	$(document).on('click', '#my-feed-link-copy a', function (e) {
        e.preventDefault();
        const feedUrl = $(this).attr('data-feed-url'); // URL 읽기

		if (!feedUrl || feedUrl.trim() === '') {
	        alert('복사할 링크가 없습니다.');
	        return;
	    }

        const fullUrl = window.location.origin + feedUrl;

		// 최신 클립보드 API 사용
        navigator.clipboard.writeText(fullUrl)
            .then(() => {
                alert(`피드 링크가 복사되었습니다: ${fullUrl}`);
            })
            .catch(err => {
                alert('링크 복사 중 오류가 발생했습니다.');
                console.error('Copy error:', err);
            });
    });
	
	// 타멤버 피드 링크 복사 (별도 이벤트 적용)
	$(document).on('click', '#other-feed-link-copy a', function (e) {
        e.preventDefault();
        const feedUrl = $(this).attr('data-feed-url');

        if (!feedUrl || feedUrl.trim() === '') {
            alert('복사할 링크가 없습니다.');
            return;
        }

        const fullUrl = window.location.origin + feedUrl;

        // 최신 클립보드 API 사용
        navigator.clipboard.writeText(fullUrl)
            .then(() => {
                alert(`피드 링크가 복사되었습니다: ${fullUrl}`);
            })
            .catch(err => {
                alert('링크 복사 중 오류가 발생했습니다.');
                console.error('Copy error:', err);
            });
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

    // 신고 유형 선택 시 AJAX로 서버에 신고 요청 보내기
	$(document).on("click", "#feed-declaration-modal-overlay ul li.pop", function () {
        let reportType = $(this).text().trim(); // 신고 유형 텍스트 가져오기
        if (reportType === "취소") {
            $("#feed-declaration-modal-overlay").fadeOut(); // 취소 시 모달 닫기
            return;
        }

        let feedNum = $("#feed-declaration-modal").attr("data-feed-num"); // 피드 코드 가져오기
        let mbrId = $("#feed-declaration-modal").attr("data-feed-id"); // 신고 대상 회원 ID 가져오기
		let feedContent = $("#feed-declaration-modal").attr("data-feed-content"); // 신고 대상 피드 내용 가져오기


        if (!feedNum || !mbrId || !feedContent) {
            alert("신고할 피드 정보가 없습니다.");
            return;
        }

        let reportData = {
            rrBcNum: feedNum, // 신고 당한 피드 코드
            mbrId: mbrId, // 신고 대상 회원 ID
            rcCode: getReportCategoryCode(reportType), // 신고 유형 코드 매핑
            rrContent: feedContent // 신고 대상 피드 내용
        };

        $.ajax({
            url: "/feed/report",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(reportData),
            success: function (response) {
                alert("신고가 접수되었습니다.");
                $("#feed-declaration-modal-overlay").hide(); // 모달 닫기
            },
            error: function (xhr, status, error) {
                console.error("신고 실패:", xhr.responseText);
                alert("신고 접수에 실패했습니다.");
            }
        });
    });

    // 신고 유형을 신고 코드(rc_code)로 변환하는 함수
    function getReportCategoryCode(reportType) {
        const reportCategories = {
            "불법 컨텐츠": "rc_001",
            "사기 및 사칭": "rc_002",
            "폭력적 및 유해 콘텐츠": "rc_003",
            "욕설 및 비방": "rc_004",
            "스팸 및 광고": "rc_005",
            "허위 사실 유포": "rc_006"
        };
        return reportCategories[reportType] || "rc_999"; // 기본 코드 설정
    }
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
        const feedNum =  feedElement.data('feed-code'); // 피드 ID 가져오기
        const isLiked = $(this).attr('data-liked') === 'true'; // 현재 좋아요 상태 확인
        const newLikedStatus = !isLiked; // 새 좋아요 상태

        // AJAX 요청으로 좋아요 상태 업데이트
        $.ajax({
            url: '/feed/like',
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


// 글자 수 카운트 증가
$(document).ready(function () {
    const maxLength = 2000;

    $('#feedContent').on('input', function () {
        const textLength = $(this).val().length;
        $('#textCount').text(textLength);

        // 글자 수 초과 시 스타일 변경
        if (textLength > maxLength) {
            $('#textCount').css('color', 'red');
        } else {
            $('#textCount').css('color', '');
        }
    });
});

// --- 피드 생성 업로드 이벤트 ---
$(document).ready(function () {
    // 파일 업로드 버튼 클릭 이벤트
    $('#feed-create-upload-btn').on('click', function (e) {
        e.preventDefault(); // 기본 동작 방지
        $('#feed-create-file-input').trigger('click'); // 파일 입력 필드 클릭 트리거
    });

    // 파일 입력 필드 변경 이벤트
    $('#feed-create-file-input').on('change', function (e) {
        const file = e.target.files[0]; // 선택된 파일 가져오기
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                // 이미지 미리보기 업데이트
                $('#feed-create-image-preview')
                    .attr('src', e.target.result)
                    .css('display', 'block'); // 이미지 미리보기 표시
                $('#plate').css('display', 'none'); // 기본 이미지 숨기기
            };
            reader.readAsDataURL(file); // 파일 읽기
        }
    });
});

// --- 섭취 날짜 초기값 설정 ---
document.addEventListener('DOMContentLoaded', () => {
    const intakeDateTime = document.getElementById('intakeDateTime');
    if (intakeDateTime) {
        const now = new Date();
        const offset = now.getTimezoneOffset() * 60000; // Timezone offset
        const localISOTime = new Date(now - offset).toISOString().slice(0, 16);
        intakeDateTime.value = localISOTime; // 올바른 초기값 설정
    }
});

// --- 피드 추가 유효성 검사 및 추가 ---
$('#feed-create-submit-btn').on('click', function (e) {
    e.preventDefault();

    const feedPicture = $('#feed-create-file-input').val();
    const feedContent = $('#feed-create-d-feed-content').val().trim();
    const feedFoodIntake = $('#serving').val();
    const mealCategoryCode = $('#meal-type').val();
    const feedOpenStatus = $('input[name="visibility"]:checked').val();

    if (!feedPicture) {
        alert('사진을 업로드해주세요.');
        return;
    }
    if (!feedContent) {
        alert('내용을 작성해주세요.');
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

    const formData = new FormData();
    formData.append('feedPicture', $('#feed-create-file-input')[0].files[0]);
    formData.append('feedContent', feedContent);
    formData.append('feedFoodIntake', feedFoodIntake);
    formData.append('mealCategoryCode', mealCategoryCode);
    formData.append('feedOpenStatus', feedOpenStatus);

    $.ajax({
        url: '/feed/createFeed',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            alert('피드가 성공적으로 생성되었습니다.');
            location.reload();
        },
        error: function () {
            alert('피드 생성 중 오류가 발생했습니다.');
        }
    });
});

// --- 피드 수정 모달 ---
$(document).ready(function () {
    // 옵션 버튼 클릭 시
	$('.option-button').click(function () {
		const feedCode = $(this).data('feed-code');
		
		if (!feedCode) {
					
            alert('피드 코드를 찾을 수 없습니다.');
            return;
        }
		
		$('#feed-modify-modal').attr('data-feed-code', feedCode);
		$('#feed-delete-modal').attr('data-feed-code', feedCode);
		
		$('#feed-option-modal-wrap').fadeIn();
	});
	
	// 글자수 카운트 업데이트 함수
    function updateTextCount() {
        const content = $('#modify-feedContent');
        const textCount = $('#feed-modify-text-count');
        const maxLength = 2000;

        const currentLength = content.val().length;
        textCount.text(currentLength);

        if (currentLength > maxLength) {
            textCount.css('color', 'red');
        } else {
            textCount.css('color', '');
        }
    }
	
	// 글자 입력 시 글자수 카운트 업데이트
    $('#modify-feedContent').on('input', updateTextCount);
	
	// 수정 버튼 클릭 이벤트
	$('#feed-modify-modal').click(function() {
		const feedCode = $(this).attr('data-feed-code');
		
		if (!feedCode) {
							
	        alert('피드 코드를 찾을 수 없습니다.');
	        return;
	    }
				
        $.ajax({
            url: '/feed/modifyfeed', // 서버에서 데이터를 가져올 엔드포인트
            method: 'GET',
            data: { feedCode: feedCode },
            success: function (data) {
				$('.popup-wrap').css('display', 'none');
	            // 데이터 바인딩
	            $('#feedCode').val(data.feedCode);
	            $('#feedFileIdx').val(data.feedFileIdx);
	            $('#modify-feedContent').val(data.feedContent);
	            $('#modify-intakeDateTime').val(data.feedIntakeDate);
	            $('#modify-mealCategoryCode').val(data.mealCategoryCode);
	            $('#modify-feedFoodIntake').val(data.feedFoodIntake);
	            $(`input[name="feedOpenStatus"][value="${data.feedOpenStatus}"]`).prop('checked', true);
				
				// **이미지 미리보기 업데이트**
	            if (data.feedFilePath) {
	                $('#modifyFeedPreviewImage').attr('src', data.feedFilePath); // 이미지 경로 설정
	                $('#createFeedPreviewContainer').show(); // 컨테이너 표시
	            } else {
	                $('#modifyFeedPreviewImage').attr('src', ''); // 이미지 초기화
	                $('#createFeedPreviewContainer').hide(); // 컨테이너 숨김
	            }
				
	            // 모달 열기
	            $('#feed-modify-modal-overlay').fadeIn();
	        },
	        error: function (error) {
	            console.error('피드 데이터를 가져오는 중 오류가 발생했습니다:', error);
	            alert('피드 데이터를 가져오는 데 실패했습니다.');
	        }
	    });
		
	});
	
	// "이미지 변경" 버튼 클릭 시 파일 업로드 창 열기
    $('#feed-modify-upload-btn').click(function () {
        $('#feedModifyFiles').click(); // ✅ ID 수정됨
    });

    // 파일 선택 시 이미지 미리보기 업데이트
    $('#feedModifyFiles').change(function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $('#modifyFeedPreviewImage').attr('src', e.target.result);
                $('#createFeedPreviewContainer').show();
            };
            reader.readAsDataURL(file);
        }
    });

    $('#feed-modify-form').on('submit', function (e) {
        e.preventDefault();
        $(this).unbind('submit').submit();
    });
		
	const content = $('#modify-feedContent');
    const textCount = $('#feed-modify-text-count');
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
		
    $('#feed-modify-form').on('submit', function (e) {
        e.preventDefault();
		$(this).unbind('submit').submit();
	});
		
	// 삭제 버튼 클릭 이벤트
	$(document).on("click", "#feed-delete-modal", function () {
	    const feedCode = $(this).data("feed-code");

	    if (!feedCode) {
	        alert("피드 코드를 찾을 수 없습니다.");
	        return;
	    }

	    if (confirm("정말 삭제하시겠습니까?")) {
	        $.ajax({
	            url: "/feed/deletefeed",
	            type: "POST",
	            data: { feedCode: feedCode },
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
        if ($(e.target).is('.feed-option-modal-wrap, #optionCencleButton, .feed-modify-modal-overlay')) {
            $('.feed-option-modal-wrap').fadeOut(); // 클릭된 오버레이 닫기
        }
    });
	
	// ESC 키로 모달 닫기
    $(document).on('keydown', function (e) {
        if (e.key === 'Escape') {
            $('.feed-option-modal-wrap, .feed-modify-modal-overlay').fadeOut();
        }
    });	
		
    // 모달 닫기 버튼
    $('#feed-modify-modal-closeBtn').on('click', function () {
        $('#feed-modify-modal-overlay').fadeOut();
    });
});

// --- 피드 댓글 게시 버튼 이벤트 ---
$(document).ready(function () {
    // 댓글 작성 버튼 클릭 시 폼 검증
    $("#addfeedCommentButton").on("click", function (e) {
        const commentContent = $("#addFeedCommentContent").val().trim();
        
        if (!commentContent) {
            alert("댓글을 입력해주세요.");
            e.preventDefault(); // 폼 전송 방지
            return false;
        }
        
        $("#AddFeedCommentForm").submit();
    });
});

	
// --- 피드 댓글 모달 ---
$(document).on('click', '.commentBtn', function () {
	console.log($('#image-preview')[0]);
	const feedCode = $(this).data('feed-code');
	let memberProfilePath = $(this).data('mbr-file-idx');
	let feedFilePath = $(this).data('meal-picture');
	
	/*console.log("feedCode:", feedCode);*/
    console.log("memberProfilePath:", memberProfilePath);
	console.log("feedFilePath BEFORE:", feedFilePath);
	
	if (!feedCode) {
        alert("피드 코드가 없습니다.");
        return;
    }
	
	const baseUrl = window.location.origin;
	
	if (!feedFilePath.startsWith("http")) {
        feedFilePath = baseUrl + feedFilePath;
    }
		
	//console.log("feedFilePath AFTER:", feedFilePath);
	
	$('#comment-image-preview').attr('src', feedFilePath);
	
	$.ajax({
        url: '/feed/feedcomment',
        type: 'GET',
        data: { feedCode: feedCode },
        success: function (response) {
            /*console.log("댓글 데이터 로드 성공:", response);*/

			console.log({response});
            let imagePath = memberProfilePath;
            $('#image-preview').attr('src', imagePath);
			// console.log($('#image-preview').attr('src'));
            let comments = response;

            if (!response || response.length === 0) {
                /*console.log("댓글이 없습니다. 빈 댓글 리스트를 표시합니다.");*/
                $('.feed-user-comment-container').html('<p>댓글이 없습니다. 첫 댓글을 작성해보세요!</p>');
            } else {
                let commentHtml = '';
                response.forEach(comment => {
                    const isAuthor = comment.loggedInMemberId === comment.feedCommentAuthor; // 작성자 여부 확인
                    const profileImage = comment.memberFilePath || '/path/to/default/profile-image.png';
                    const content = comment.feedCommentContent || '내용 없음';

					console.log("Profile Image URL:", comment.memberFilePath); // ✅ URL 출력
				    console.log("Feed Image URL:", comment.feedFilePath); // ✅ URL 출력
					
                    commentHtml += `
                    <section data-comment-id="${comment.feedCommentCode}">
                        <div class="feed-comment-user-block">
                            <div class="feed-comment-content-block">
                                <img class="comment-profile" src="${profileImage}" alt="프로필">
                                <a href="#" class="feed-comment-user-link">${comment.memberId}</a>
                                <div class="feed-comment-feed-comment">
                                    <span>${content}</span>
									<div class="feed-mofify-comment-feed-comment" style="display: none;">
					                    <input type="text" class="comment-edit-input" value="test">
					                </div>
                                </div>
                            </div>
                            <div class="comment-actions" style="display: ${isAuthor ? 'block' : 'none'};">
                                <button type="button" class="edit-btn" data-comment-id="${comment.feedCommentCode}" data-content="${content}">수정</button>
                                <button type="button" class="delete-btn" data-comment-id="${comment.feedCommentCode}">삭제</button>
                            </div>
                        </div>
                    </section>`;
                });

                $('.feed-user-comment-container').html(commentHtml);
            }
            // 댓글 모달 열기
            $('#feedCommentModalOverlay').fadeIn(300);
        },
		error: function (error) {
            console.error("댓글 데이터 로드 실패:", error);
            alert("댓글 데이터를 가져오는 중 오류가 발생했습니다.");
        }
    });
	
	// 댓글 수정 버튼 클릭 이벤트
	$(document).on('click', '.edit-btn', function () {
		const parentSection = $(this).closest('section'); // 현재 댓글 섹션
	    const commentEditContainer = parentSection.find('.feed-mofify-comment-feed-comment');
	    const commentText = parentSection.find('.comment-text');
	    const originalContent = $(this).data('content');

		// 수정 input에 기존 텍스트 값 설정
	    commentEditContainer.find('.comment-edit-input').val(originalContent);
		
	    // 텍스트 숨기고, 수정 input 표시
		commentText.hide();
	    commentEditContainer.show();

		// 다른 댓글의 수정 상태 초기화
	    $('.feed-mofify-comment-feed-comment').not(commentEditContainer).hide();
	    $('.comment-text').not(commentText).show();
	    $('.save-btn').text('수정').addClass('edit-btn').removeClass('save-btn');
	    $('.cancel-btn').text('삭제').addClass('delete-btn').removeClass('cancel-btn');

	    // 버튼 상태 변경 (수정 -> 저장, 삭제 -> 취소)
	    $(this).text('저장').addClass('save-btn').removeClass('edit-btn');
	    parentSection.find('.delete-btn').text('취소').addClass('cancel-btn').removeClass('delete-btn');
	});
	
	// 댓글 저장 버튼 클릭 이벤트
    $(document).on('click', '.save-btn', function () {
		const parentSection = $(this).closest('section');
	    const commentEditContainer = parentSection.find('.feed-mofify-comment-feed-comment');
	    const commentTextSpan = parentSection.find('.feed-comment-feed-comment > span'); // 수정 대상
	    const commentId = $(this).data('comment-id');
	    const newContent = commentEditContainer.find('.comment-edit-input').val();
		
        if (!newContent.trim()) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }
		
		$.ajax({
	        url: '/feed/modifyfeedcomment',
	        type: 'POST',
	        data: { feedCommentCode: commentId, feedCommentContent: newContent },
	        success: function () {
	           /*alert("댓글이 수정되었습니다.");*/
		       
	            commentTextSpan.text(newContent); // 수정된 텍스트를 표시
	            commentEditContainer.hide(); // 수정 input 숨기기

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
	    const parentDiv = $(this).closest('.feed-comment-user-block');
	    const commentEditContainer = parentDiv.find('.feed-mofify-comment-feed-comment');
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
        const feedCommentCode = $(this).data('comment-id');
        if (confirm('댓글을 삭제하시겠습니까?')) {
            $.ajax({
                url: '/feed/deletefeedcomment',
                type: 'POST',
                data: { feedCommentCode: feedCommentCode },
                success: function () {
                    alert("댓글이 삭제되었습니다.");
                    $(`button[data-comment-id='${feedCommentCode}']`).closest('section').remove();
                },
                error: function (error) {
                    console.error("댓글 삭제 실패:", error);
                    alert("댓글 삭제 중 오류가 발생했습니다.");
                }
            });
        }
    });
	
	// 댓글 등록 버튼 클릭 이벤트
    $(document).on('click', '#feedCommentModalButton', function () {
        const feedCode = $('.commentBtn').data('feed-code');
        const commentContent = $('input[placeholder="댓글 달기..."]').val();

        if (!commentContent.trim()) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        $.ajax({
            url: '/feed/createfeedcomment',
            type: 'POST',
            data: {
                feedCode: feedCode,
                feedCommentContent: commentContent
            },
            success: function () {
                /*alert("댓글이 등록되었습니다.");*/
                $('input[placeholder="댓글 달기..."]').val('');
                $('#feedCommentModalOverlay').fadeOut(300);

                // 댓글 목록 새로고침
                location.reload();
            },
            error: function (error) {
                console.error("댓글 등록 실패:", error);
                alert("댓글 등록 중 오류가 발생했습니다.");
            }
        });
    });
	
	// 모달 닫기 버튼 클릭 이벤트
	$(document).on('click', '.feed-comment-modalCloseBtn', function () {
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
});

// 오른쪽 사이드바 업데이트
function updateRightSidebar(feed) {
	if (!feed) {
	    /*console.error('feed is not defined');*/
	    return;
	}
		
    const mealPicture = feed.getAttribute('data-meal-picture') || '/default-food.png';
	const mealWeight = feed.getAttribute('data-meal-weight') || '0';
	const mealCalories = feed.getAttribute('data-meal-calories') || '0';
    const mealCarbohydrates = feed.getAttribute('data-meal-carbohydrates') || '0';
    const mealProtein = feed.getAttribute('data-meal-protein') || '0';
    const mealFat = feed.getAttribute('data-meal-fat') || '0';

	document.querySelector('#analysis-img img').src = mealPicture;
	
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


// 영양 정보 페이지
/*document.addEventListener("DOMContentLoaded", () => {
    const calendarBody = document.getElementById("calendar-body");
    const currentMonthElement = document.querySelector(".current-month");
    const prevMonthButton = document.querySelector(".prev-month");
    const nextMonthButton = document.querySelector(".next-month");

    let currentDate = new Date();

    function renderCalendar(date) {
        const year = date.getFullYear();
        const month = date.getMonth();
        const today = new Date();

        currentMonthElement.textContent = date.toLocaleDateString("ko-KR", {
            month: "long",
            year: "numeric",
        });

        calendarBody.innerHTML = "";

        const firstDay = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();

        let row = document.createElement("tr");
        for (let i = 0; i < firstDay; i++) {
            row.appendChild(document.createElement("td"));
        }

        for (let day = 1; day <= daysInMonth; day++) {
            const cell = document.createElement("td");
            cell.textContent = day;

            if (
                day === today.getDate() &&
                month === today.getMonth() &&
                year === today.getFullYear()
            ) {
                cell.classList.add("today");
            }

            cell.addEventListener("click", async () => {
                const selectedDate = `${year}-${String(month + 1).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
                await fetchNutritionalInfo("userId123", selectedDate); // Replace with actual member ID
            });

            row.appendChild(cell);

            if ((firstDay + day) % 7 === 0) {
                calendarBody.appendChild(row);
                row = document.createElement("tr");
            }
        }

        if (row.children.length > 0) {
            calendarBody.appendChild(row);
        }
    }

    async function fetchNutritionalInfo(mbrId, date) {
        const response = await fetch(`/nutrition/info?mbrId=${mbrId}&date=${date}`);
        const data = await response.json();

        document.getElementById("energy").textContent = data.dniiKcal || 0;
        document.getElementById("carb").textContent = data.dniiCarbo || 0;
        document.getElementById("protein").textContent = data.dniiProtein || 0;
        document.getElementById("fat").textContent = data.dniiFat || 0;

        updateChart(data);
    }

    function updateChart(data) {
        const ctx = document.getElementById("pieChart").getContext("2d");
        new Chart(ctx, {
            type: "doughnut",
            data: {
                labels: ["Carb", "Protein", "Fat"],
                datasets: [
                    {
                        data: [data.dniiCarbo, data.dniiProtein, data.dniiFat],
                        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56"],
                    },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
            },
        });
    }

    prevMonthButton.addEventListener("click", () => {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate);
    });

    nextMonthButton.addEventListener("click", () => {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate);
    });

    renderCalendar(currentDate);
});*/