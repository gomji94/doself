document.addEventListener("DOMContentLoaded", () => {
    const progressForeground = document.querySelector(".progress-foreground");
    const progressText = document.querySelector(".progress-text");

    let progress = 0; // 초기 달성률

    function updateProgress(targetProgress) {
        if (progress < targetProgress) {
            progress++;
            progressForeground.style.width = `${progress}%`; // 달성률에 따라 넓이 조정
            progressText.textContent = `${progress}%`;
            requestAnimationFrame(() => updateProgress(targetProgress));
        }
    }

    // 목표 달성률 설정 (예: 83%)
    const targetProgress = 83;
    updateProgress(targetProgress);
});