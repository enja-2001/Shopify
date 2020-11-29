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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class timeSlotInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String timeSlot;
    String ph;
    int maxCustomer;
    ArrayList alTimeSlot;
    ArrayList<Long> alValue;

    timeSlot ts = new timeSlot();
    public Map<String, String> time;
    public Map<String, String> person = new HashMap<>();
    public int maxPeople;

    Button butProceed;
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
        spinner=(Spinner)findViewById(R.id.spinner);

//        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    maxPeople =  Integer.parseInt(doc.getString("maxPeople"));
//                    time = ts.time;
//                    Set<String> keyset = time.keySet();
//                    Iterator<String> itr = keyset.iterator();
//                    while (itr.hasNext()) {
//                        String key = itr.next();
//                        int value = Integer.parseInt(time.get(key));
//                        if (timeSlot.equals(key) && value != maxPeople) {
//                            value += 1;
//                            time.put(key, Integer.toString(value));
//                            person.put(ph, key);
//                        }
//                        if (value == 4) {
//
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(timeSlotInput.this, "Error!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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
                String s=spinner.getSelectedItem().toString();

//                Object arr[]=alOrderList.toArray();

                String userPhoneNumber = preferences.getString("Phone Number", null);


                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("Time slot",s);
                hashMap.put("Order list",alOrderList);
                hashMap.put("User phone number",userPhoneNumber);
                hashMap.put("Shop phone number",ph);
                hashMap.put("User address","");





                FirebaseFirestore.getInstance().collection("Orders").document().set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            updateTimeSlot(s);
                        }
                        else{
                            Toast.makeText(timeSlotInput.this, "Error occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }

    private void populateArrayList(HashMap<String,Object> hashMap){
        String key="";
        long val=0;
        alTimeSlot=new ArrayList<String>();
        alValue=new ArrayList<>();

        for(Map.Entry m:hashMap.entrySet()){
            key=(String)m.getKey();
            val=(long)m.getValue();

            alTimeSlot.add(key);
            alValue.add(val);
        }
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
                    mTextView.setTextColor(Color.BLACK);
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

    }
    private void updateTimeSlot(String s){
        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    long val=task.getResult().getLong(s);
                    val++;
                    FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).update(s,val).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(timeSlotInput.this,OrderSuccessful.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(timeSlotInput.this, "Error occured!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    }
                else{
                    Toast.makeText(timeSlotInput.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}