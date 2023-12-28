$("#content").summernote({
    width: 1200,
    height: 350,
    placeholder: "내용을 입력해 주세요",
});
$(".note-placeholder")[0].style.color = "rgb(190, 190, 190)";

const titleInput = document.querySelector("#title");
const writeBtn = document.querySelector("#submit-btn");
const errMsgs = document.querySelectorAll(".err-msg");

const scroll = (topVal) => {
    window.scrollTo({
        top: topVal,
        left: 0,
        behavior: "smooth",
    });
};

writeBtn.addEventListener("click", () => {
    let cnt = 0;

    if ($(".note-editable")[0].textContent == "") {
        errMsgs[1].innerHTML = "내용을 입력해 주세요";
        errMsgs[1].style.display = "block";
        scroll(330);
    } else {
        errMsgs[1].style.display = "none";
        cnt++;
    }

    if (titleInput.value == "") {
        errMsgs[0].innerHTML = "제목을 입력해 주세요";
        errMsgs[0].style.display = "block";
        titleInput.focus();
    } else {
        errMsgs[0].style.display = "none";
        cnt++;
    }

    if (cnt == 2) {
        document.forms["community-write-form"].submit();
    }
});
