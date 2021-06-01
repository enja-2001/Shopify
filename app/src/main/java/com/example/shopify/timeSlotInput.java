package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class timeSlotInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String timeSlot;
    String ph;
    String shopName;
    String shopAddress;
    int maxCustomer;
    ArrayList alTimeSlot;
    ArrayList<Long> alValue;
    timeSlot ts = new timeSlot();
    public Map<String, String> time;
    public Map<String, String> person = new HashMap<>();
    public int maxPeople;

    Button butProceed;
    Button butBack;
    Spinner spinner;
    ArrayList<OrderNode> alOrderList;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot_input);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ph = preferences.getString("Shop Phone Number", null);

        alOrderList= (ArrayList<OrderNode>) getIntent().getSerializableExtra("OrderList");

        butProceed=(Button)findViewById(R.id.butProceed);
        butBack=findViewById(R.id.buttonBack);
        spinner=(Spinner)findViewById(R.id.spinner);

        FirebaseFirestore.getInstance().collection("Shops").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    String str=documentSnapshot.getString("Maximum customer");
                    maxCustomer=Integer.parseInt(str);
                }
                else{
                    Toast.makeText(timeSlotInput.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    HashMap<String,Object> hashMap=(HashMap<String, Object>)documentSnapshot.getData();
                    populateArrayList(hashMap);
                    createSpinner();
                }
                else{
                    Toast.makeText(timeSlotInput.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        butProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = spinner.getSelectedItem().toString();

                    String userPhoneNumber = preferences.getString("Phone Number", null);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,yyyy");

                    FirebaseFirestore.getInstance().collection("Shops").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                shopName = task.getResult().getString("Name");
                                shopAddress = task.getResult().getString("Address");

                                Log.d("Inside", shopName + " - " + shopAddress);

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("Time slot", s);
                                hashMap.put("Order list", alOrderList);
                                hashMap.put("User phone number", userPhoneNumber);
                                hashMap.put("Shop phone number", ph);
                                hashMap.put("User address", "");
                                hashMap.put("Shop Name", shopName);
                                hashMap.put("Shop Address", shopAddress);
                                hashMap.put("Date", simpleDateFormat.format(calendar.getTime()));


                                Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
//                                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                intent.putExtra("OrderHashMap", hashMap);
                                startActivity(intent);

                            } else {
                                Toast.makeText(timeSlotInput.this, "Error occured !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateArrayList(HashMap<String,Object> hashMap){
        String key="";
        long val=0;
        alTimeSlot=new ArrayList<String>();
        alValue=new ArrayList<Long>();

        ArrayList<String> a1=new ArrayList<>(); //AM to AM
        ArrayList<String> a2=new ArrayList<>(); //PM to PM
        ArrayList<String> a3=new ArrayList<>();//AM to PM
        ArrayList<String> a4=new ArrayList<>();//PM to PM for 12:00


        for(Map.Entry m:hashMap.entrySet()){
            key=(String)m.getKey();
            val=(long)m.getValue();

            alValue.add(val);
            int len=key.length();

            if(key.charAt(len-2)=='A' && key.charAt(6)=='A')
                a1.add(key);

            else if(key.charAt(len-2)=='P' && key.charAt(6)=='P'){
                if(key.startsWith("12"))
                    a4.add(key);
                else
                    a2.add(key);
            }
            else
                a3.add(key);
        }
        Collections.sort(a1);
        Collections.sort(a2);
        Collections.sort(a3);
        Collections.sort(a4);

        a1.addAll(a3);
        a1.addAll(a4);
        a1.addAll(a2);

        alTimeSlot=a1;

    }

    private void createSpinner(){

        alTimeSlot.add(0, "Choose a time slot");
        ArrayAdapter adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, alTimeSlot) {
            @Override
            public boolean isEnabled(int position) {
                // TODO Auto-generated method stub
                if (position == 0 || alValue.get(position-1)>=maxCustomer) {
                    return false;
                }
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                // TODO Auto-generated method stub
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;
                if (position == 0 || alValue.get(position-1)>=maxCustomer) {
                    mTextView.setTextColor(Color.GRAY);
                } else {
                    mTextView.setTextColor(Color.parseColor("#011627"));
                }
                return mView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(timeSlotInput.this, "Choose a valid Time Slot !", Toast.LENGTH_SHORT).show();
    }
}