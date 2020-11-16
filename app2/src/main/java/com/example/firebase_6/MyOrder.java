package com.example.firebase_6;

import java.io.Serializable;

class MyOrder implements Serializable {
    String orderId;
    String userPhoneNumber;
    String workerPhoneNumber;
    String name;
    String address;
    String phoneNumber;
    String scheduledDate;
    String scheduledTime;
    String orderPlacingDate;
    String orderPlacingTime;
    String paymentMethod;
    String paymentStatus;
    String workStatus;
    String startingDate;
    String endingDate;
    String startingTime;
    String endingTime;
    String rating;
    String review;

    public MyOrder(String review,String rating,String startingDate,String startingTime,String endingDate,String endingTime,String workStatus,String paymentMethod,String paymentStatus,String orderPlacingDate,String orderPlacingTime,String orderId, String userPhoneNumber, String workerPhoneNumber, String name, String address, String phoneNumber, String scheduledDate, String scheduledTime) {
        this.orderId = orderId;
        this.workStatus=workStatus;
        this.userPhoneNumber = userPhoneNumber;
        this.workerPhoneNumber = workerPhoneNumber;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
        this.orderPlacingDate=orderPlacingDate;
        this.orderPlacingTime=orderPlacingTime;
        this.paymentMethod=paymentMethod;
        this.paymentStatus=paymentStatus;
        this.startingDate=startingDate;
        this.startingTime=startingTime;
        this.endingDate=endingDate;
        this.endingTime=endingTime;
        this.review=review;
        this.rating=rating;
    }
}
