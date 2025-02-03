// 챌린지 경고 피드/댓글 조회
$(document).ready(function () {
    $('#locationSelect').on('change', function () {
        const type = $(this).val();
        const memberId = $('#challengeMember').val();
        const challengeCode = $('#challengeCode').val();

        if (type && memberId) {
            $.ajax({
                url: '/challenge/feed/fetchContent',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ challengeCode, memberId, type }),
                success: function (data) {
                    const contentSelect = $('#contentSelect');
                    contentSelect.empty(); // 기존 옵션 제거
                    contentSelect.append('<option value="">참여자의 피드/댓글 선택해주세요</option>');

                    if (data && data.length > 0) {
                        data.forEach(item => {
                            contentSelect.append(`<option value="${item.id}">${item.content}</option>`);
                        });
                    } else {
                        contentSelect.append('<option value="">데이터가 없습니다.</option>');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error fetching content:', error);
                    alert('데이터를 가져오는 중 문제가 발생했습니다.');
                }
            });
        }
    });
});


// 챌린지 멤버 경고 폼
$(document).ready(function () {
    // 제출 버튼 클릭 이벤트
    $('#submitWarning').on('click', function () {
        const challengeCode = $('#challengeCode').val();
        const memberId = $('#challengeMember').val();
        const warningCategoryCode = $('#warningCategorySelect').val();
        const occuranceLocationCode = $('#locationSelect').val();
        const contentCode = $('#contentSelect').val();

        // 서버로 보낼 데이터 구성
        const warningData = {
            challengeCode: challengeCode,
            challengeMemberCode: memberId,
            challengeWarningCategoryCode: warningCategoryCode,
            occuranceLocationCode: occuranceLocationCode,
            contentCode: contentCode
        };

        // Ajax 요청
        $.ajax({
            url: '/challenge/feed/memberlist/warningrequest',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(warningData),
            success: function (response) {
                if (response) {
                    alert('경고 등록이 성공적으로 처리되었습니다.');
					window.history.back();
                } else {
                    alert('경고 등록에 실패했습니다. 다시 시도해주세요.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error submitting warning:', error);
                alert('서버와 통신 중 문제가 발생했습니다.');
            }
        });
    });
});
