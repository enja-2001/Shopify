package com.example.shopify;

import java.io.Serializable;
import java.util.ArrayList;

public class OngoingOrderModel implements Serializable {
    public ArrayList<OrderNode> al;
    public String timeSlot;
    public String shopPhoneNumber;
    public String date;
    public String shopName;
    public String address;
    public String orderId;
    public String totalPrice;
    public String remainingPrice;

    public OngoingOrderModel(String totalPrice,String remainingPrice,String orderId,String shopName,ArrayList<OrderNode> al, String timeSlot, String shopPhoneNumber, String date, String address) {
        this.totalPrice=totalPrice;
        this.remainingPrice=remainingPrice;
        this.orderId=orderId;
        this.shopName=shopName;
        this.al = al;
        this.timeSlot = timeSlot;
        this.shopPhoneNumber = shopPhoneNumber;
        this.date = date;
        this.address = address;
    }
}

