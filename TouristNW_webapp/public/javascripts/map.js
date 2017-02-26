function myMap() {
    navigator.geolocation.getCurrentPosition(onSuccess, onError, { timeout: 30000 });
}

function onSuccess(position) {
    var lat=position.coords.latitude;
    var lang=position.coords.longitude;

//Google Maps
//     var myLatlng = new google.maps.LatLng(lat,lang);
    var myLatlng = new google.maps.LatLng(49.2067, -122.9109);
    var mapOptions = {zoom: 16,center: myLatlng}
    var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
    window.map = map;

    var pinColor = "32CD32";
    var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
        new google.maps.Size(21, 34),
        new google.maps.Point(0,0),
        new google.maps.Point(10, 34));

    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map  ,
        icon: pinImage
    });
    window.currentLocationMarker = marker;
}
function onError(error) {
    alert('code: ' + error.code + '\n' +
        'message: ' + error.message + '\n');
}
function toRad(number){
    return number * (Math.PI / 180);
}
function calculator(lat1,lng1,lat2,lng2,radius){
    var R = 6371;
    var dlat = toRad((lat2-lat1));
    var dlng = toRad((lng2-lng1));
    var lat1r = toRad(lat1);
    var lat2r = toRad(lat2);

    var a = Math.sin(dlat/2) * Math.sin(dlat/2) +
        Math.sin(dlng/2) * Math.sin(dlng/2) * Math.cos(lat1r) * Math.cos(lat2r);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c;
    return d < radius;
}

function search() {
    var radius = $("#radius").val();
    var type = $("#destinationType").val();

    if (radius == null) {
        radius = 1;
    }
    navigator.geolocation.getCurrentPosition(function(position) {
        var lat=position.coords.latitude;
        var long=position.coords.longitude;
        $.post("/Search", {
            radius: radius,
            type: type,
            currentLat: lat,
            currentLng: long})
            .done(function(data) {
                if (window.markers != null) {
                    for (var j = 0; j < window.markers.length; j++) {
                        window.markers[j].setMap(null);
                    }
                }
                window.markers = [];
                for (var i = 0; i < data.response.length; i++) {
                    var facility = data.response[i];
                    var marker = new google.maps.Marker({
                        position: new google.maps.LatLng(facility.Y, facility.X),
                        map: window.map
                    });

                    if (facility.content) {
                        marker.content = facility.content;
                    }
                    var infoWindow = new google.maps.InfoWindow({
                        content: marker.content
                    });

                    google.maps.event.addListener(marker, 'click', function() {
                        infoWindow.setContent(this.content);
                        infoWindow.open(map, this);
                    });
                    window.markers.push(marker);
                }
            });
    }, function(error) {}, { timeout: 30000 });





}