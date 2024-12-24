// 이미지 업로드 및 미리보기
const uploadBtn = document.querySelector('.cf-upload-btn');
const imagePreview = document.getElementById('cf-image-preview');

uploadBtn.addEventListener('click', () => {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';
    input.onchange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                imagePreview.src = reader.result;
                imagePreview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    };
    input.click();
});

// 글자수 카운터
const content = document.getElementById('cf-content');
const textCount = document.getElementById('cf-text-count');

content.addEventListener('input', () => {
    textCount.textContent = content.value.length;
});