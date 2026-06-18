document.addEventListener("DOMContentLoaded", () => {

    // ── Marcar nav-item activo según URL ──────────────────────────────
    const path = window.location.pathname;
    document.querySelectorAll(".nav-item").forEach(link => {
        const href = link.getAttribute("href");
        if (href && path.startsWith(href)) {
            link.classList.add("activo");
        }
    });

    // ── Animación de entrada del sidebar ─────────────────────────────
    const sidebar = document.querySelector(".sidebar");
    sidebar.style.opacity = "0";
    sidebar.style.transform = "translateX(-12px)";
    setTimeout(() => {
        sidebar.style.transition = "opacity 0.4s ease, transform 0.4s ease";
        sidebar.style.opacity = "1";
        sidebar.style.transform = "translateX(0)";
    }, 50);

    // ── Animación de entrada del contenido ───────────────────────────
    const main = document.querySelector(".main-content");
    main.style.opacity = "0";
    main.style.transform = "translateY(16px)";
    setTimeout(() => {
        main.style.transition = "opacity 0.4s ease, transform 0.4s ease";
        main.style.opacity = "1";
        main.style.transform = "translateY(0)";
    }, 150);
});