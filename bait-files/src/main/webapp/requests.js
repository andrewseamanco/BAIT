const TABLEBODY = 'table-body';
const TABLEBODYCELL = 'table-body-cell';
const TABLEBODYROW = 'table-body-row';

const URL =
    'https://step-bait-project-2020.uc.r.appspot.com/review.html?requestId=';

function getRequests() {
  fetch('/requests').then(response => response.json()).then((requests) => {
    const queue = document.getElementById(TABLEBODY);
    for (request of requests) {
      const row = document.createElement('div');
      row.setAttribute('class', TABLEBODYROW);

      const requestId = document.createElement('td');
      requestId.setAttribute('class', TABLEBODYCELL);
      requestId.append(document.createTextNode(request.requestId));
      row.append(requestId);

      const status = document.createElement('div');
      status.setAttribute('class', TABLEBODYCELL);
      status.append(document.createTextNode(request.status));
      row.append(status);

      const timestamp = document.createElement('div');
      timestamp.setAttribute('class', TABLEBODYCELL);
      timestamp.append(document.createTextNode(new Date(request.submissionDate).toLocaleDateString() + "  " + new Date(request.submissionDate).toLocaleTimeString()));
      row.append(timestamp);

      const userId = document.createElement('div');
      userId.setAttribute('class', TABLEBODYCELL);
      userId.append(document.createTextNode(request.userId));
      row.append(userId);

      const detailView = document.createElement('div');
      detailView.setAttribute('class', TABLEBODYCELL);
      const detailLink = document.createElement('a');
      detailLink.setAttribute('href', URL + request.requestId);
      detailLink.append(document.createTextNode('View'));
      detailView.append(detailLink);
      row.append(detailView);
      queue.append(row);
    }
  });
}