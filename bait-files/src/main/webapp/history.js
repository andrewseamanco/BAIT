const url =
    'https://step-bait-project-2020.uc.r.appspot.com/results.jsp?reviewId=';

const PENDING_TABLE = 'pending-table';
const COMPLETED_TABLE = 'completed-table';

function getCompletedRequests() {
  fetch('/userHistory?is-pending-review=false')
      .then(response => response.json())
      .then(requests => {
        for (request of requests) {
          addRequest(request, COMPLETED_TABLE);
        }
      });
}

function getPendingRequests() {
  fetch('/userHistory?is-pending-review=true')
      .then(response => response.json())
      .then(requests => {
        for (request of requests) {
          addRequest(request, PENDING_TABLE);
        }
      });
}

function addRequest(request, table) {
  const tableToAddTo = document.getElementById(table);
  const row = document.createElement('div');
  row.setAttribute('class', 'table-body-row');
  createRowElement(
      new Date(request.submissionDate).toLocaleDateString() + '  ' +
          new Date(request.submissionDate).toLocaleTimeString(),
      row);

  const detailView = document.createElement('div');
  detailView.setAttribute('class', 'table-body-cell');

  if (table == PENDING_TABLE) {
    createRowElement(request.requestId, row);
    detailView.appendChild(document.createTextNode('Pending'))
  } else {
    createRowElement(request.requestId, row);
    const detailLink = document.createElement('a');
    detailLink.setAttribute('href', url + request.reviewId);
    detailLink.append(document.createTextNode('View'));
    detailView.append(detailLink);
  }

  row.append(detailView);
  tableToAddTo.append(row);
}

function createRowElement(textNodeContent, row) {
  const rowElement = document.createElement('div');
  rowElement.setAttribute('class', 'table-body-cell');
  rowElement.append(document.createTextNode(textNodeContent));
  row.append(rowElement);
}

function fillTables() {
  getPendingRequests();
  getCompletedRequests();
}

