const url =
    'https://step-bait-project-2020.uc.r.appspot.com/review.html?reviewId=';

function getCompletedReviews() {
  fetch('/userHistory?is-pending-review=false')
      .then(response => response.json())
      .then(reviews => {
        for (review of reviews) {
          addReview(review, document.getElementById('completed-table'));
        }
      });
}

function getPendingReviews() {
  fetch('/userHistory?is-pending-review=true')
      .then(response => response.json())
      .then(reviews => {
        for (review of reviews) {
          addReview(review, document.getElementById('pending-table'));
        }
      });
}

function addReview(review, tableToAddTo) {
  const row = document.createElement('div');
  row.setAttribute('class', 'table-body-row');

  createRowElement(review.timestamp, row);
  createRowElement(review.reviewId, row);

  const detailView = document.createElement('div');
  detailView.setAttribute('class', 'table-body-cell');
  const detailLink = document.createElement('a');
  detailLink.setAttribute('href', url + review.requestId);
  detailLink.append(document.createTextNode('View'));
  detailView.append(detailLink);
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
  getPendingReviews();
  getCompletedReviews();
}