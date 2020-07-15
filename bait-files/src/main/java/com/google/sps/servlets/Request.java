package com.google.sps.servlets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Request {
  @Id Long requestId;
  String userId;
  String status;
  String name;
  String username;
  String email;
  String phoneNum;
  String address;
  String notes;
  Long submissionDate;

  private Request() {}

  public Request(Long requestId, String userId, String status, String name, String username,
      String phoneNum, String address, String notes, Long submissionDate) {
    this.requestId = requestId;
    this.userId = userId;
    this.status = status;
    this.name = name;
    this.username = username;
    this.email = email;
    this.phoneNum = phoneNum;
    this.address = address;
    this.notes = notes;
    this.submissionDate = submissionDate;
  }
}