package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
    Toolbar reviewsToolbar;

    TextView tvNotificationEmpty;
    ImageView ivNotificationEmpty;
    RecyclerView recyclerView;

    ArrayList<MyOrder> al;
    String phoneNumber;

    RecyclerViewReviewsAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        reviewsToolbar = findViewById(R.id.reviewsToolbar);
        setSupportActionBar(reviewsToolbar);
        ActionBar ob = getSupportActionBar();
        // ob.setElevation(50);
        ob.setHomeAsUpIndicator(R.drawable.back);
        ob.setHomeButtonEnabled(true);
        ob.setDisplayShowHomeEnabled(true);
        ob.setDisplayHomeAsUpEnabled(true);
        ob.setTitle("");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);


        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.VISIBLE);


        tvNotificationEmpty = (TextView) findViewById(R.id.tvReviewsEmpty);
        ivNotificationEmpty = (ImageView) findViewById(R.id.ivReviewsEmpty);
        recyclerView = (RecyclerView) findViewById(R.id.reviewsRecyclerView);

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);
        ivNotificationEmpty.setVisibility(View.GONE);


        al = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewsActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ReviewsActivity.this);

        phoneNumber = preferences.getString("Phone Number", null);
//        new MyAsyncTask().execute();



//        new LoadDataForActivity().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



        FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("User Phone Number","+91"+phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

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
                        adapter = new RecyclerViewReviewsAdapter(al, ReviewsActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        tvNotificationEmpty.setVisibility(View.VISIBLE);
                        ivNotificationEmpty.setVisibility(View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);


                }
                else{
                    Toast.makeText(ReviewsActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }

            }
        });

    }

}
