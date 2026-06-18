document.addEventListener("DOMContentLoaded", () => {

    // ── Animación de entrada ──────────────────────────────────────────
    const card = document.querySelector(".card");
    card.style.opacity = "0";
    card.style.transform = "translateY(24px)";
    setTimeout(() => {
        card.style.transition = "opacity 0.5s ease, transform 0.5s ease";
        card.style.opacity = "1";
        card.style.transform = "translateY(0)";
    }, 80);

    // ── Validación en tiempo real ─────────────────────────────────────
    const inputs = document.querySelectorAll("input:not([type='hidden'])");

    inputs.forEach(input => {
        input.addEventListener("blur", () => validarCampo(input));
        input.addEventListener("input", () => {
            if (input.classList.contains("error")) validarCampo(input);
        });
    });

    function validarCampo(input) {
        const field = input.closest(".field");
        let mensaje = "";

        if (!input.value.trim()) {
            mensaje = "Este campo es obligatorio.";
        } else if (input.type === "email" && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input.value)) {
            mensaje = "Ingresa un correo válido.";
        } else if (input.placeholder.includes("0000") && !/^\d+$/.test(input.value.replace(/\s/g, ""))) {
            mensaje = "Solo se permiten números.";
        }

        let errorEl = field.querySelector(".error-msg");
        if (mensaje) {
            input.classList.add("error");
            input.classList.remove("valido");
            if (!errorEl) {
                errorEl = document.createElement("span");
                errorEl.className = "error-msg";
                field.appendChild(errorEl);
            }
            errorEl.textContent = mensaje;
        } else {
            input.classList.remove("error");
            input.classList.add("valido");
            if (errorEl) errorEl.remove();
        }
    }

    // ── Feedback del botón al enviar ──────────────────────────────────
    const form = document.querySelector("form");
    const btn = document.querySelector(".btn-submit");

    form.addEventListener("submit", (e) => {
        let hayErrores = false;

        inputs.forEach(input => {
            validarCampo(input);
            if (input.classList.contains("error") || !input.value.trim()) {
                hayErrores = true;
            }
        });

        if (hayErrores) {
            e.preventDefault();
            sacudirFormulario();
            return;
        }

        btn.textContent = "Guardando...";
        btn.disabled = true;
        btn.style.opacity = "0.75";
    });

    function sacudirFormulario() {
        card.style.animation = "sacudir 0.4s ease";
        card.addEventListener("animationend", () => {
            card.style.animation = "";
        }, { once: true });
    }

    // ── Tooltips en labels ────────────────────────────────────────────
    const tooltips = {
        "Documento": "Cédula o NIT del cliente",
        "Teléfono": "Celular o fijo con indicativo",
        "Correo Electrónico": "Se usará para notificaciones",
        "Dirección": "Dirección de entrega principal"
    };

    document.querySelectorAll(".field label").forEach(label => {
        const tip = tooltips[label.textContent.trim()];
        if (!tip) return;
        label.style.cursor = "help";
        label.title = tip;
    });
});