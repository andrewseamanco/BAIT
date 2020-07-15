package com.google.sps.servlets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.sps.servlets.Enums.Status;
import com.google.sps.servlets.Enums.Validity;

@Entity
public class Review {
  @Id Long reviewId;
  String userId;
  String requestId;
  Status status;
  String nameValidity;
  String usernameValidity;
  String emailValidity;
  String phoneNumValidity;
  String addressValidity;
  String imageValidity;
  int authenticityRating; // integer from 1 to 5
  String reviewerNotes;

  private Review() {}

  public Review(Long reviewId, String userId, String requestId, String nameValidity,
      String usernameValidity, String phoneNumValidity, String addressValidity,
      String imageValidity, int authenticityRating, String reviewerNotes) {
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
  }
}