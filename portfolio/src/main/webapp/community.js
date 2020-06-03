function renderComments() {
  fetch('/comments').then(response => response.json()).then((conversation) => {
    const commentSection = document.getElementById('submitted-comments');
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
