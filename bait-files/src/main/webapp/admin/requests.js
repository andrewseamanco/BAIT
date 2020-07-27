const TABLE_BODY = 'table-body';
const TABLE_BODY_CELL = 'table-body-cell';
const TABLE_BODY_ROW = 'table-body-row';

const URL =
    'https://step-bait-project-2020.uc.r.appspot.com/review.html?requestId=';

function getRequests() {
  fetch('/requests?illegalAccess=false').then(response => response.json()).then((requests) => {
    const queue = document.getElementById(TABLE_BODY);
    for (request of requests) {
      const row = document.createElement('div');
      row.setAttribute('class', TABLE_BODY_ROW);

      const requestId = document.createElement('td');
      requestId.setAttribute('class', TABLE_BODY_CELL);
      requestId.append(document.createTextNode(request.requestId));
      row.append(requestId);

      const status = document.createElement('div');
      status.setAttribute('class', TABLE_BODY_CELL);
      status.append(document.createTextNode(request.status));
      row.append(status);

      const timestamp = document.createElement('div');
      timestamp.setAttribute('class', TABLE_BODY_CELL);
      timestamp.append(document.createTextNode(
          new Date(request.submissionDate).toLocaleDateString() + '  ' +
          new Date(request.submissionDate).toLocaleTimeString()));
      row.append(timestamp);

      const userId = document.createElement('div');
      userId.setAttribute('class', TABLE_BODY_CELL);
      userId.append(document.createTextNode(request.userId));
      row.append(userId);

      const detailView = document.createElement('div');
      detailView.setAttribute('class', TABLE_BODY_CELL);
      const detailLink = document.createElement('a');
      detailLink.setAttribute('href', URL + request.requestId);
      detailLink.append(document.createTextNode('View'));
      detailView.append(detailLink);
      row.append(detailView);
      queue.append(row);
    }
  });
}