package com.google.sps.servlets;

import com.google.sps.servlets.Enums.Status;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.sps.servlets.Enums.Permission;


@Entity
public class User {
  @Id String personId;
  String username;
  String firstName;
  String lastName;
  Permission permission;

  private User() {}

  public User(String personId, String username, String firstName, String lastName, Permission permission) {
    this.personId = personId;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.permission = permission;
  }

  public String getUserId() {
    return this.personId;
  }

  public String getUsername() {
    return this.username;
  }

  public Permission getPermission() {
      return this.permission;
  }
}