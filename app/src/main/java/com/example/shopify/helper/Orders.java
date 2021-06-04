package com.example.shopify.helper;

public class Orders {

    private String time, userph;
    private int rempay, price;

    public Orders(String time, int rempay, String userph, int price)
    {
        this.time = time;
        this.rempay = rempay;
        this.userph = userph;
        this.price = price;
    }

    public String getTime()
    {
        return time;
    }

    public int getRempay()
    {
        return rempay;
    }
    public int getPrice()
    {
        return price;
    }

    public String getUserph()
    {
        return userph;
    }
}
