const raiseModalClose = document.querySelector(".modal-close-btn");
const raiseModalWrap = document.querySelector(".raise-alert-modal");

const clickModalClose = () => {
    raiseModalWrap.style.display = `none`;
}

raiseModalClose.addEventListener("click", clickModalClose);

const searchInput = document.querySelector("#search-input");
const searchInputVal = document.querySelector("#search-input-val");
const initialBtn = document.querySelector("#initial-btn");

searchInputVal.value = searchInput.value;
searchInput.oninput = (e) => {
    searchInputVal.value = e.target.value;
}

initialBtn.onclick = () => {
    searchInputVal.value = "";

    $("[name='orderFrm']").attr({
        "method": "POST",
        "action": "orderWay"
    }).submit();
}

$("[name='postOrderWay']").change(() => {
    $("[name='orderFrm']").attr({
        "method": "POST",
        "action": "orderWay"
    }).submit();
});

