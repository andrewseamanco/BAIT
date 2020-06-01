let answer = -1;

function loadInFact(index, fact_index, isTruth) {
    let fact = isTruth ? truths[fact_index] : lies[fact_index];

    switch(index) {
        case 0:
            document.getElementById("first-txt").innerHTML = fact[0];
            document.getElementById("first-img").src = fact[1];
            break;
        case 1:
            document.getElementById("second-txt").innerHTML = fact[0];
            document.getElementById("second-img").src = fact[1];
            break;
        case 2:
            document.getElementById("third-txt").innerHTML = fact[0];
            document.getElementById("third-img").src = fact[1];
            break;
    }
}

function loadInTruth() {

    let first_truth_value = Math.floor(Math.random() * truths.length);
    let second_truth_value = Math.floor(Math.random() * truths.length);
    while (first_truth_value == second_truth_value) {
        second_truth_value = Math.floor(Math.random() * truths.length);
    }

    let first_truth_index = Math.floor(Math.random() * 3);
    let second_truth_index = Math.floor(Math.random() * 3);
    while (first_truth_index == second_truth_index) {
        second_truth_index = Math.floor(Math.random() * 3);
    }

    loadInFact(first_truth_index, first_truth_value, true);
    loadInFact(second_truth_index, second_truth_value, true);

    return [first_truth_index, second_truth_index];
}


function loadInLie(truth_indeces) {
    //determine the location without a truth value
    let lie_index = (3 - (truth_indeces[0] + truth_indeces[1]));
    
    let lie_value = Math.floor(Math.random() * lies.length);
    loadInFact(lie_index, lie_value, false);

    return lie_index;
}

function next()  {
    let q_num = document.getElementById("question-num");
    if (q_num.innerHTML=="Press Start to Begin!") {
        document.getElementById("question-num").innerHTML = 1;

        document.getElementById("two-truths-btn").innerHTML = "Next";

    } else {
        let chosenAnswer = -1;
        let chosenRadios = document.querySelectorAll("input[name='chosen_answer']");
        for (chosenRadio of chosenRadios) {
            if(chosenRadio.checked) {
                chosenAnswer = chosenRadio.value;
            }
        }
        if (chosenAnswer != -1) {
            console.log(answer);
            if (answer == chosenAnswer) {
                alert("This is the lie!");
            } else {
                alert("This is the truth!");
            }
        }
    } 
    let truth_indeces = loadInTruth();
    answer = loadInLie(truth_indeces);
}
