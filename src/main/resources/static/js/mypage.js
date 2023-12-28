const $nicknameChangeBtn = document.querySelector(".nickname-change-btn");
const $nicknameChangeCompleteBtn = document.querySelector(".nickname-change-complete");
const $nickname = document.querySelector(".nickname");
const $nicknameInput = document.querySelector(".nickname-change-input");
const $completeBtn = document.querySelector(".nickname-change-complete");

const $changePwBtn = document.querySelector(".mypage-content-content > .change-password-btn");
const $changePwCompleteBtn = document.querySelector(".change-pw-complete");
const $changePwWrap = document.querySelector(".change-pw-wrap");
const pwInputArr = document.querySelectorAll(".change-pw-content > input");

const $changePicBtn = document.querySelector(".picture-change-btn");
const $resetPicBtn = document.querySelector(".picture-reset-btn");
const $changePic = document.querySelector(".profile-pic");
const $changePicForm = document.querySelector(".picture-change-wrap > form");

const $dropUser = document.querySelector(".drop-user");

const $body = document.querySelector("body");

const clickNickNameChangeBtn = () => {
    $nickname.style.display = `none`;
    $nicknameChangeBtn.style.display = `none`;
    $completeBtn.style.display = `block`;
    $nicknameInput.style.display = `block`;
    $nicknameInput.placeholder = $nickname.innerText;
};

const clickNickNameChangeCompleteBtn = () => {
    if($nicknameInput.value == ""){
       $nickname.style.display = `block`;
       $nicknameChangeBtn.style.display = `block`;
       $completeBtn.style.display = `none`;
       $nicknameInput.style.display = `none`;
       $nicknameInput.placeholder = $nickname.innerText;
    }else {
        document.forms["nickChange"].submit();
    }
};

const clickPasswordChangeBtn = () => {
    $changePwBtn.style.display = `none`;
    $changePwWrap.style.display = `block`;
};

const clickPasswordChangeCompleteBtn = () => {

    let count = 0;
    let pwCheck = false;

    for(let i=0; i<pwInputArr.length; i++){
        let input = pwInputArr[i].value;
        if(input == ""){
            alert(`${pwInputArr[i].placeholder} 을(를) 입력해 주세요`);
            return;
        } else {
            count++
        }
    }

    if(pwInputArr[0].value == pwInputArr[1].value){
        pwCheck = true;
    } else {
        alert("비밀번호가 일치하지 않습니다");
    }

    if(count == 2 && pwCheck == true) {
        document.forms["pwChange"].submit();
    }

};

const clickChangePicBtn = () => {
    if($changePic.value == ""){
        alert("이미지 파일을 선택해 주세요")
    } else {
        $changePicForm.submit();
    }
};

const clickResetPicBtn = () => {
    $changePicForm.submit();
}

const clickDropUserBtn = () => {
    const realDrop = confirm("정말 탈퇴하시겠습니까?");
    if (realDrop) {
        $modal.style.visibility = `visible`;
        $body.style.overflow = "hidden";
    }
};


$nicknameChangeBtn.addEventListener("click", clickNickNameChangeBtn);
$nicknameChangeCompleteBtn.addEventListener("click", clickNickNameChangeCompleteBtn);

$changePwBtn.addEventListener("click", clickPasswordChangeBtn);
$changePwCompleteBtn.addEventListener("click", clickPasswordChangeCompleteBtn);

$changePicBtn.addEventListener("click", clickChangePicBtn);
$resetPicBtn.addEventListener("click", clickResetPicBtn);

$dropUser.addEventListener("click", clickDropUserBtn);
