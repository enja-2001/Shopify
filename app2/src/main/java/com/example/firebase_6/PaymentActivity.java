package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    Button butMakePayment;

    MyOrder ob;

    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ9876543210abcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Button butNext;
    Button butBack;

    TextView tvWorkerName;
    TextView tvUserName;
    TextView tvUserPhoneNumber;

    TextView tvUserAddress;

    TextView tvUserDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        ob=(MyOrder) getIntent().getSerializableExtra("obj");

        preferences = PreferenceManager.getDefaultSharedPreferences(PaymentActivity.this);
        editor = preferences.edit();

        butNext=(Button)findViewById(R.id.buttonConfirmationNext);
        butBack=(Button)findViewById(R.id.buttonConfirmationBack);

        tvWorkerName=(TextView)findViewById(R.id.tvConfirmationWorkerName);
        tvUserName=(TextView)findViewById(R.id.tvConfirmationUserName);
        tvUserPhoneNumber=(TextView)findViewById(R.id.tvConfirmationUserPhoneNumber);
        tvUserAddress=(TextView)findViewById(R.id.tvConfirmationUserAddress);

        tvUserDateTime=(TextView)findViewById(R.id.tvConfirmationUserDateTime);

        tvUserName.setText(ob.name);
        tvUserPhoneNumber.setText(ob.phoneNumber);
        tvUserAddress.setText(ob.address);
        tvUserDateTime.setText("Our worker will reach you on "+ob.scheduledDate+"\n at "+ob.scheduledTime);

        FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

//                    holder.tvOngoingProfession.setText(documentSnapshot.getString(""));
                    tvWorkerName.setText(documentSnapshot.getString("Profession")+": "+documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name"));
//                    Picasso.get().load(documentSnapshot.getString("Photo")).into(holder.ivReviews);

                } else {
                    Toast.makeText(PaymentActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });








        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ob.paymentMethod="Offline";
                ob.paymentStatus="Due";
                //------ob.workStatus="Yet to start";
                ob.workStatus="Ongoing";




                Calendar calendar=Calendar.getInstance();

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM,yyyy");
                ob.orderPlacingDate=simpleDateFormat.format(calendar.getTime());

                SimpleDateFormat newSimpleDateFormat=new SimpleDateFormat("HH:mm");

                int hr=Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(0,2));
                int min=Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(3));

                String time="";

                if(hr>=12){
                    time="PM";
                }
                else{
                    time="AM";
                }

                if(hr>=13)
                    hr=hr-12;
                if(hr==0)
                    hr=12;

                time=hr+":"+min+" "+time;

                ob.orderPlacingTime=time;

                //-----ob.startingDate="";
                ob.startingDate=ob.orderPlacingDate;
//---------                ob.startingTime="";
                ob.startingTime=ob.orderPlacingTime;

                //Toast.makeText(PaymentActivity.this, ob.startingDate, Toast.LENGTH_SHORT).show();


                int len=20;

                StringBuilder sb = new StringBuilder(len);
                for( int i = 0; i < len; i++ )
                    sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );

                ob.orderId=sb.toString();

                Map<String, Object> mymap = new HashMap<>();

                mymap.put("Order Address", ob.address);
                mymap.put("Order User Name", ob.name);
                mymap.put("Order ID", ob.orderId);
                mymap.put("Order Placing Date", ob.orderPlacingDate);
                mymap.put("Order Placing Time", ob.orderPlacingTime);
                mymap.put("Payment Method",ob.paymentMethod);

                mymap.put("Payment Status", ob.paymentStatus);
                mymap.put("Order Phone Number", ob.phoneNumber);
                mymap.put("Scheduled Date", ob.scheduledDate);
                mymap.put("Scheduled Time", ob.scheduledTime);
                mymap.put("User Phone Number", ob.userPhoneNumber);
                mymap.put("Worker Phone Number", ob.workerPhoneNumber);
                mymap.put("Work Status",ob.workStatus);
                mymap.put("Starting Date",ob.startingDate);
                mymap.put("Starting Time",ob.startingTime);
                mymap.put("Ending Date",ob.endingDate);
                mymap.put("Ending Time",ob.endingTime);
                mymap.put("Rating",ob.rating);

                mymap.put("Review",ob.review);
                mymap.put("Timestamp", FieldValue.serverTimestamp());






                FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).set(mymap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){


                            int notificationBadgeCount=0;
                            String strNotificationBadge=preferences.getString("notificationBadgeCount", "");

                            if(strNotificationBadge=="")
                                notificationBadgeCount=0;
                            else
                                notificationBadgeCount=Integer.parseInt(strNotificationBadge);

                            notificationBadgeCount++;


                            editor.putString("notificationBadgeCount", Integer.toString(notificationBadgeCount));
                            editor.apply();


                            Intent intent=new Intent(PaymentActivity.this,PaymentSuccessfulActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(PaymentActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaymentActivity.this,DateTimeActivity.class);
                intent.putExtra("obj",ob);

                startActivity(intent);
            }
        });
    }
}