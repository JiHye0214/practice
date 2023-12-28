const detailText = document.querySelectorAll("#txt-list-content > p");

for(e of detailText){
    if(e.innerText == ""){
        e.innerText = "-";
    }
}

const heartBtn = document.querySelector("#heart");
const zzimForm = document.querySelector("#zzimBtn");
const isZzimClicked = document.querySelector("#isZzimClicked");
const logged = document.querySelector("#logged");

if (isZzimClicked.value == "true") {
    heartBtn.style.fill = "rgb(255, 80, 80)";
} else {
    heartBtn.style.fill = "#fff";
}

heartBtn.addEventListener("click", () => {
    console.log(logged.value);

    if (logged.value) {
        zzimForm.submit();
    } else {
        location.href = "/user/logIn";
    }
});

const lot = document.querySelector("#LOT");
const lat = document.querySelector("#LAT");

var container = document.getElementById('map-wrap'),
		options = {
			center: new kakao.maps.LatLng(lat.value, lot.value),
			level: 3
		};

var map = new kakao.maps.Map(container, options);
var imageSrc = 'https://ifh.cc/g/oxMhdm.png',
    imageSize = new kakao.maps.Size(53, 55),
    imageOption = {offset: new kakao.maps.Point(27, 69)};

var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
    markerPosition = new kakao.maps.LatLng(lat.value, lot.value);

var marker = new kakao.maps.Marker({
    position: markerPosition,
    image: markerImage
});

marker.setMap(map);







