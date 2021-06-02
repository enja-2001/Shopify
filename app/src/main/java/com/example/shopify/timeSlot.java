package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import developers.mobile.abt.FirebaseAbt;

public class timeSlot extends AppCompatActivity {

    private String open;
    private String close;
    private DatabaseReference shop;
    public HashMap<String, String> time = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);


//        FirebaseFirestore.getInstance().collection("Shops").document("9999999999").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    open = documentSnapshot.getString("Opening time");
//                    close = documentSnapshot.getString("Closing time");
//
//                    addTimeSlots(open, close);
//
//                } else {
//                    Toast.makeText(timeSlot.this, "Oops! Some error has occured!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        String test="3333333333";
        getOngoingOrder(test);

    }

    public String convert12HourTo24HourTime(String s) { //input format "hh:mm a"  output format "hh:mm"
        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        char a = s.charAt(6);

        if (a == 'P' && hr != 12)
            hr = hr + 12;
        else if (a == 'A' && hr == 12)
            hr = 0;

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin);

    }

    public String convert24HourTo12HourTime(String s) { //input format "hh:mm"  output format "hh:mm a"

        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        String a="";

        if(hr==12){
            a="PM";
        }
        else if(hr==0){
            hr=12;
            a="AM";
        }
        else if(hr>=13){
            hr=hr-12;
            a="PM";
        }
        else if(hr<=12){
            a="AM";
        }

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin+" "+a);
    }

    private void addTimeSlots(String s1,String s2){
        String t1 = convert12HourTo24HourTime(s1);
        String t2 = convert12HourTo24HourTime(s2);

        int hrOpen = Integer.parseInt(t1.substring(0, 2));
        int hrClose = Integer.parseInt(t2.substring(0, 2));
        int minOpen = Integer.parseInt(t1.substring(3, 5));
        int minClose = Integer.parseInt(t2.substring(3, 5));

        HashMap<String, Integer> hashMap = new HashMap<>();
        int hr = hrOpen;
        int min = minOpen;
        String temp1 =s1;
        String temp2="";

        while (hr < hrClose) {
            min = min + 15;
            if (min >= 60) {
                min = min - 60;
                hr = hr + 1;
            }

            String strhr = "";
            if (hr >= 0 && hr <= 9)
                strhr = "0" + hr;
            else
                strhr = "" + hr;

            String strmin = "";
            if (min >= 0 && min <= 9)
                strmin = "0" + min;
            else
                strmin = "" + min;

            temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
            hashMap.put(temp1+" - "+temp2,0);
            temp1=temp2;

        }
        //   hr=hrClose
        while (min < minClose) {
            min = min + 15;

            String strhr = "";
            if (hr >= 0 && hr <= 9)
                strhr = "0" + hr;
            else
                strhr = "" + hr;

            String strmin = "";
            if (min >= 0 && min <= 9)
                strmin = "0" + min;
            else
                strmin = "" + min;

            temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
            hashMap.put(temp1+" - "+temp2,0);
            temp1=temp2;
        }

        FirebaseFirestore.getInstance().collection("TimeSlots").document("4444444444").set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(timeSlot.this, " Timeslots successfully added to database!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(timeSlot.this, "Error occured!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getOngoingOrder(String ph){

        ArrayList<OngoingOrderModel> alOngoing=new ArrayList<>();

        FirebaseFirestore.getInstance().collection("Ongoing Orders").whereEqualTo("Shop phone number",ph).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        String orderId=documentSnapshot.getId();
                        String date=documentSnapshot.getString("Date");
                        String totalPrice=documentSnapshot.getString("Total price");
                        String remainingPayment=documentSnapshot.getString("Remaining payment");
                        String shopAddress=documentSnapshot.getString("Shop Address");
                        String shopName=documentSnapshot.getString("Shop Name");
                        String shopPhoneNumber=documentSnapshot.getString("Shop phone number");
                        String timeSlot=documentSnapshot.getString("Time slot");
                        String userPhoneNumber=documentSnapshot.getString("User phone number");
                        ArrayList<HashMap<String,String>> al=(ArrayList<HashMap<String,String>>)documentSnapshot.get("Order list");


                        int len=al.size();
                        int i=0;
                        HashMap<String,String> hashMap=new HashMap<>();
                        ArrayList<OrderNode> alOrderNode=new ArrayList<>();

                        for(i=0;i<=len-1;i++){
                            hashMap=al.get(i);
                            String nm = hashMap.get("subCategory");
                            String quantity=hashMap.get("quantity");
                            String price=hashMap.get("price");

                            if(price.isEmpty())
                                price="100";

                            OrderNode ob=new OrderNode(nm,"",price,"",quantity);
                            alOrderNode.add(ob);
                        }

                        OngoingOrderModel ongoingOrderModel=new OngoingOrderModel(totalPrice,remainingPayment,orderId,shopName,alOrderNode,timeSlot,shopPhoneNumber,date,shopAddress);
                        alOngoing.add(ongoingOrderModel);
                    }

                    ///////// just for fun testing
                    ArrayList<OrderNode> A=alOngoing.get(0).al;
                    for(OrderNode ob:A)
                        Log.d("here",""+ob.subCategory+" - "+ob.price);
                }
                else{
                    Toast.makeText(timeSlot.this, "Oops! Some error occured...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}