package com.example.firebase_6;

import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Calendar;


@SuppressWarnings("serial")

public class worker implements Serializable {
    String name;
    String rating;
    String uri;
    String gender;
    String age;
    String reviews;
    String working_since;
    String profession;
    String phoneNumber;


    public worker(String phoneNumber,String name, String rating,String uri,String gender,String dateOfBirth,String reviews,String working_since,String profession) {
        this.name = name;
        this.rating = rating;
        this.uri=uri;
        this.gender=gender;
        int currYear= Calendar.getInstance().get(Calendar.YEAR);
        int yearOfBirth=Integer.parseInt(dateOfBirth.substring(7));
        this.age=""+(currYear-yearOfBirth);
        this.working_since=working_since;
        this.reviews=reviews;
        this.profession=profession;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        if(rating.isEmpty())
            return "0";
        else
            return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
