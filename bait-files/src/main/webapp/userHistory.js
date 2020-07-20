const url =
    'https://step-bait-project-2020.uc.r.appspot.com/review.html?reviewId=';

function getCompletedReviews() {
  fetch('/userHistory?is-pending-request=false')
      .then(response => response.json())
      .then((reviews) => {
        const queue = document.getElementById('table-body');
        for (review of reviews) {
          console.log(review);
          addReview(review, document.getElementById('completed-table'));
        }
      });
}

function getPendingReviews() {
  fetch('/userHistory?is-pending-request=true')
      .then(response => response.json())
      .then((reviews) => {
        for (review of reviews) {
          console.log(review);
          addReview(review, document.getElementById('pending-table'));
        }
      });
}

function addReview(review, tableToAddTo) {
  const queue = tableToAddTo;
  const row = document.createElement('div');
  row.setAttribute('class', 'table-body-row');

  const timestamp = document.createElement('div');
  timestamp.setAttribute('class', 'table-body-cell');
  timestamp.append(document.createTextNode(review.timestamp));
  row.append(timestamp);

  const detailView = document.createElement('div');
  detailView.setAttribute('class', 'table-body-cell');
  const detailLink = document.createElement('a');
  detailLink.setAttribute('href', url + review.requestId);
  detailLink.append(document.createTextNode('View'));
  detailView.append(detailLink);
  row.append(detailView);
  queue.append(row);
}

function fillTables() {
  getPendingReviews();
  getCompletedReviews();
}