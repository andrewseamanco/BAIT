function renderComments() {
let commentRequestedNum= document.getElementById("commentRequestedNum").value;
  fetch('/comments?commentRequestedNum=' + commentRequestedNum).then(response => response.json()).then((conversation) => {
    
    let commentSection = document.getElementById('submitted-comments');
    while (commentSection.lastChild) {
        commentSection.removeChild(commentSection.lastChild);
    }

    let commentCount = 0;
    conversation.forEach((comment) => {
        createComment(comment, commentCount%2);
        commentCount++;
    });
  });
}

function createComment(comment, color) {
    const commentSection = document.getElementById('submitted-comments');
    const commentDivElement = document.createElement('div');
    const usernameElement = document.createElement('p');
    const commentElement = document.createElement('p');
    const exitButtonElement = document.createElement('button');

    let commentDivClasses = new Array(2);
    commentDivClasses[0] = 'comment-div';

    exitButtonElement.className = 'left';

    if (color === 0) {
        commentDivClasses[1] = "lavender";
    } else {
        commentDivClasses[1] = "lavender-blush";
    }

    console.log(commentDivClasses);

    commentDivElement.classList.add(...commentDivClasses);

    usernameElement.innerHTML = comment.username + " says:";
    commentElement.innerHTML = comment.commentText;
    exitButtonElement.innerHTML = "×";

    exitButtonElement.addEventListener('click', () => {
        const params = new URLSearchParams;
        params.append('id', comment.id);
        fetch('/delete-comment', {method: "POST", body:params});

        commentDivElement.remove();
    });

    commentDivElement.appendChild(usernameElement);
    commentDivElement.appendChild(commentElement);
    commentDivElement.appendChild(exitButtonElement);
    commentDivElement.appendChild(document.createElement("br"));
    commentSection.appendChild(commentDivElement);
}
