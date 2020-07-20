package com.google.sps.servlets;

import com.google.sps.servlets.Enums.Status;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
  @Id String userId;
  String username;
  String firstName;
  String lastName;

  private User() {}

  public User(String userId, String username, String firstName, String lastName) {
    this.userId = userId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUserId() {
    return this.userId;
  }
}