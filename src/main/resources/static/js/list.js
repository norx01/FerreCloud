document.addEventListener("DOMContentLoaded", () => {

    // ── Animación de entrada ──────────────────────────────────────────
    const container = document.querySelector(".container");
    container.style.opacity = "0";
    container.style.transform = "translateY(20px)";
    setTimeout(() => {
        container.style.transition = "opacity 0.4s ease, transform 0.4s ease";
        container.style.opacity = "1";
        container.style.transform = "translateY(0)";
    }, 80);

    // ── Animación de filas ────────────────────────────────────────────
    document.querySelectorAll("tbody tr").forEach((fila, i) => {
        fila.style.opacity = "0";
        fila.style.transform = "translateY(10px)";
        setTimeout(() => {
            fila.style.transition = "opacity 0.3s ease, transform 0.3s ease";
            fila.style.opacity = "1";
            fila.style.transform = "translateY(0)";
        }, 150 + i * 60);
    });

    // ── Búsqueda en tiempo real ───────────────────────────────────────
    const buscador = document.getElementById("buscador");
    if (buscador) {
        buscador.addEventListener("input", () => {
            const texto = buscador.value.toLowerCase().trim();
            const filas = document.querySelectorAll("tbody tr:not(.empty-row)");
            let visibles = 0;

            filas.forEach(fila => {
                const coincide = fila.textContent.toLowerCase().includes(texto);
                fila.style.display = coincide ? "" : "none";
                if (coincide) visibles++;
            });

            const emptyRow = document.querySelector(".empty-row");
            if (emptyRow) emptyRow.style.display = visibles === 0 ? "" : "none";
        });
    }

    // ── Confirmación antes de eliminar ───────────────────────────────
    document.querySelectorAll(".btn-eliminar").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const fila = btn.closest("tr");
            const nombre = fila.querySelector("td")?.textContent.trim() || "este cliente";
            const confirmar = confirm(`¿Seguro que deseas eliminar a ${nombre}?`);
            if (!confirmar) e.preventDefault();
        });
    });
});