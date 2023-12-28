
const inputArr = document.querySelectorAll(".new")
const warnMsgArr = document.querySelectorAll(".login-warn")
const $signupBtn = document.querySelector(".signup-btn")
const $signValid = document.querySelector(".sign-valid")

let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

const signUpValidation = () => {

    let count = 0;
    let idCheck = false;
    let pwCheck = false;
    let emailCheck = false;

    for(let i=0; i<inputArr.length; i++) {
        let input = inputArr[i].value;
        if(input == "") {
            warnMsgArr[i].innerHTML = `${inputArr[i].placeholder} 을(를) 입력해 주세요`;
        } else {
            warnMsgArr[i].innerHTML = ``;
            count++;
        }
    }

    if(inputArr[0].value != ""){
        if(inputArr[0].value.length < 6){
            warnMsgArr[0].innerHTML = "아이디는 6자 이상으로 입력해 주세요";
        } else {
            idCheck = true;
        }
    }

    if(inputArr[1].value == inputArr[2].value){
        pwCheck = true;
    } else {
        warnMsgArr[2].innerHTML = "비밀번호가 일치하지 않습니다";
    }

    let email = inputArr[4].value;
    if(email != ""){
        if(regex.test(email)){
            emailCheck = true;
        } else {
          warnMsgArr[4].innerHTML = "이메일 형식이 올바르지 않습니다";
        }
    }

     if(count == 6 && idCheck && pwCheck && emailCheck) {
        document.forms["signup"].submit();
     }
}

$signupBtn.addEventListener("click", signUpValidation)