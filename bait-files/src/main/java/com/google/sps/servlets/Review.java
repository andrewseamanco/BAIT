package com.google.sps.servlets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Review {
  @Id Long reviewId;
  String userId;
  String requestId;
  String status;
  String nameValidity;
  String usernameValidity;
  String emailValidity;
  String phoneNumValidity;
  String addressValidity;
  String imageValidity;
  String authenticityRating;
  String reviewerNotes;
  long submissionDate;

  private Review() {}

  public Review(Long reviewId, String userId, String requestId, String status, String nameValidity,
      String usernameValidity, String phoneNumValidity, String addressValidity,
      String imageValidity, String authenticityRating, String reviewerNotes, long submissionDate) {
    this.reviewId = reviewId;
    this.userId = userId;
    this.requestId = requestId;
    this.status = status;
    this.nameValidity = nameValidity;
    this.usernameValidity = usernameValidity;
    this.emailValidity = emailValidity;
    this.phoneNumValidity = phoneNumValidity;
    this.addressValidity = addressValidity;
    this.imageValidity = imageValidity;
    this.authenticityRating = authenticityRating;
    this.reviewerNotes = reviewerNotes;
    this.submissionDate = submissionDate;
  }
}