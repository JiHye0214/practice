const openDivArr = document.querySelectorAll(".find-content-title");
const hiddenMenuArr = document.querySelectorAll(".find-content-hidden");
const openBtnArr = document.querySelectorAll(".menu-open");
const closeBtnArr = document.querySelectorAll(".menu-close");
const writeWrap = document.querySelector(".findWrap");

const stateArr = [false, false, false];

if(writeWrap != null){
    const clickHiddenMenuBtn = (index) => {
        if (!stateArr[index]) {
            openBtnArr[index].style.display = `none`;
            closeBtnArr[index].style.display = `block`;
            hiddenMenuArr[index].style.display = `block`;
            stateArr[index] = true;
        } else {
            openBtnArr[index].style.display = `block`;
            closeBtnArr[index].style.display = `none`;
            hiddenMenuArr[index].style.display = `none`;
            stateArr[index] = false;
        }
    };

    for (let i = 0; i < 3; i++) {
        openDivArr[i].onclick = function () {
            clickHiddenMenuBtn(i);
        };
    }
}

const findBtnArr = document.querySelectorAll(".find-btn");
const findFromArr = document.querySelectorAll(".find-input-wrap");
const errMsgArr = document.querySelectorAll(".findWrap .login-warn");
const FindInputArr = document.querySelectorAll(".find-input");

window.onload = function() {
    for(e of FindInputArr){
        e.value = "";
    }
}

if(writeWrap != null){
    const clickFindBtn = (index) => {

        const firstChild = findFromArr[index].children[0];
        const secondChild = findFromArr[index].children[1];

        if(firstChild.value == ""){
            errMsgArr[index].innerHTML = `${firstChild.placeholder} 을(를) 입력해 주세요`;
            errMsgArr[index].style.marginTop = `15px`;
        } else if(secondChild.value == "") {
            errMsgArr[index].innerHTML = `${secondChild.placeholder} 을(를) 입력해 주세요`;
            errMsgArr[index].style.marginTop = `15px`;
        } else {
            errMsgArr[index].innerHTML = ``;
            errMsgArr[index].style.marginTop = `0px`;
            findFromArr[index].submit();

            firstChild.value = "";
            secondChild.value = "";
        }
    }
    for(let i=0; i<3; i++){
        findBtnArr[i].onclick = function() {
            clickFindBtn(i);
        }
    }
}

const changeWrap = document.querySelector("#changePw");
const errMsgArr2 = document.querySelectorAll(".giveIdWrap .login-warn");
const $complete = document.querySelector(".change-pw-complete");
const inputArr = document.querySelectorAll(".change-pw-content > input");
const changeForm = document.querySelector(".change-pw-wrap");

if(changeWrap != null){
    console.log(changeWrap)
    const changePwValid = () => {

        let count = 0;
        let pwCheck = false;

        for(let i=0; i<inputArr.length; i++) {
            let input = inputArr[i].value;
            if(input == "") {
                errMsgArr2[i].style.marginBottom = `10px`;
                errMsgArr2[i].innerHTML = `${inputArr[i].placeholder} 을(를) 입력해 주세요`;
            } else {
                errMsgArr2[i].style.marginBottom = `0px`;
                errMsgArr2[i].innerHTML = ``;
                count++;
            }
        }

        if(inputArr[0].value == inputArr[1].value){
            pwCheck = true;
        } else {
            errMsgArr2[1].innerHTML = "비밀번호가 일치하지 않습니다";
        }

        if(count == 2 && pwCheck == true) {
            changeForm.submit();
        }
    }

    $complete.onclick = function() {
        changePwValid();
    }

}