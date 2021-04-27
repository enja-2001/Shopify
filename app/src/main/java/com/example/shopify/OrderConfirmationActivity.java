package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderConfirmationActivity extends AppCompatActivity {

    HashMap<String,Object> hashMap;
    String ph;
    String timeSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        hashMap= (HashMap<String,Object>) getIntent().getSerializableExtra("OrderHashMap");
//        ph=(String)hashMap.get("Shop phone number");
//        timeSlot=(String)hashMap.get("Time slot");
        Log.d("hasmap",""+hashMap.size());
        for(Map.Entry m:hashMap.entrySet()){
            Log.d("hasmap",m.getKey()+" - "+m.getValue());

        }

        FirebaseFirestore.getInstance().collection("Ongoing Orders").document().set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updateTimeSlot(timeSlot);
                }
                else{
                    Toast.makeText(OrderConfirmationActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTimeSlot(String s) {
        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    long val = task.getResult().getLong(s);
                    val++;
                    FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).update(s, val).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(OrderConfirmationActivity.this, OrderConfirmationActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OrderConfirmationActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(OrderConfirmationActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}