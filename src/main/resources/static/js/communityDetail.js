const $commentInput = document.querySelector(".raise-comment-content > .write-comment-input");
const $commentWriteBtn = document.querySelector(".raise-comment-content > .talk-btn");

const $writeForm = document.querySelector(".raise-comment-content");
const cmtDelBtnArr = document.querySelectorAll(".comment-delete-btn");
const cmtFormArr = document.querySelectorAll(".raise-comment-content");

const clickOpposite = () => {
    document.forms["opposite"].submit();
}

const clickRecommend = () => {
    document.forms["recommend"].submit();
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