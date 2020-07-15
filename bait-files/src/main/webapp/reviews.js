const reviewPage =
    'https://8080-b649220a-2c73-40b7-9ab6-c56ef1009571.us-east1.cloudshell.dev/review.html?requestId=';

function getReviews() {
  fetch('/reviews').then(response => response.json()).then((reviews) => {
    const queue = document.getElementById('table-body');
    for (review of reviews) {
      const row = document.createElement('div');
      row.setAttribute('class', 'table-body-row');

      const requestId = document.createElement('td');
      requestId.setAttribute('class', 'table-body-cell');
      requestId.append(document.createTextNode(review.requestId));
      row.append(requestId);

      const status = document.createElement('div');
      status.setAttribute('class', 'table-body-cell');
      status.append(document.createTextNode(review.status));
      row.append(status);

      const timestamp = document.createElement('div');
      timestamp.setAttribute('class', 'table-body-cell');
      timestamp.append(document.createTextNode(review.timestamp));
      row.append(timestamp);
      
      const userId = document.createElement('div');
      userId.setAttribute('class', 'table-body-cell');
      userId.append(document.createTextNode(review.userId));
      row.append(userId);

      const detailView = document.createElement('div');
      detailView.setAttribute('class', 'table-body-cell');
      const detailLink = document.createElement('a');
      detailLink.setAttribute('href', reviewPage + review.requestId);
      detailLink.append(document.createTextNode('View'));
      detailView.append(detailLink);
      row.append(detailView);
      queue.append(row);
    }
  });
}