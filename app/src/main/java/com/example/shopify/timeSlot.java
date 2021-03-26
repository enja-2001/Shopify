package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import developers.mobile.abt.FirebaseAbt;

public class timeSlot extends AppCompatActivity {
    public int size;
    private String open;
    private String close;
    private DatabaseReference shop;
    public HashMap<String, String> time = new HashMap<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);

        FirebaseFirestore.getInstance().collection("timeSlots").document("1111111111").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();
                    open = doc.getString("Opening time");
                    close = doc.getString("Closing time");
                }
                else
                {
                    Toast.makeText(timeSlot.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String hro = open.substring(0, open.indexOf(':'));
        String mo = open.substring(open.indexOf(':')+1, open.indexOf(' '));
        String hre = close.substring(0, close.indexOf(':'));
        String me = close.substring(close.indexOf(':')+1, close.indexOf(' '));
        int hr, m;
        String t;
        hr = Integer.parseInt(hro);
        m = Integer.parseInt(mo);
        String sl[] = {"0", "15", "30", "45", "0"};
        while (true)
        {
            if(Integer.toString(hr).equals(hre))
            {
                if (Integer.toString(m).equals(me))
                    break;
            }
            size++;
            m = m+15;
            if(m > 60)
            {
                hr = hr++;
                m = m-60;
            }
            t = Integer.toString(hr) + ":" + Integer.toString(m);
            time.put(t, "0");
        }
        shop = FirebaseDatabase.getInstance().getReference();
        shop.child("Null").push().setValue(time);
        FirebaseFirestore.getInstance().collection("timeSlots").document("1111111111").set(time);
    }
}