package com.example.shopify;

import java.io.Serializable;
import java.util.ArrayList;

public class OngoingOrderModel implements Serializable {
    ArrayList<OrderNode> al;
    String timeSlot;
    String shopPhoneNumber;
    String date;
    String shopName;
    String address;

    public OngoingOrderModel(String shopName,ArrayList<OrderNode> al, String timeSlot, String shopPhoneNumber, String date, String address) {
        this.shopName=shopName;
        this.al = al;
        this.timeSlot = timeSlot;
        this.shopPhoneNumber = shopPhoneNumber;
        this.date = date;
        this.address = address;
    }
}

