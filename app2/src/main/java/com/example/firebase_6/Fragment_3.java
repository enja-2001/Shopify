package com.example.firebase_6;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_3 extends Fragment {

 ImageView iv;
 TextView tvName;
 TextView tvPhoneNumber;
 TextView tvEmailId;

 TextView butView1;
 TextView butView2;
 ImageView editBut;

 FirebaseAuth ob;
 FirebaseFirestore fstore;

 TextView tvNotificationEmpty;
 ImageView ivNotificationEmpty;
 RecyclerView recyclerView;

 ArrayList<MyOrder> al;
 String phoneNumber;

 RecyclerViewNotificationAdapter adapter;
 ProgressBar progressBar;




 public Fragment_3() {
  // Required empty public constructor
 }


 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {


  View view=inflater.inflate(R.layout.fragment_3, container, false);

  tvNotificationEmpty=(TextView)view.findViewById(R.id.tvNotificationEmpty);
  ivNotificationEmpty=(ImageView)view.findViewById(R.id.ivNotificationEmpty);
  recyclerView=(RecyclerView)view.findViewById(R.id.notificationRecyclerView);

  recyclerView.setVisibility(View.GONE);
  tvNotificationEmpty.setVisibility(View.GONE);
  ivNotificationEmpty.setVisibility(View.GONE);


  al = new ArrayList<>();

  progressBar = (ProgressBar) view.findViewById(R.id.progressBarReviews);
  progressBar.setVisibility(View.VISIBLE);

  recyclerView.setHasFixedSize(true);
  recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
  recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

  phoneNumber = preferences.getString("Phone Number", null);


  FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("User Phone Number","+91"+phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
   @Override
   public void onComplete(@NonNull Task<QuerySnapshot> task) {
    progressBar.setVisibility(View.GONE);


    if(task.isSuccessful()){
     for(DocumentSnapshot documentSnapshot:task.getResult()){
      String orderId=documentSnapshot.getString("Order ID");
      String userPhoneNumber=documentSnapshot.getString("User Phone Number");
      String workerPhoneNumber=documentSnapshot.getString("Worker Phone Number");
      String name=documentSnapshot.getString("Order User Name");
      String address=documentSnapshot.getString("Order Address");
      String orderPhoneNumber=documentSnapshot.getString("Order Phone Number");
      String scheduledDate=documentSnapshot.getString("Scheduled Date");
      String scheduledTime=documentSnapshot.getString("Scheduled Time");
      String orderPlacingDate=documentSnapshot.getString("Order Placing Date");
      String orderPlacingTime=documentSnapshot.getString("Order Placing Time");
      String paymentMethod=documentSnapshot.getString("Payment Method");
      String paymentStatus=documentSnapshot.getString("Payment Status");
      String workStatus=documentSnapshot.getString("Work Status");
      String startingDate=documentSnapshot.getString("Starting Date");
      String startingTime=documentSnapshot.getString("Starting Time");
      String endingDate=documentSnapshot.getString("Ending Date");

      String endingTime=documentSnapshot.getString("Ending Time");
      String rating=documentSnapshot.getString("Rating");
      String review=documentSnapshot.getString("Review");



      MyOrder ob=new MyOrder(review,rating,startingDate,startingTime,endingDate,endingTime,workStatus,paymentMethod,paymentStatus,orderPlacingDate,orderPlacingTime,orderId,userPhoneNumber,workerPhoneNumber,name,address,orderPhoneNumber,scheduledDate,scheduledTime);
      al.add(ob);


     }

     if(al.size()>0) {
      recyclerView.setVisibility(View.VISIBLE);
      tvNotificationEmpty.setVisibility(View.GONE);
      ivNotificationEmpty.setVisibility(View.GONE);
      adapter = new RecyclerViewNotificationAdapter(al, getContext());
      recyclerView.setAdapter(adapter);
     }
     else{
      recyclerView.setVisibility(View.GONE);
      tvNotificationEmpty.setVisibility(View.VISIBLE);
      ivNotificationEmpty.setVisibility(View.VISIBLE);
     }



    }
    else{
     Toast.makeText(getContext(), "Error occured!", Toast.LENGTH_SHORT).show();

    }

   }
  });




  return view;
 }


}
