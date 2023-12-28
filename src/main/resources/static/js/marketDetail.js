const $commentWriteBtn = document.querySelector(".raise-comment-content > .market-btn");
const $commentInput = document.querySelector(".raise-comment-content > .write-comment-input");
const $price = document.querySelector(".market-writer-price");
const cmtDelBtnArr = document.querySelectorAll(".comment-delete-btn");
const cmtFormArr = document.querySelectorAll(".raise-comment-content");

if($price.innerText == "0"){
    $price.innerText = "나눔";
}

const clickMarketCommentWriteBtn = () => {
    if($commentInput.value != "") {
        document.forms["writeComment"].submit();
        $commentInput.value = "";
    }
}

$commentWriteBtn.addEventListener("click", clickMarketCommentWriteBtn);

const formWithBtn = [];
let index = 0;

for(let i=0; i<cmtFormArr.length-1; i++){
    if(cmtFormArr[i].childElementCount == 6){
        cmtFormArr[i].children[3].style.width = `730px`;
        formWithBtn[index] = cmtFormArr[i];
        formWithBtn[index].name = index;
        index++;
    }
}

for(let i=0; i<cmtDelBtnArr.length; i++){
    cmtDelBtnArr[i].onclick = function () {
        clickCmtDelBtn(i);
    };
}

const clickCmtDelBtn = (i) => {
    let del = confirm("댓글을 삭제하시겠습니까?");
    if(del){
        formWithBtn[i].submit();
    }
}

const clickDetailDelBtn = () => {
    let del = confirm("글을 삭제하시겠습니까?");
    if(del){
        document.forms["detailDelete"].submit();
    }
}
