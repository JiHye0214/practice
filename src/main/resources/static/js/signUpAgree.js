const allAgree = document.getElementById("check0");
const agree1 = document.getElementById("check1");
const agree2 = document.getElementById("check2");
const $nextBtn = document.querySelector(".agree-next-btn");
const agreeWarnArr = document.querySelectorAll(".warn-message");
const agreeCheckArr = document.querySelectorAll(".agree-checkbox");

$nextBtn.style.background = `rgb(197,197,197)`;
$nextBtn.style.cursor = `default`;

if (allAgree.checked || (agree1.checked && agree2.checked)) {
    $nextBtn.style.background = ``;
}

const clickAllCheckBox = () => {
    if (allAgree.checked) {
        agree1.checked = true;
        agree2.checked = true;
        $nextBtn.style.background = ``;
        $nextBtn.style.cursor = ``;
    } else {
        agree1.checked = false;
        agree2.checked = false;
        $nextBtn.style.background = `rgb(197,197,197)`;
        $nextBtn.style.cursor = `default`;
    }
};

const clickCheckBox = () => {
    if (agree1.checked && agree2.checked) {
        allAgree.checked = true;
        $nextBtn.style.background = ``;
        $nextBtn.style.cursor = ``;
    } else {
        allAgree.checked = false;
        $nextBtn.style.background = `rgb(197,197,197)`;
        $nextBtn.style.cursor = `default`;
    }
};

allAgree.addEventListener("click", clickAllCheckBox);
agree1.addEventListener("click", clickCheckBox);
agree2.addEventListener("click", clickCheckBox);


const agreeValidation = () => {
    for(let i=1; i<3; i++){
        if(!agreeCheckArr[i].checked){
            agreeWarnArr[i-1].style.display = `block`;
        } else {
            agreeWarnArr[i-1].style.display = `none`;
        }
    }

    if(allAgree.checked){
        location.href = "/user/signUp"
    }
}

$nextBtn.addEventListener("click", agreeValidation);