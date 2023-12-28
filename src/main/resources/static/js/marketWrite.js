const guNames = [
    "강남구",
    "강동구",
    "강북구",
    "강서구",
    "관악구",
    "광진구",
    "구로구",
    "금천구",
    "노원구",
    "도봉구",
    "동대문구",
    "동작구",
    "마포구",
    "서대문구",
    "서초구",
    "성동구",
    "성북구",
    "송파구",
    "양천구",
    "영등포구",
    "용산구",
    "은평구",
    "종로구",
    "중구",
    "중랑구",
];

$("#content").summernote({
    width: 1200,
    height: 350,
    placeholder: "내용을 입력해 주세요",
});
$(".note-placeholder")[0].style.color = "rgb(190, 190, 190)";

const guInput = document.querySelector(".gu-input");
const guSelectBtn = document.querySelector("#gu-select-btn");
const guWrap = document.querySelector("#gu-wrap");

guNames.forEach((guName) => {
    guWrap.innerHTML += `<div class="gu">${guName}</div>`;
});
const guDiv = document.querySelectorAll(".gu");

guDiv.forEach((gu) => {
    gu.addEventListener("click", () => {
        if (gu.classList.contains("clicked")) {
            gu.classList.remove("clicked");
            guSelectBtn.textContent = "지역선택";
        }
        else {
            guDiv.forEach((el) => {
                el.classList.remove("clicked");
            });
            gu.classList.add("clicked");
            guSelectBtn.textContent = gu.textContent;
        }

        guInput.value = guSelectBtn.textContent;
    });
});

if (guSelectBtn.textContent != "지역선택") {
    guSelectBtn.classList.add("clicked");
    guDiv.forEach((gu) => {
        if (gu.textContent == guSelectBtn.textContent) {
            gu.classList.add("clicked");
        }
    })
    guInput.value = guSelectBtn.textContent;
} else {
    guInput.value = guSelectBtn.textContent;
}

guSelectBtn.addEventListener("click", () => {
    if (guWrap.style.display !== "block") {
        $("#gu-wrap").slideDown("fast");
    }
    else {
        $("#gu-wrap").slideUp("fast");
    }

    if (guSelectBtn.classList.contains("clicked") && guSelectBtn.textContent == "지역선택") {
        guSelectBtn.classList.remove("clicked");
    }
    else {
        guSelectBtn.classList.add("clicked");
    }
});

$(document).click(function (e) {
    if (e.target.className == "gu-select-btn") {
        return false;
    } else if (e.target.className == "gu-select-btn clicked") {
        return false;
    } else if (e.target.className == "gu") {
        return false;
    } else if (e.target.className == "gu clicked") {
        return false;
    }

    $("#gu-wrap").slideUp("fast");

    if (guSelectBtn.textContent == "지역선택") {
        guSelectBtn.classList.remove("clicked");
    } else {
        guSelectBtn.classList.add("clicked");
    }
});

const priceInput = document.querySelector("#price");
const productNameInput = document.querySelector("#productName");
const writeBtn = document.querySelector("#submit-btn");
const errMsgs = document.querySelectorAll(".err-msg");
const writeForm = document.querySelector(".talk-wrapper > form");

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
        errMsgs[3].innerHTML = "내용을 입력해 주세요";
        errMsgs[3].style.display = "block";
        scroll(320);
    } else {
        errMsgs[3].style.display = "none";
        cnt++;
    }

    if (guSelectBtn.textContent == undefined || guSelectBtn.textContent == "지역선택") {
        errMsgs[2].innerHTML = "지역을 선택해 주세요";
        errMsgs[2].style.display = "block";
        scroll(200);
    } else {
        errMsgs[2].style.display = "none";
        cnt++;
    }

    if (productNameInput.value == "") {
        errMsgs[1].innerHTML = "상품명을 입력해 주세요";
        errMsgs[1].style.display = "block";
        productNameInput.focus();
    } else {
        errMsgs[1].style.display = "none";
        cnt++;
    }

    if (priceInput.value == "") {
        errMsgs[0].innerHTML = "가격을 입력해 주세요";
        errMsgs[0].style.display = "block";
        priceInput.focus();
    } else if (priceInput.value.trim() == "나눔") {
        errMsgs[0].innerHTML = "나눔은 0을 입력해 주세요";
        errMsgs[0].style.display = "block";
        priceInput.focus();
    } else if (Number(priceInput.value).toString() == "NaN") {
        errMsgs[0].innerHTML = "숫자로 입력해 주세요";
        errMsgs[0].style.display = "block";
        priceInput.focus();
    } else {
        errMsgs[0].style.display = "none";
        cnt++;
    }

    if (cnt == 4) {
        document.forms["market-write-form"].submit();
    }
});

const productPic = document.querySelector(".product-pic");
const confirm = document.querySelector(".confirmBox");

productPic.onchange = function() {

    const splitPic = productPic.value.split("");
    const joinPic = splitPic.slice(12).join("");
    confirm.innerText = joinPic;
};
