package com.google.sps.servlets;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Enums.Validity;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Review {
  @Id Long reviewId;
  String userId;
  String requestId;
  Status status;
  Validity nameValidity;
  Validity usernameValidity;
  Validity emailValidity;
  Validity phoneNumValidity;
  Validity addressValidity;
  Validity imageValidity;
  int authenticityRating; // integer from 1 to 5
  String reviewerNotes;
  long submissionDate;

  private Review() {}

  public Review(Long reviewId, String userId, String requestId, Validity nameValidity,
      Validity usernameValidity, Validity phoneNumValidity, Validity addressValidity,
      Validity imageValidity, int authenticityRating, String reviewerNotes) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.requestId = requestId;
    this.status = Status.COMPLETED;
    this.nameValidity = nameValidity;
    this.usernameValidity = usernameValidity;
    this.emailValidity = emailValidity;
    this.phoneNumValidity = phoneNumValidity;
    this.addressValidity = addressValidity;
    this.imageValidity = imageValidity;
    this.authenticityRating = authenticityRating;
    this.reviewerNotes = reviewerNotes;
    this.submissionDate = System.currentTimeMillis();
  }
}