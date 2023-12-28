const $togetherTitle = document.querySelector(".together");
const $protectTitle = document.querySelector(".protect");
const $raiseTitle = document.querySelector(".raise");

const titleArr = document.querySelectorAll(".main-menu");
const detailArr = document.querySelectorAll(".main-detail");

let findIndex = 0;

const addClassTogetherDetail = () => {
    $togetherTitle.classList.add("togetherColor");
    detailArr[0].classList.add("slideDetailDown");
    findIndex = 0;
};
const addClassProtectDetail = () => {
    $protectTitle.classList.add("protectColor");
    detailArr[1].classList.add("slideDetailDown");
    findIndex = 1;
};
const addClassRaiseDetail = () => {
    $raiseTitle.classList.add("raiseColor");
    detailArr[2].classList.add("slideDetailDown");
    findIndex = 2;
};

const mouseOut = () => {
    titleArr[findIndex].className = "main-menu";
    detailArr[findIndex].classList.remove("slideDetailDown");
    detailArr[findIndex].classList.add("slideDetailUp");
};

$togetherTitle.addEventListener("mouseover", addClassTogetherDetail);
$protectTitle.addEventListener("mouseover", addClassProtectDetail);
$raiseTitle.addEventListener("mouseover", addClassRaiseDetail);

$togetherTitle.addEventListener("mouseout", mouseOut);
$protectTitle.addEventListener("mouseout", mouseOut);
$raiseTitle.addEventListener("mouseout", mouseOut);

const graphWrap = document.querySelectorAll(".table");

for(let i=0; i<graphWrap.length; i++){
    let count = graphWrap[i].childElementCount;
    for(let j=1; j<count; j++){
        graphWrap[i].children[j].children[0].innerText = j;
    }
}

const valueArr1 = document.querySelectorAll("#value1");
const valueArr2 = document.querySelectorAll("#value2");
const arrWrap = [valueArr1, valueArr2];

let num = 1;
for( valueArr of arrWrap ){

    const resultArr = [];
    const regionArr = [];

    const guCount = [];
    for(let i=0; i<25; i++){
        guCount[i] = 0;
    }

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

    for(let i=0; i<25; i++){
        let data = {};
        data.region = guNames[i];
        data.count = guCount[i];

        resultArr.push(data);
    }

    for(let i=0; i<valueArr.length; i++) {
        regionArr[i] = valueArr[i].value.split(" ");

        for(let j=0; j<25; j++){
            if(regionArr[i][1] == resultArr[j].region){
                resultArr[j].count++;
            }
        }
    }

    let result = resultArr.sort(function (b, a) {
        return a.count - b.count;
    });

    result = result.slice(0, 5);

    let graphText = null;
    let graphBar = null;

    if(num == 1) {
        graphText = document.querySelector(".graphText1");
        graphBar = document.querySelector(".graphBar1");
    }
    if(num == 2) {
        graphText = document.querySelector(".graphText2");
        graphBar = document.querySelector(".graphBar2");
    }

    for(let i=0; i<5; i++){
        let text = result[i].region;
        graphText.innerHTML += `<div class="gu">${text}</div>`;
    }

    for(let i=0; i<5; i++){
        let count = result[i].count;
        if(num == 1){
            graphBar.innerHTML += `<div class="count1">${count}</div>`;
        } else {
            graphBar.innerHTML += `<div class="count2">${count}</div>`;
        }
    }

    let graphBarArr = null;
    if(num == 1){
        graphBarArr = document.querySelectorAll(".count1");
    } else {
        graphBarArr = document.querySelectorAll(".count2");
    }

    for(let i=0; i<5; i++){
        let count = result[i].count * 3.5;
        graphBarArr[i].style.height = `${count}%`;
    }

    num++;
}






