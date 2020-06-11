  let birthContent = '<div id="content">'+
      '<h1 id="firstHeading">Step 1: The Hospital Where I was Born</h1>'+
      '<div id="bodyContent">'+
      '<p>On April 19, 2000, I was born at Littleton Adventist Hospital ' +
      'with these character traits </p>' + 
      '<ul>' +
      '<li>Gender: Boy</li>' +
      '<li>Weight: 6 pounds 3.4 ounces </li>' +
      '<li>Height: 19 inches </li>' +
      '<li>Time: 9:30PM </li>' +
      '<li>Star Sign: Taurus (but Aires cusp) </li>' +
      '<li>Rising Sign: Scorpio </li>' +
      '<li>Chinese Year: Golden Dragon [heck yeah]</li>' +
      '<li>Dexterity: 0</li>' +
      '</div>'+
      '</div>';

  let childhoodHomeContent = '<div id="childhoodHomeContent">' +
        '<h1 id="header">Step 2: My Childhood Home </h1>' + 
        '<div id="bodyContent">' +
        '<p>I spent a lot of time kicking around on a piece of ground in my home town right here in ' +
        'Littleton Colorado.  I primarily spent the beginning years of my life going to the library, playing ' +
        'hide and seek, and doing other stuff that toddlers do.  Also a fun fact, but I have always ' +
        'struggled with my directions and around this time I discovered that I could read words better backwards ' +
        'than I could forewards.  These two character quirks are why I ended up spelling my name ' +
        'Anbrew for a very long time!' +
        '</div>' +
        '</div>';

let elementarySchoolContent = '<div id="elementarySchoolContent">' +
        '<h1 id="header">Step 3: My Childhood Elementary School </h1>' +
        '<p> I attended Elementary School at All Souls Catholic School.  In order to make sure that we ' +
        'had school spirit, our mascot was the spirits!  Yep you heard it here, the All Soulds Spirits. ' +
        'While at All Souls I learned many interesting things like where to sit during indoor recess so I ' +
        'could make it to the computer first to play video games, the reasons that being a monk probably is ' +
        'not for me while we listened to Gregorian monk chanting to <a href="https://www.catholic.org/lent/abfast.php"> fast </a> ' +
        'or durability testing while trying to break -- yes I mean shatter -- the (not very well) reheated pancakes by ' +
        'throwing them against the table.'
        '</div>';

let taekwondoeContent = '<div id="taekwondoContent">' +
        '<h1 id="header">Step 4: Various Activities </h1>' +
        '<p> Around this time my parents were trying to figure out the type of kid that I would be.  To accomplish '+
        'these ends, my parents enrolled me in a bunch of athletic activities.  Unfortunately, or fortunately because ' +
        'otherwise you would not be reading about the wonders of childhood taekwondo, my cousins had become ' +
        'very invested in taekwondoe before me.  Taekwondo is, at a professional level, a martial arts style that ' +
        'is characterized by fast kicking, head height kicks and other kickass techniques.  However, for third grader ' +
        'American beginners taking lessons from a dojo next to a hair salon, it mostly meant doing "moves" around ' +
        'a square made with duck tape in my basement for a few hours every day.  The only "cool" part if you can really ' +
        'characterize anything that I was doing around that time as cool was breaking through plastic boards.  A fun bonus aside ' +
        'story is in fifth grade - long after I had quit after only achieving a purple belt - my friend got really into ' +
        'taekwondo.  When it came to board breaking the instructor held the board a little bit too high and I punched him ' +
        'in the neck really hard by accident.  That was my last taekwondo experience. </p>' +
        '</div>';

let footballContent = '<div id="footballContent">' +
        '<h1 id="header">Step 4: Various Activities </h1>' +
        '<p> Since I did not in fact become the Karate (or Taekwondo?) Kid, my parents, inspired by the movie ' +
        'remember the Titans enrolled me in football.  At this time in my life I was definitely not focused on ' +
        'the glory of scoring a touchdown or the thrill of making a really good tackle.  Instead I was mostly ' +
        'just going with the flow - Hukuna Matata style.  Therefore I was the bane of my "football is my life and ' +
        'if this team doesn\'t make it to the fifth grade superbowl I am going to freak out" coach\'s life. ' +
        'I pretty much rode the bench except for the ten minutes [mandatory to appease the parents who paid a ridiculous ' +
        'amount of money for their fifth grader to play D-1 football].  Ironically, during this time I discovered ' +
        'that I was really good at being coached.  Even though I never played, I ended up in the best shape of my young ' +
        'life and was ready to actually play some sports that I enjoyed. </p>' +
        '</div>';


function createMap() {
    let map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 39.577216, lng: -104.987153150},
    zoom: 8
    });

    addLifeEvent(map, {lat: 39.577216, lng: -104.987153}, birthContent);
    addLifeEvent(map, {lat: 39.570632, lng: -104.960932}, childhoodHomeContent);
    addLifeEvent(map, {lat: 39.626513, lng: -104.982373}, elementarySchoolContent);
    addLifeEvent(map, {lat: 39.579657, lng: -105.098878}, taekwondoeContent);
    addLifeEvent(map, {lat: 39.583138, lng: -105.069205}, footballContent);
}


function addLifeEvent(map, eventCoordinates, eventText) {
    let marker = new google.maps.Marker({position: eventCoordinates, map: map});
    let info = new google.maps.InfoWindow({
        content: eventText
    }); 
    marker.addListener('click', function() {
        info.open(map, marker);
    });

}