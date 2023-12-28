const $dropUserClose = document.querySelector(".dropUserCloseBtn");
const $modal = document.querySelector(".modal");
const $dropBtn = document.querySelector(".dropUserOk");
const $dropInput = document.querySelector(".drop-input");
const $warnMsg = document.querySelector(".login-warn");

const clickDropUserCloseBtn = () => {
    $modal.style.visibility = `hidden`;
    $body.style.overflow = "unset";
};

const clickDropUserBtn5656 = () => {
    if($dropInput.value == ""){
        console.log("비었다!")
        $warnMsg.innerHTML = "비밀번호를 입력해 주세요";
    } else {
        document.forms["drop"].submit();
    }
}

$dropUserClose.addEventListener("click", clickDropUserCloseBtn);
$dropBtn.addEventListener("click", clickDropUserBtn5656);
