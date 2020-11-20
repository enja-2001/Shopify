package com.example.shopify;

class Shop {

    public String address;
    public String category;
    public String closingTime;
    public String name;

    public String openingTime;

    public String pinCode;
    public String phoneNumber;
    public String rating;
    public String ratingCount;
    public String reviewCount;
    public String shopkeeper;

    public Shop(String address, String category, String closingTime, String name, String openingTime, String pinCode, String phoneNumber, String rating, String ratingCount, String reviewCount, String shopkeeper) {
        this.address = address;
        this.category = category;
        this.closingTime = closingTime;
        this.name = name;
        this.openingTime = openingTime;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.reviewCount = reviewCount;
        this.shopkeeper = shopkeeper;
    }
}
