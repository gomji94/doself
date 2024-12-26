document.addEventListener('DOMContentLoaded', () => {
    // 1. 파일 업로드 및 미리보기
    const uploadBtn = document.getElementById('upload-btn');
    const fileInput = document.getElementById('file-input');
    const imagePreview = document.getElementById('image-preview');
    const previewContainer = document.getElementById('preview-container');
    const defaultImage = "https://velog.velcdn.com/images/mekite/post/a7d15153-7ad6-4c26-b1c0-20c711e47f07/image.png";

    // 기본 이미지 설정
    if (imagePreview) {
        imagePreview.src = defaultImage;
        previewContainer.style.display = 'none'; // 기본적으로 미리보기 숨김
    }

    // 업로드 버튼 클릭 이벤트
    if (uploadBtn && fileInput) {
        uploadBtn.addEventListener('click', () => {
            fileInput.click(); // 파일 선택 창 열기
        });

        // 파일 선택 시 이벤트
        fileInput.addEventListener('change', (event) => {
            const file = event.target.files[0]; // 선택된 파일
            if (!file) {
                console.warn('파일이 선택되지 않았습니다. 기본 이미지를 유지합니다.');
                imagePreview.src = defaultImage;
                previewContainer.style.display = 'none';
                return;
            }

            const reader = new FileReader();
            reader.onload = (e) => {
                imagePreview.src = e.target.result; // 업로드된 이미지를 미리보기로 설정
                previewContainer.style.display = 'block'; // 미리보기 컨테이너 표시
                console.log('미리보기 이미지 설정 완료:', e.target.result);
            };

            reader.onerror = () => {
                console.error('이미지 파일을 읽는 중 오류가 발생했습니다.');
                alert('이미지 파일을 읽을 수 없습니다. 다시 시도해주세요.');
            };

            reader.readAsDataURL(file); // 파일을 Data URL로 읽기
        });
    } else {
        console.error('uploadBtn 또는 fileInput 요소를 찾을 수 없습니다.');
    }

    // 2. 텍스트 카운터 업데이트
    const $inputText = document.getElementById('content'); // 텍스트 박스
    const $textCount = document.getElementById('text-count'); // 글자 수 카운터
    const maxLength = 2000; // 최대 글자 수

    if ($inputText && $textCount) {
        // 초기 텍스트 설정 및 카운터 업데이트
        const initialText = '핫케이크에 과일까지 좋았는데 시럽 넘 많이 뿌림... ㅋㅋ';
        $inputText.value = initialText;
        $textCount.textContent = initialText.length;

        // 텍스트 입력 이벤트
        $inputText.addEventListener('input', () => {
            const currentLength = $inputText.value.length;
            $textCount.textContent = currentLength;

            // 글자 수 초과 시 색상 변경
            if (currentLength > maxLength) {
                $textCount.style.color = 'red'; // 초과 시 빨간색 표시
            } else {
                $textCount.style.color = ''; // 기본 색상 복원
            }
        });
    } else {
        console.error('텍스트 입력 또는 카운터 관련 요소를 찾을 수 없습니다.');
    }
});
