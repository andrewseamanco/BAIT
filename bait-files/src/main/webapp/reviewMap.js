function initialize() {
  geocoder = new google.maps.Geocoder();
  const latlng = new google.maps.LatLng(53.3496, -6.3263);
  const mapOptions = {
    zoom: 8,
    center: latlng
  }
  map = new google.maps.Map(document.getElementById('address-map-view'), mapOptions);
}

function codeAddress(address) {
  geocoder.geocode({
    address: address
  }, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      map.setCenter(results[0].geometry.location); //center the map over the result
      //place a marker at the location
      var marker = new google.maps.Marker({
        map: map,
        position: results[0].geometry.location
      });
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}