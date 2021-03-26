package com.example.shopify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Cancel_Booking extends AppCompatActivity {

    timeSlotInput tsi = new timeSlotInput();
    Map<String, String> time;
    Map<String, String> person;
    public int maxPeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel__booking);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Cancel_Booking.this);
        String ph = preferences.getString("Phone Number", null);
        person = tsi.person;
        time = tsi.time;
        maxPeople = tsi.maxPeople;
        if(person.containsKey(ph))
        {
            Set<String> keyset = person.keySet();
            Iterator<String> itr = keyset.iterator();
            while (itr.hasNext())
            {
                String p = itr.next();
                if(p.equals(ph)) {
                    String t = person.get(p);
                    int val = Integer.parseInt(time.get(t));
                    if (val == maxPeople) {

                    }
                    val -= 1;
                    person.remove(p);
                    time.put(t, Integer.toString(val));
                    break;
                }
            }
        }
    }
}