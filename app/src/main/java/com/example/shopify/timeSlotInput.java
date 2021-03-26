package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class timeSlotInput extends AppCompatActivity {
    String slot;
    timeSlot ts = new timeSlot();
    public Map<String, String> time;
    public Map<String, String> person = new HashMap<>();
    public int maxPeople;
    Spinner spin;
    String open, close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot_input);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(timeSlotInput.this);
        int c = 0;
        String ph = preferences.getString("Phone Number", null);
        FirebaseFirestore.getInstance().collection("timeSlots").document("1111111111").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    maxPeople =  Integer.parseInt(doc.getString("maxPeople"));
                    time = ts.time;
                    Set<String> keyset = time.keySet();
                    Iterator<String> itr = keyset.iterator();
                    while (itr.hasNext()) {
                        String key = itr.next();
                        int value = Integer.parseInt(time.get(key));
                        if (slot.equals(key) && value != maxPeople) {
                            value += 1;
                            time.put(key, Integer.toString(value));
                            person.put(ph, key);
                        }
                        if (value == maxPeople) {

                        }
                    }
                }
                else {
                    Toast.makeText(timeSlotInput.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}