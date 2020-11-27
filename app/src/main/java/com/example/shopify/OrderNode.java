package com.example.shopify;

class OrderNode {
    public String category;
    public String subCategory;
    public String description;
    public String price;
    public String image;
    public String quantity;

    public OrderNode(String subCategory, String description, String price, String image, String quantity) {
//        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
