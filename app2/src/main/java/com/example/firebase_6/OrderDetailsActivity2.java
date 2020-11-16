package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderDetailsActivity2 extends AppCompatActivity {

    public ImageView iv;
    public TextView tvName;
    public TextView tvRating;
    public TextView tvGender;

    public TextView tvAge;

    public TextView tvProfession;

    public TextView tvWorkingSince;



    TextView tvOrderId;
    String userPhoneNumber;
    String workerPhoneNumber;
    TextView tvOrderName;
    TextView tvOrderAddress;
    TextView tvOrderPhoneNumber;
    TextView tvScheduledDate;
    TextView tvScheduledTime;
    TextView tvOrderPlacingDate;
    TextView tvOrderPlacingTime;
    TextView tvPaymentMethod;
    TextView tvPaymentStatus;
    TextView tvWorkStatus;

    TextView tvStartingDate;
    TextView tvStartingTime;

    TextView tvEndingDate;
    TextView tvEndingTime;

    TextView tvDays;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);


        MyOrder ob=(MyOrder) getIntent().getSerializableExtra("obj");


        iv=(ImageView)findViewById(R.id.ivWorkerDetails);
        tvName=(TextView)findViewById(R.id.tvNameWorkerDetails);
        tvRating=(TextView)findViewById(R.id.tvRatingWorkerDetails);
        tvGender=(TextView)findViewById(R.id.tvGenderWorkerDetails);

        tvAge=(TextView)findViewById(R.id.tvAgeWorkerDetails);

        tvProfession=(TextView)findViewById(R.id.tvProfessionWorkerDetails);

        tvWorkingSince=(TextView)findViewById(R.id.tvWorkingSinceWorkerDetails);

        tvOrderId=(TextView)findViewById(R.id.tvOrderID);
        tvOrderName=(TextView)findViewById(R.id.tvUserName);
        tvOrderAddress=(TextView)findViewById(R.id.tvAddress);
        tvOrderPhoneNumber=(TextView)findViewById(R.id.tvPhoneNumber);
        tvOrderPlacingDate=(TextView)findViewById(R.id.tvOrderPlacingDate);
        tvOrderPlacingTime=(TextView)findViewById(R.id.tvOrderPlacingTime);
        tvScheduledDate=(TextView)findViewById(R.id.tvScheduledDate);
        tvScheduledTime=(TextView)findViewById(R.id.tvScheduledTime);
        tvPaymentMethod=(TextView)findViewById(R.id.tvPaymentMethod);
        tvPaymentStatus=(TextView)findViewById(R.id.tvPaymentStatus);
        tvStartingDate=(TextView)findViewById(R.id.tvStartingDate);
        tvStartingTime=(TextView)findViewById(R.id.tvStartingTime);

        tvEndingDate=(TextView)findViewById(R.id.tvEndingDate);
        tvEndingTime=(TextView)findViewById(R.id.tvEndingTime);

        tvWorkStatus=(TextView)findViewById(R.id.tvWorkStatus);
        tvDays=(TextView)findViewById(R.id.tvDaysPassed);



        tvOrderId.setText(ob.orderId);
        tvOrderPlacingDate.setText(ob.orderPlacingDate);
        tvOrderPlacingTime.setText(ob.orderPlacingTime);
        tvPaymentMethod.setText(ob.paymentMethod);
        tvPaymentStatus.setText(ob.paymentStatus);
        tvScheduledDate.setText(ob.scheduledDate);
        tvScheduledTime.setText(ob.scheduledTime);
        tvOrderAddress.setText(ob.address);
        tvOrderName.setText(ob.name);
        tvOrderPhoneNumber.setText(ob.phoneNumber);

        tvWorkStatus.setText(ob.workStatus);
        tvStartingDate.setText(ob.startingDate);
        tvStartingTime.setText(ob.startingTime);

        tvEndingDate.setText(ob.endingDate);
        tvEndingTime.setText(ob.endingTime);
//


        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM,yyyy");
        String currentDate=simpleDateFormat.format(calendar.getTime());


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,yyyy");

        try {
            Date currDate = sdf.parse(currentDate);
            Date startDate = sdf.parse(ob.startingDate);

            long diff = currDate.getTime() - startDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = (hours / 24);
            tvDays.setText(""+days);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }











        FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    worker obWorker=new worker(documentSnapshot.getString("Phone number"),(documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name")),documentSnapshot.getString("Rating"),documentSnapshot.getString("Photo"),documentSnapshot.getString("Gender"),documentSnapshot.getString("Date of birth"),documentSnapshot.getString("Reviews"),documentSnapshot.getString("Working since"),documentSnapshot.getString("Profession"));
                    tvAge.setText(obWorker.age);
                    tvName.setText(obWorker.name);

                    tvProfession.setText(obWorker.profession);

                    tvWorkingSince.setText(obWorker.working_since);

                    tvGender.setText(obWorker.gender);
                    Picasso.get().load(obWorker.uri).into(iv);


                }
                else{
                    Toast.makeText(OrderDetailsActivity2.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}