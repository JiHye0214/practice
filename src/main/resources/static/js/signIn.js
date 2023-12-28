const inputArr = document.querySelectorAll(".login")
const warnMsgArr = document.querySelectorAll(".login-warn")
const $errorMsg = document.querySelector(".errorMsg")
const $loginBtn = document.querySelector(".login-btn")

const loginValidation = () => {

    $errorMsg.style.display = `none`;

    let count = 0;

    for(let i=0; i<inputArr.length; i++) {
        let input = inputArr[i].value;
        if(input == "") {
            $loginBtn.style.margin = `0`;
            warnMsgArr[i].style.marginBottom = `15px`;
            warnMsgArr[i].innerHTML = `${inputArr[i].placeholder} 을(를) 입력해 주세요`;
        } else {
            $loginBtn.style.margin = ``;
            warnMsgArr[i].style.marginBottom = ``;
            warnMsgArr[i].innerHTML = ``;
            count++;
        }
    }

    if(count == 2) {
        document.forms["login"].submit();
    }
}

$loginBtn.addEventListener("click", loginValidation);
inputArr[0].addEventListener("keyup", function (event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        $loginBtn.click();
    }
});
inputArr[1].addEventListener("keyup", function (event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        $loginBtn.click();
    }
});
