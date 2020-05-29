let answer = 0;

function loadInTruth() {

    let firstTruth = Math.floor(Math.random() * truths.length);
    let secondTruth = Math.floor(Math.random() * truths.length);
    while (firstTruth == secondTruth) {
        secondTruth = Math.floor(Math.random() * truths.length);
    }

    let firstPlace = Math.floor(Math.random() * 3);
    let secondPlace = Math.floor(Math.random() * 3);
    while (firstPlace == secondPlace) {
        secondPlace = Math.floor(Math.random() * 3);
    }

    let truthLocations = new Array(2);
    truthLocations[0] = firstPlace;
    truthLocations[1] = secondPlace;

    if (firstPlace==0) {
        document.getElementById("first_txt").innerHTML = truths[firstTruth][0];
        document.getElementById("first_img").src = truths[firstTruth][1];
    } else if (firstPlace==1) {
        document.getElementById("second_txt").innerHTML = truths[firstTruth][0];
        document.getElementById("second_img").src = truths[firstTruth][1];
    } else if (firstPlace==2) {
        document.getElementById("third_txt").innerHTML = truths[firstTruth][0];
        document.getElementById("third_img").src = truths[firstTruth][1];
    }

    if (secondPlace == 0) {
        document.getElementById("first_txt").innerHTML = truths[secondTruth][0];
        document.getElementById("first_img").src = truths[secondTruth][1];
    } else if (secondPlace==1) {
        document.getElementById("second_txt").innerHTML = truths[secondTruth][0];
        document.getElementById("second_img").src = truths[secondTruth][1];
    } else if (secondPlace==2) {
        document.getElementById("third_txt").innerHTML = truths[secondTruth][0];
        document.getElementById("third_img").src = truths[secondTruth][1];
    }

    return truthLocations;
}

function loadInLie(truthLocations) {
    //determine the location without a truth value
    let lieLocation = (3 - (truthLocations[0] + truthLocations[1]));

    let lie = Math.floor(Math.random() * lies.length);
    
    if (lieLocation==0) {
        document.getElementById('first_txt').innerHTML = lies[lie][0];
        document.getElementById('first_img').src = lies[lie][1];
    } else if (lieLocation==1) {
        document.getElementById('second_txt').innerHTML = lies[lie][0];
        document.getElementById('second_img').src = lies[lie][1];
    } else if (lieLocation==2) {
        document.getElementById('third_txt').innerHTML = lies[lie][0];
        document.getElementById('third_img').src = lies[lie][1];
    }
    return lieLocation;
}

function loadInNewQuestion() {
    let truthLocations = loadInTruth();
    answer = loadInLie(truthLocations);
}

function next()  {
    let q_num = document.getElementById("question_num");
    if (q_num.innerHTML=="Press Start to Begin!") {
        document.getElementById("question_num").innerHTML = 1;
        loadInNewQuestion();
        document.getElementById("two_truths").innerHTML = "Next";
    } else {
        let selectedAnswer = -1;
        let chosenRadios = document.querySelectorAll("input[name='chosen_answer']");
        for (chosenRadio of chosenRadios) {
            if(chosenRadio.checked) {
                selectedAnswer = chosenRadio.value;
            }
        }
        if (selectedAnswer!=-1) {
            if (answer==selectedAnswer) {
                alert("This is the lie!");
            } else {
                alert("This is the truth!");
            }
            loadInNewQuestion();
        }
    }
}
