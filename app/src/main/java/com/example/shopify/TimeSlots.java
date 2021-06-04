package com.example.shopify;

public class TimeSlots {
    String time;
    int value;

    public TimeSlots(String time, int value) {
        this.time = time;
        this.value = value;
    }

    public String getTime()
    {
        return time;
    }

    public int getValue()
    {
        return value;
    }
}
