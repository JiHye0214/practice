const $protectTell = document.querySelectorAll(".protect-tell");

for(e of $protectTell){
    const tellSplitHyphen = e.innerText.split("-");
    const tellSplitToken = e.innerText.split("");

    // 앞에 점
    if(tellSplitToken[0] == "."){
        tellSplitToken.shift();
        let joinTell = tellSplitToken.join("");
        e.innerText = joinTell;
    }

    // 하이픈
    if(tellSplitHyphen.length == 1){
        if(tellSplitToken.length == 9){ // 일단 9자만
            tellSplitToken.splice(2,0,"-");
            tellSplitToken.splice(6,0,"-");
            let joinTell = tellSplitToken.join("");
            e.innerText = joinTell;
        }
    }
}
// -------------------------------------------
const addr = document.querySelector("#CRADDR, #ADDR");
console.log("주소 : ", addr.value);

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder(addr);

// 주소로 좌표를 검색합니다
geocoder.addressSearch(addr.value, function(result, status) {

    // 정상적으로 검색이 완료됐으면
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);


        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });


        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    }
});




//----------------------------------