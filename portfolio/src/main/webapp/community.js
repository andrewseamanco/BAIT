function renderComments() {
let quantity = document.getElementById("quantity").value;
  fetch('/comments?quantity=' + quantity).then(response => response.json()).then((conversation) => {
    console.log(conversation);

    let commentSection = document.getElementById('submitted-comments');
    while (commentSection.lastChild) {
        commentSection.removeChild(commentSection.lastChild);
    }

    conversation.forEach((comment) => {
        createComment(comment);
    });
  });
}

function createComment(comment) {
    const commentSection = document.getElementById('submitted-comments');
    const commentDivElement = document.createElement('div');
    const usernameElement = document.createElement('p');
    const commentElement = document.createElement('p');
    const exitButtonElement = document.createElement('button');

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
    commentSection.appendChild(commentDivElement);
}
