package com.google.sps.servlets;

public final class Address {
  String addressLine1;
  String addressLine2;
  String city;
  String postalCode;
  String zipCode;
  String countryCode;
  String state;
  String province;

  public Address() {
    this.addressLine1 = "";
    this.addressLine2 = "";
    this.city = "";
    this.postalCode = "";
    this.zipCode = "";
    this.countryCode = "";
    this.state = "";
    this.province = "";
  }

  public Address(String addressLine1, String addressLine2, String city, String postalCode,
      String zipCode, String state, String province, String countryCode) {
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.city = city;
    this.postalCode = postalCode;
    this.zipCode = zipCode;
    this.state = state;
    this.province = province;
    this.countryCode = countryCode;
  }
}
