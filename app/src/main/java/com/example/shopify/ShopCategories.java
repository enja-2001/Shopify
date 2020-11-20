package com.example.shopify;

class ShopCategories {

    public String category;
    public String imgstr;

    public ShopCategories(String category, String imgstr) {
        this.category = category;
        this.imgstr = imgstr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgstr() {
        return imgstr;
    }

    public void setImgstr(String imgstr) {
        this.imgstr = imgstr;
    }
}
