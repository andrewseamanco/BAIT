package com.google.sps.servlets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/** A Url object class for storing and retrieving API information */
@Entity
public final class Url {
  @Id Long id;
  String name;
  String url;

  public Url() {}

  public Url(Long id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }
}