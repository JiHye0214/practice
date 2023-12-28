const heartsBtns = document.querySelectorAll(".heart");
const heartForm = document.querySelectorAll(".content-zzimBtn");

heartsBtns.forEach((heart, i) => {
    heart.addEventListener("click", () => {
        heart.style.fill = "#fff";
        heartForm[i].name = i;
        heartForm[i].submit();
    });
});
