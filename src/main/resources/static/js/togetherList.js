const heartsBtns = document.querySelectorAll(".heart");
const zzimForms = document.querySelectorAll(".content-zzimBtn");
const isZzimClicked = document.querySelectorAll(".isZzimClicked");
const logged = document.querySelector("#logged");

heartsBtns.forEach((heart, i) => {
    if (isZzimClicked[i].value == "true") {
        heart.style.fill = "rgb(255, 80, 80)";
    } else {
        heart.style.fill = "#fff";
    }

    heart.addEventListener("click", () => {
        console.log(logged.value);

        if (logged.value) {
            zzimForms[i].name = "zzimFrm_" + i;
            zzimForms[i].submit();
        } else {
            location.href = "/user/logIn";
        }
    });
});

const types = document.querySelectorAll(".type");
const typeValInput = document.querySelector("#type-val-input");
const typeVal = document.querySelector("#type-val");

if (typeVal.textContent == null) {
    types[0].classList.add("active");
}

types.forEach((type) => {
    if (type.textContent == typeVal.textContent) {
        type.classList.add("active");
    }

    type.addEventListener("click", () => {
        if (!type.classList.contains("active")) {
            types.forEach((el) => {
                el.classList.remove("active");
            })
            type.classList.add("active");

            typeValInput.value = type.textContent;

            $("[name='typeFrm']").attr({
                "method": "POST",
                "action": "typeSelect"
            }).submit();
        }
    })
})
