package com.techelevator.model;


public class Campground {

  private long campgroundId;
  private long parkId;
  private String name;
  private String openFromMm;
  private String openToMm;
  private String dailyFee;
  private boolean isOpen = false;
  private boolean isAvailable = false;


  public long getCampgroundId() {
    return campgroundId;
  }

  public void setCampgroundId(long campgroundId) {
    this.campgroundId = campgroundId;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public long getParkId() {
    return parkId;
  }

  public void setParkId(long parkId) {
    this.parkId = parkId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getOpenFromMm() {
    return openFromMm;
  }

  public void setOpenFromMm(String openFromMm) {
    this.openFromMm = openFromMm;
  }


  public String getOpenToMm() {
    return openToMm;
  }

  public void setOpenToMm(String openToMm) {
    this.openToMm = openToMm;
  }


  public String getDailyFee() {
    return dailyFee;
  }

  public void setDailyFee(String dailyFee) {
    this.dailyFee = dailyFee;
  }

}
