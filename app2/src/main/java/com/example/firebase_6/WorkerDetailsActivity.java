package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkerDetailsActivity extends AppCompatActivity {

    public ImageView iv;
    public TextView tvName;
    public TextView tvRating;
    public TextView tvGender;

    public TextView tvAge;

    public TextView tvProfession;

    public TextView tvWorkingSince;

    public TextView totalRatings;

    Button butWorkerDetails;

    RecyclerView recyclerView;

    ArrayList<MyOrder> al;
    String phoneNumber;

    TextView tvNotificationEmpty;


    RecyclerViewWorkerReviewsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);


        worker ob = (worker) getIntent().getSerializableExtra("obj");


        iv = (ImageView) findViewById(R.id.ivWorkerDetails);
        tvName = (TextView) findViewById(R.id.tvNameWorkerDetails);
        tvRating = (TextView) findViewById(R.id.tvRatingWorkerDetails);
        tvGender = (TextView) findViewById(R.id.tvGenderWorkerDetails);

        tvAge = (TextView) findViewById(R.id.tvAgeWorkerDetails);
        totalRatings = (TextView) findViewById(R.id.tvTotalRatingsWorkerDetails);

        tvProfession = (TextView) findViewById(R.id.tvProfessionWorkerDetails);

        tvWorkingSince = (TextView) findViewById(R.id.tvWorkingSinceWorkerDetails);
        butWorkerDetails = (Button) findViewById(R.id.buttonWorkerDetails);

        tvNotificationEmpty = (TextView) findViewById(R.id.tvWorkerReviewsEmpty);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWorkerDetails);

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);


        tvName.setText(ob.getName());
        tvRating.setText(ob.getRating());
        Picasso.get().load(ob.uri).into(iv);
        tvGender.setText(ob.gender);
        tvAge.setText(ob.age);

        tvProfession.setText(ob.profession);

        tvWorkingSince.setText(ob.working_since);

        butWorkerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerDetailsActivity.this, com.example.firebase_6.PlaceOrderAddress.class);
                startActivity(intent);
                //payUsingUpi("1");
            }
        });

        al = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkerDetailsActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(WorkerDetailsActivity.this);

        phoneNumber = preferences.getString("workerPhoneNumber", null);

//        Toast.makeText(this, ""+phoneNumber, Toast.LENGTH_SHORT).show();


        FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("Worker Phone Number", "+91" + phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String orderId = documentSnapshot.getString("Order ID");
                        String userPhoneNumber = documentSnapshot.getString("User Phone Number");
                        String workerPhoneNumber = documentSnapshot.getString("Worker Phone Number");
                        String name = documentSnapshot.getString("Order User Name");
                        String address = documentSnapshot.getString("Order Address");
                        String orderPhoneNumber = documentSnapshot.getString("Order Phone Number");
                        String scheduledDate = documentSnapshot.getString("Scheduled Date");
                        String scheduledTime = documentSnapshot.getString("Scheduled Time");
                        String orderPlacingDate = documentSnapshot.getString("Order Placing Date");
                        String orderPlacingTime = documentSnapshot.getString("Order Placing Time");
                        String paymentMethod = documentSnapshot.getString("Payment Method");
                        String paymentStatus = documentSnapshot.getString("Payment Status");
                        String workStatus = documentSnapshot.getString("Work Status");
                        String startingDate = documentSnapshot.getString("Starting Date");
                        String startingTime = documentSnapshot.getString("Starting Time");
                        String endingDate = documentSnapshot.getString("Ending Date");

                        String endingTime = documentSnapshot.getString("Ending Time");
                        String rating = documentSnapshot.getString("Rating");
                        String review = documentSnapshot.getString("Review");

                        if(!rating.equals("")) {

                            MyOrder ob = new MyOrder(review, rating, startingDate, startingTime, endingDate, endingTime, workStatus, paymentMethod, paymentStatus, orderPlacingDate, orderPlacingTime, orderId, userPhoneNumber, workerPhoneNumber, name, address, orderPhoneNumber, scheduledDate, scheduledTime);
                            al.add(ob);
                        }


                    }

                    if (al.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNotificationEmpty.setVisibility(View.GONE);
                        adapter = new RecyclerViewWorkerReviewsAdapter(al, WorkerDetailsActivity.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvNotificationEmpty.setVisibility(View.VISIBLE);
                    }


                } else {
                    Toast.makeText(WorkerDetailsActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        FirebaseFirestore.getInstance().collection("Workers").document("+91"+phoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    String rating = documentSnapshot.getString("Rating Count");
                    String review = documentSnapshot.getString("Review Count");

                    if(rating.equals(""))
                        rating="0.0";
                    if(review.equals(""))
                        review="0.0";

//                    Toast.makeText(WorkerDetailsActivity.this, rating+" "+review, Toast.LENGTH_SHORT).show();
                    totalRatings.setText("From " + rating.substring(0, rating.length() - 2) + " ratings and " + review.substring(0, review.length() - 2) + " reviews");



                } else
                    Toast.makeText(WorkerDetailsActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void payUsingUpi(String amt) {
        Uri uri=Uri.parse("upi://pay").buildUpon().appendQueryParameter("pa","8927782759@upi").appendQueryParameter("pn","Md. Enjamum Hossain").appendQueryParameter("tn","Testing").appendQueryParameter("am",amt).appendQueryParameter("cu","INR").build();

        Intent intent=new Intent(Intent.ACTION_VIEW);
        //Intent intent=new Intent();
        intent.setData(uri);

        //intent.setClassName("net.one97", "net.one97.paytm");


        Intent chooser=Intent.createChooser(intent,"Pay with");

        if(chooser.resolveActivity(getPackageManager())!=null)
            startActivityForResult(chooser,10);
        else
            Toast.makeText(this, "No apps found! ", Toast.LENGTH_SHORT).show();

        //startActivityForResult(intent,101);

       /* Intent i = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
        i.setData(uri);
        startActivityForResult(i,101);*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10 && resultCode==RESULT_OK){

            if(data!=null){
                ArrayList<String> al=new ArrayList<>();
                al.add(data.getStringExtra("response"));
                upiPaymentOperation(al);
            }
            else{
                ArrayList<String> al=new ArrayList<>();
                al.add("nothing");
                upiPaymentOperation(al);
            }
        }
        else{
            ArrayList<String> al=new ArrayList<>();
            al.add("nothing");
            upiPaymentOperation(al);
        }
    }

    private void upiPaymentOperation(ArrayList<String> data) {
        if (isConnectionAvailable(WorkerDetailsActivity.this)) {
            String str = data.get(0);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(WorkerDetailsActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(WorkerDetailsActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(WorkerDetailsActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(WorkerDetailsActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}