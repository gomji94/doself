document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".filter-section button");
    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            buttons.forEach((btn) => btn.classList.remove("active"));
            button.classList.add("active");
        });
    });
});