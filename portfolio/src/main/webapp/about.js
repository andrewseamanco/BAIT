let answer = -1;

function loadInFact(index, factIndex, isTruth) {
    let fact = isTruth ? truths[factIndex] : lies[factIndex];

    switch(index) {
        case 0:
            document.getElementById('first-claim-text').innerHTML = fact[0];
            document.getElementById('first-claim-image').src = fact[1];
            break;
        case 1:
            document.getElementById('second-claim-text').innerHTML = fact[0];
            document.getElementById('second-claim-image').src = fact[1];
            break;
        case 2:
            document.getElementById('third-claim-text').innerHTML = fact[0];
            document.getElementById('third-claim-image').src = fact[1];
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


function loadInLie(truthIndices) {
    //determine the location without a truth value
    let lie_index = (3 - (truthIndices[0] + truthIndices[1]));
    
    let lie_value = Math.floor(Math.random() * lies.length);
    loadInFact(lie_index, lie_value, false);

    return lie_index;
}

function next()  {
    let q_num = document.getElementById('question-num');
    if (q_num.innerHTML=='Press Start to Begin!') {
        document.getElementById('question-num').innerHTML = 1;

        document.getElementById('advance-game-btn').innerHTML = 'Next';

    } else {
        let chosenAnswer = -1;
        let chosenRadios = document.querySelectorAll('input[name="selected_answer"]');
        for (chosenRadio of chosenRadios) {
            if(chosenRadio.checked) {
                chosenAnswer = chosenRadio.value;
            }
        }
        if (chosenAnswer != -1) {
            console.log(answer);
            if (answer == chosenAnswer) {
                alert('This is the lie!');
            } else {
                alert('This is the truth!');
            }
        }
    } 
    const truth_indices = loadInTruth();
    answer = loadInLie(truth_indices);
}
