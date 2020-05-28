console.log(truths);

function loadInTruth() {
    let firstTruth = Math.floor(Math.random() * truths.length);
    let secondTruth = Math.floor(Math.random() * truths.length);
    while (firstTruth == secondTruth && truths.length != 1) {
        secondTruth = Math.floor(Math.random() * truths.length);
    }

    let firstPlace = Math.floor(Math.random() * 3);
    let secondPlace = Math.floor(Math.random() * 3);
    while (firstPlace == secondPlace) {
        secondPlace = Math.floor(Math.random() * 3);
    }

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
}

loadInTruth();
