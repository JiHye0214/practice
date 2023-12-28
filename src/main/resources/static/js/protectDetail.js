const $protectTell = document.querySelectorAll(".protect-tell");

for(e of $protectTell){
    const tellSplitHyphen = e.innerText.split("-");
    const tellSplitToken = e.innerText.split("");

    if(tellSplitToken[0] == "."){
        tellSplitToken.shift();
        let joinTell = tellSplitToken.join("");
        e.innerText = joinTell;
    }

    if(tellSplitHyphen.length == 1){
        if(tellSplitToken.length == 9){
            tellSplitToken.splice(2,0,"-");
            tellSplitToken.splice(6,0,"-");
            let joinTell = tellSplitToken.join("");
            e.innerText = joinTell;
        }
    }
}

const addr = document.querySelector("#CRADDR, #ADDR");
console.log("주소 : ", addr.value);

var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };

var map = new kakao.maps.Map(mapContainer, mapOption);

var imageSrc = 'https://ifh.cc/g/qQZkwx.png',
    imageSize = new kakao.maps.Size(53, 55),
    imageOption = {offset: new kakao.maps.Point(27, 69)};

var geocoder = new kakao.maps.services.Geocoder(addr);

geocoder.addressSearch(addr.value, function(result, status) {

     if (status === kakao.maps.services.Status.OK) {

        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
             coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        var marker = new kakao.maps.Marker({
            map: map,
            position: coords,
            image: markerImage
        });
        map.setCenter(coords);
    }
});

