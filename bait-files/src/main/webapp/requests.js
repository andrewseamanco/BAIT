const url =
    'https://step-bait-project-2020.uc.r.appspot.com/review.html?requestId=';

function getRequests() {
  fetch('/requests').then(response => response.json()).then((requests) => {
    const queue = document.getElementById('table-body');
    for (request of requests) {
      const row = document.createElement('div');
      row.setAttribute('class', 'table-body-row');

      const requestId = document.createElement('td');
      requestId.setAttribute('class', 'table-body-cell');
      requestId.append(document.createTextNode(request.requestId));
      row.append(requestId);

      const status = document.createElement('div');
      status.setAttribute('class', 'table-body-cell');
      status.append(document.createTextNode(request.status));
      row.append(status);

      const timestamp = document.createElement('div');
      timestamp.setAttribute('class', 'table-body-cell');
      timestamp.append(document.createTextNode(request.timestamp));
      row.append(timestamp);

      const userId = document.createElement('div');
      userId.setAttribute('class', 'table-body-cell');
      userId.append(document.createTextNode(request.userId));
      row.append(userId);

      const detailView = document.createElement('div');
      detailView.setAttribute('class', 'table-body-cell');
      const detailLink = document.createElement('a');
      detailLink.setAttribute('href', url + request.requestId);
      detailLink.append(document.createTextNode('View'));
      detailView.append(detailLink);
      row.append(detailView);
      queue.append(row);
    }
  });
}