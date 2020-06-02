let correctAnswer = -1;

/**
 * Advances the game to the next question. Loads in the first question 
 * game has not yet started. Otherwise, checks answer against user 
 * selected anwer and alerts if a lie or truth has been chosen.
 */
function advanceGame()  {
    let gameStatus = document.getElementById('game-status');
    if (gameStatus.innerHTML=='Press Start to Begin!') {
        gameStatus.innerHTML = 1;
        document.getElementById('advance-game-btn').innerHTML = 'Next';
    } else {
        let chosenAnswer = -1;
        const chosenRadios = document.querySelectorAll('input[name="selected-answer"]');
        for (chosenRadio of chosenRadios) {
            if(chosenRadio.checked) {
                chosenAnswer = chosenRadio.value;
            }
        }
        if (chosenAnswer != -1) {
            if (correctAnswer == chosenAnswer) {
                alert('This is the lie!');
            } else {
                alert('This is the truth!');
            }
        }
    } 
    const truthIndices = loadInTruth();
    correctAnswer = loadInLie(truthIndices);
}

/**
 * Populates the page with two true facts in randomized indexes
 * 
 *@return {Array} an array populated with two numbers containing
 * locations of the true facts
 */
function loadInTruth() {

    let firstTruthValue = Math.floor(Math.random() * truths.length);
    let secondTruthValue = Math.floor(Math.random() * truths.length);
    while (firstTruthValue == secondTruthValue) {
        secondTruthValue = Math.floor(Math.random() * truths.length);
    }

    let firstTruthElementIndex = Math.floor(Math.random() * 3);
    let secondTruthElementIndex = Math.floor(Math.random() * 3);
    while (firstTruthElementIndex == secondTruthElementIndex) {
        secondTruthElementIndex = Math.floor(Math.random() * 3);
    }

    loadInFact(firstTruthElementIndex, firstTruthValue, true);
    loadInFact(secondTruthElementIndex, secondTruthValue, true);

    return [firstTruthElementIndex, secondTruthElementIndex];
}


/**
 * Gets the image and text from the position stored in index and 
 * populates it with the fact stored in factIndex.  Populates a truth
 * if isTruth is true and a lie otherwise
 * 
 *@param {number} index the position we will populate the fact
 *@param {number} factIndex the position in the array of the 
 * fact that will be populated
 *@param {boolean} isTruth true if we will populate a truth; 
 * false if we will populate a lie
 */

/**
 * Loads in a lie [used after loadInTruth]
 * 
 *@param {Array} truthIndices indices where true facts have 
 * been populated
 * @return {number} index populated by lie
 */
function loadInLie(truthIndices) {
    //determine the location without a truth value
    let lieElementIndex = (3 - (truthIndices[0] + truthIndices[1]));
    
    let lieValue = Math.floor(Math.random() * lies.length);
    loadInFact(lieElementIndex, lieValue, false);

    return lieElementIndex;
}

function loadInFact(elementIndex, factIndex, isTruth) {
    let fact = isTruth ? truths[factIndex] : lies[factIndex];

    switch(elementIndex) {
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