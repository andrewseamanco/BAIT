package com.google.sps.servlets;
import com.google.sps.servlets.Enums.Status;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/** A Request to verify a profile with information the user filled out on the submission form. */
@Entity
public final class Request {
  @Id Long requestId;
  String userId;
  Status status;
  long submissionDate;
  String name;
  String username;
  String email;
  Address address;
  String image;
  String phoneNum;
  String notes;

  private Request() {}

  public Request(Long requestId, String userId, String name, String username, String email,
      Address address, String image, String phoneNum, String notes) {
    this.requestId = requestId;
    this.userId = userId;
    this.status = Status.PENDING;
    this.submissionDate = System.currentTimeMillis();
    this.name = name;
    this.username = username;
    this.email = email;
    this.address = address;
    this.image = image;
    this.phoneNum = phoneNum;
    this.notes = notes;
  }
}