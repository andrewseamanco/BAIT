const starfilled = "★";
const staroutline = "☆";
const maxRating = 5;

const STARRATING = "star-rating";
const AUTHENTICITYEXPLANATION = "authenticity-explanation";

const DOESNOTEXIST = "It is likely that this person does not exist.";
const EXISTS = "It is likely that this person exists.";

const PHONEINPUT = 'phone-input-container';
const NAMEINPUT = 'name-input-container';
const USERNAMEINPUT = 'username-input-container';
const EMAILINPUT = 'email-input-container';
const IMAGEINPUT = 'image-input-container';
const ADDRESSINPUT = 'address-input-container';
const NOTESINPUT = 'notes-input-container';

const PHONERESULTS = 'phone-results-container';
const NAMERESULTS = 'name-results-container';
const USERNAMERESULTS = 'username-results-container';
const EMAILRESULTS = 'email-results-container';
const IMAGERESULTS = 'image-results-container';
const ADDRESSRESULTS = 'address-results-container';
const REVIEWERNOTES = 'reviewer-notes-container';

const VALID = "VALID";
const INVALID = "INVALID";
const NOTAPPLICABLE = "N/A";

const invalidString = "There were some problems validating the provided information.";
const validString = "There were no problems validating the provided information.";
// const unknownString = "There was no information provided.";


function getReview() {
  const queryString = window.location.search;
  const params = new URL(location.href).searchParams;
  const requestId = params.get('reviewId');
  if (requestId == null || isNaN(requestId)) {
    window.location.replace('/');
    return;
  }

  fetch('/review' + queryString)
      .then(response => response.json())
      .then((result) => {

        if(result.request.name == null || result.request.name == "") {
          document.getElementById(NAMEINPUT).appendChild(document.createTextNode(NOTAPPLICABLE));
        }
        else {
          document.getElementById(NAMEINPUT).appendChild(document.createTextNode(result.request.name));          
        }

        if(result.request.email == null || result.request.email == "") {
          document.getElementById(EMAILINPUT).appendChild(document.createTextNode(NOTAPPLICABLE));  

        }
        else {
          document.getElementById(EMAILINPUT).appendChild(document.createTextNode(result.request.email));     
        }

        if(result.request.username == null || result.request.username == "") {
          document.getElementById(USERNAMEINPUT).appendChild(document.createTextNode(NOTAPPLICABLE));   
        }
        else {
          document.getElementById(USERNAMEINPUT).appendChild(document.createTextNode(result.request.username));     
        }

        if(result.request.phoneNum == null || result.request.phoneNum == "") {
          document.getElementById(PHONEINPUT).appendChild(document.createTextNode(NOTAPPLICABLE)); 

        }
        else {
          document.getElementById(PHONEINPUT).appendChild(document.createTextNode(result.request.phoneNum));                
        }

        if(result.request.address == null || result.request.address == "") {
          document.getElementById(ADDRESSINPUT).appendChild(document.createTextNode(NOTAPPLICABLE));            
        }
        else {
          document.getElementById(ADDRESSINPUT).appendChild(document.createTextNode(result.request.address));             
        }

        if(result.request.image == null || result.request.image == "" || result.request.image == undefined) {
          document.getElementById(IMAGEINPUT).appendChild(document.createTextNode(NOTAPPLICABLE));     
        }
        else {
          document.getElementById(IMAGEINPUT).appendChild(document.createTextNode(result.request.image));            
        }

        var stars = " "
        for(let i = 0; i < maxRating; i++) {
            if(i < result.review.authenticityRating) {
                stars += starfilled;
                continue;
            }
            stars += staroutline;
        }

        document.getElementById(STARRATING).appendChild(document.createTextNode(stars));
        
        if(result.review.authenticityRating <= 2){
            document.getElementById(AUTHENTICITYEXPLANATION).appendChild(document.createTextNode(DOESNOTEXIST));
        }
        else{
            document.getElementById(AUTHENTICITYEXPLANATION).appendChild(document.createTextNode(EXISTS));
        }

        if(result.review.nameValidity == VALID){
          document.getElementById(NAMERESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(NAMERESULTS).appendChild(document.createTextNode(invalidString));
        }

        if(result.review.emailValidity == VALID){
          document.getElementById(EMAILRESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(EMAILRESULTS).appendChild(document.createTextNode(invalidString));
        }

        if(result.review.usernameValidity == VALID){
          document.getElementById(USERNAMERESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(USERNAMERESULTS).appendChild(document.createTextNode(invalidString));
        }        

        if(result.review.phoneNumValidity == VALID){
          document.getElementById(PHONERESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(PHONERESULTS).appendChild(document.createTextNode(invalidString));
        }     

        if(result.review.addressValidity == VALID){
          document.getElementById(ADDRESSRESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(ADDRESSRESULTS).appendChild(document.createTextNode(invalidString));
        }

        if(result.review.imageValidity == VALID){
          document.getElementById(IMAGERESULTS).appendChild(document.createTextNode(validString));
        }
        else{
          document.getElementById(IMAGERESULTS).appendChild(document.createTextNode(invalidString));
        }

        document.getElementById(REVIEWERNOTES).appendChild(document.createTextNode(result.review.reviewerNotes));
  });
}
