function createMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 39.577216, lng: -104.987153150}, zoom: 8});

  var data = {
    title: "Content"
  }

  let birth = $("#birth-content").html();
  let childhoodHome = $("#elementary-school-content").html();
  let elementarySchool = $("#elementary-school-content").html();
  let taekwondo = $("#taekwondo-content").html();
  let football = $("#football-content").html();

  let birthContent = Mustache.render(birth, data);
  let childhoodHomeContent = Mustache.render(childhoodHome, data);
  let elementarySchoolContent = Mustache.render(elementarySchool, data);
  let taekwondoContent = Mustache.render(taekwondo, data);
  let footballContent = Mustache.render(football, data);


  addLifeEvent(map, {lat: 39.577216, lng: -104.987153}, birthContent);
  addLifeEvent(map, {lat: 39.570632, lng: -104.960932}, childhoodHomeContent);
  addLifeEvent(map, {lat: 39.626513, lng: -104.982373}, elementarySchoolContent);
  addLifeEvent(map, {lat: 39.579657, lng: -105.098878}, taekwondoContent);
  addLifeEvent(map, {lat: 39.583138, lng: -105.069205}, footballContent);
}


function addLifeEvent(map, eventCoordinates, eventText) {
  const marker = new google.maps.Marker({position: eventCoordinates, map: map});
  const info = new google.maps.InfoWindow({content: eventText});
  marker.addListener('click', function() {
    info.open(map, marker);
  });
}
