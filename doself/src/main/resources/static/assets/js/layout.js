
    document.querySelectorAll('.menu-item').forEach(item => {
        item.addEventListener('click', (e) => {
            const parent = item.parentElement;
            const subMenu = parent.querySelector('.sub-menu');
            const toggleIcon = parent.querySelector('.toggle-icon');

            if (subMenu) {
                const isVisible = subMenu.style.display === 'block';
                subMenu.style.display = isVisible ? 'none' : 'block';
                toggleIcon.textContent = isVisible ? '+' : '-';
            }
        });
    });

    /*
    document.querySelectorAll('.sidebar > ul > li > a').forEach(item => {
        const circle = document.createElement('div');
            circle.style.width = '20px';
            circle.style.height = '20px';
            circle.style.backgroundColor = '#bbb';
            circle.style.borderRadius = '50%';
            circle.style.position = 'absolute';
            circle.style.left = '10px';
            circle.style.top = '50%';
            circle.style.transform = 'translateY(-50%)';
            circle.style.pointerEvents = 'none';

    // 상위 메뉴에만 원형 추가
        item.style.position = 'relative'; // 부모 요소 기준으로 배치
        item.appendChild(circle);
    });
    */

/*     document.addEventListener("DOMContentLoaded", () => {
        const menuItems = document.querySelectorAll(".menu-item");
    
        menuItems.forEach(item => {
            item.addEventListener("click", function (e) {
                e.preventDefault();
    
                // 현재 클릭한 메뉴의 하위 메뉴
                const subMenu = this.nextElementSibling;
    
                // 모든 하위 메뉴를 닫기
                document.querySelectorAll(".sub-menu").forEach(menu => {
                    if (menu !== subMenu) {
                        menu.style.display = "none";
                    }
                });
    
                // 클릭한 메뉴의 하위 메뉴 토글
                if (subMenu) {
                    subMenu.style.display = subMenu.style.display === "block" ? "none" : "block";
                }
            });
        });
    }); 
   
    */
    /* alt shift a */