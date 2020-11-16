package com.example.firebase_6;

public class UserAddress {
    String name;
    String phoneNumber;
    String address;
    String accountNumber;
    String houseNumber;

    private boolean isChecked;

    public  UserAddress(){

    }
    public UserAddress(String name, String phoneNumber, String address,String houseNumber,String accountNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accountNumber=accountNumber;
        this.isChecked=false;
        this.houseNumber=houseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
