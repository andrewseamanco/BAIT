function renderComments() {
  fetch('/comments').then(response => response.json()).then((conversation) => {
    console.log(conversation);
    const commentSection = document.getElementById('submitted-comments');
    conversation.forEach((comment) => {
      const usernameElement = document.createElement('p');
      const commentElement = document.createElement('p');
      usernameElement.innerHTML = comment.username;
      commentElement.innerHTML = comment.commentText;
      commentSection.appendChild(usernameElement);
      commentSection.appendChild(commentElement);
    });
  });
}
