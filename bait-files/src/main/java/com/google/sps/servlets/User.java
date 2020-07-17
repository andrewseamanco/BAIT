package com.google.sps.servlets;

import com.google.sps.servlets.Enums.Status;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
  @Id String personId;
  String username;
  String firstName;
  String lastName;

  private User() {}

  public User(String personId, String username, String firstName, String lastName) {
    this.personId = personId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUserId() {
      return this.personId;
  }

  public String getUsername() {
      return this.username;
  }
}