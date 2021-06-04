package com.example.shopify;

public class TimeSlots {
    String time;
    long value;

    public TimeSlots(String time, long value) {
        this.time = time;
        this.value = value;
    }

    public String getTime()
    {
        return time;
    }

    public long getValue()
    {
        return value;
    }
}
