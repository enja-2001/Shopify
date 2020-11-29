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

import java.util.HashMap;
import java.util.Map;

import developers.mobile.abt.FirebaseAbt;

public class timeSlot extends AppCompatActivity {

    private String open;
    private String close;
    private DatabaseReference shop;
    public HashMap<String, String> time = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);


        FirebaseFirestore.getInstance().collection("Shops").document("9999999999").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    open=documentSnapshot.getString("Opening time");
                    close=documentSnapshot.getString("Closing time");

                    addTimeSlots(open,close);

                }
                else{
                    Toast.makeText(timeSlot.this, "Oops! Some error has occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });




//        String hro = open.substring(0, open.indexOf(':'));
//        String mo = open.substring(open.indexOf(':')+1, open.indexOf(' '));
//        String hre = close.substring(0, close.indexOf(':'));
//        String me = close.substring(close.indexOf(':')+1, close.indexOf(' '));
//        int hr, m;
//        String t;
//        hr = Integer.parseInt(hro);
//        m = Integer.parseInt(mo);
//        String sl[] = {"0", "15", "30", "45", "0"};
//        while (true)
//        {
//            if(Integer.toString(hr).equals(hre))
//            {
//                if (Integer.toString(m).equals(me))
//                    break;
//            }
//            m = m+15;
//            if(m > 60)
//            {
//                hr = hr++;
//                m = m-60;
//            }
//            t = Integer.toString(hr) + ":" + Integer.toString(m);
//            time.put(t, "0");
//        }
//        shop = FirebaseDatabase.getInstance().getReference();
//        shop.child("Null").push().setValue(time);
//        FirebaseFirestore.getInstance().collection("timeSlots").document("1111111111").set(time);
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

        if(hr>=13){
            hr=hr-12;
            a="PM";
        }
        else if(hr==0){
            hr=12;
            a="AM";
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
        }

        FirebaseFirestore.getInstance().collection("TimeSlots").document("9999999999").set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}