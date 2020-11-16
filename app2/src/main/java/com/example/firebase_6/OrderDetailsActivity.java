package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class OrderDetailsActivity extends AppCompatActivity {

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

    TextView tvDays;
    Button butEndWork;

    String rating;
    String review;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


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
        tvWorkStatus=(TextView)findViewById(R.id.tvWorkStatus);
        tvDays=(TextView)findViewById(R.id.tvDaysPassed);

        butEndWork=(Button)findViewById(R.id.butOngoingEndWork);


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




        butEndWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(OrderDetailsActivity.this);
                alertdialog.setTitle("End the work?");

                alertdialog.setMessage("\nOnce ended, the work can not be restarted");

                alertdialog.setCancelable(false);


                alertdialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        final AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                        builder.setTitle("Ratings and Reviews");

// Set up the input
//                        final EditText input = new EditText(OrderDetailsActivity.this);
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
//                        builder.setView(input);

// Set up the buttons

                        View view= LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.rating_reviews_dialog,null);

                        builder.setView(view);
                        builder.setCancelable(false);

                        final EditText etReviews =view.findViewById(R.id.etReviews);
                        final ImageView ivRating1=view.findViewById(R.id.ivRating1);
                        final ImageView ivRating2=view.findViewById(R.id.ivRating2);

                        final ImageView ivRating3=view.findViewById(R.id.ivRating3);

                        final ImageView ivRating4=view.findViewById(R.id.ivRating4);
                        final ImageView ivRating5=view.findViewById(R.id.ivRating5);

                        final TextView tvReviews=view.findViewById(R.id.tvReviews);

                        final ImageView ivRating6=view.findViewById(R.id.ivRating6);
                        final ImageView ivRating7=view.findViewById(R.id.ivRating7);
                        final ImageView ivRating8=view.findViewById(R.id.ivRating8);
                        final ImageView ivRating9=view.findViewById(R.id.ivRating9);
                        final ImageView ivRating10=view.findViewById(R.id.ivRating10);


                        rating="";

                        tvReviews.setVisibility(View.GONE);
                        etReviews.setVisibility(View.GONE);
                        ivRating6.setVisibility(View.GONE);
                        ivRating7.setVisibility(View.GONE);

                        ivRating8.setVisibility(View.GONE);

                        ivRating9.setVisibility(View.GONE);
                        ivRating10.setVisibility(View.GONE);



                        //etReviews.setText("Hi there!");




                        ivRating1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating="1";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating1.setVisibility(View.GONE);
                                ivRating6.setVisibility(View.VISIBLE);



//                                view.findViewById(R.id.etReviews).setVisibility(View.VISIBLE);
//                                view.findViewById(R.id.tvReviews).setVisibility(View.VISIBLE);

//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//
//                                        String m_Text = etReviews.getText().toString();
//                                        Toast.makeText(view.getContext(), rating + " " + m_Text, Toast.LENGTH_SHORT).show();
//                                    }
//                                });

//                                DrawableCompat.setTint(ivRating1.getDrawable(), Color.parseColor("#d32f2f"));


                            }
                        });

                        ivRating2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating="2";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating1.setVisibility(View.GONE);
                                ivRating6.setVisibility(View.VISIBLE);

                                ivRating2.setVisibility(View.GONE);
                                ivRating7.setVisibility(View.VISIBLE);

//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//
//                                        String m_Text = etReviews.getText().toString();
//                                        Toast.makeText(view.getContext(), rating + " " + m_Text, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        });

                        ivRating3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating="3";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);
                                ivRating1.setVisibility(View.GONE);
                                ivRating6.setVisibility(View.VISIBLE);
                                ivRating2.setVisibility(View.GONE);
                                ivRating7.setVisibility(View.VISIBLE);
                                ivRating3.setVisibility(View.GONE);
                                ivRating8.setVisibility(View.VISIBLE);

//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//
//                                        String m_Text = etReviews.getText().toString();
//                                        Toast.makeText(view.getContext(), rating + " " + m_Text, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        });

                        ivRating4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating="4";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating1.setVisibility(View.GONE);
                                ivRating6.setVisibility(View.VISIBLE);
                                ivRating2.setVisibility(View.GONE);
                                ivRating7.setVisibility(View.VISIBLE);
                                ivRating3.setVisibility(View.GONE);
                                ivRating8.setVisibility(View.VISIBLE);

                                ivRating4.setVisibility(View.GONE);
                                ivRating9.setVisibility(View.VISIBLE);


//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//
//                                        String m_Text = etReviews.getText().toString();
//                                        Toast.makeText(view.getContext(), rating + " " + m_Text, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        });

                        ivRating5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating="5";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating1.setVisibility(View.GONE);
                                ivRating6.setVisibility(View.VISIBLE);
                                ivRating2.setVisibility(View.GONE);
                                ivRating7.setVisibility(View.VISIBLE);
                                ivRating3.setVisibility(View.GONE);
                                ivRating8.setVisibility(View.VISIBLE);
                                ivRating4.setVisibility(View.GONE);
                                ivRating9.setVisibility(View.VISIBLE);
                                ivRating5.setVisibility(View.GONE);
                                ivRating10.setVisibility(View.VISIBLE);


//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//
//                                        String m_Text = etReviews.getText().toString();
//                                        Toast.makeText(view.getContext(), rating + " " + m_Text, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        });

                        ivRating9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating = "4";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating10.setVisibility(View.GONE);
                                ivRating5.setVisibility(View.VISIBLE);
                            }
                        });

                        ivRating8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating = "3";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating10.setVisibility(View.GONE);
                                ivRating5.setVisibility(View.VISIBLE);

                                ivRating9.setVisibility(View.GONE);
                                ivRating4.setVisibility(View.VISIBLE);
                            }
                        });

                        ivRating7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating = "2";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating10.setVisibility(View.GONE);
                                ivRating5.setVisibility(View.VISIBLE);

                                ivRating9.setVisibility(View.GONE);
                                ivRating4.setVisibility(View.VISIBLE);
                                ivRating8.setVisibility(View.GONE);
                                ivRating3.setVisibility(View.VISIBLE);
                            }
                        });
                        ivRating6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rating = "1";
                                tvReviews.setVisibility(View.VISIBLE);
                                etReviews.setVisibility(View.VISIBLE);

                                ivRating10.setVisibility(View.GONE);
                                ivRating5.setVisibility(View.VISIBLE);

                                ivRating9.setVisibility(View.GONE);
                                ivRating4.setVisibility(View.VISIBLE);

                                ivRating8.setVisibility(View.GONE);
                                ivRating3.setVisibility(View.VISIBLE);
                                ivRating7.setVisibility(View.GONE);
                                ivRating2.setVisibility(View.VISIBLE);


                            }
                        });
                        builder.setPositiveButton("Ok",null);
                        builder.setNegativeButton("Cancel",null);




                        AlertDialog dialog = builder.create();

                        /*dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            @Override
                            public void onShow(DialogInterface dialog) {
                                if(rating.equals("")) {
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
                                }
                                else{
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
                                }

                            }
                        });*/


                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                Button buttonPositive = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                                buttonPositive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String m_Text = etReviews.getText().toString();

                                        if(ivRating6.getVisibility()==View.GONE)
                                            Toast.makeText(OrderDetailsActivity.this, "Select a valid rating", Toast.LENGTH_SHORT).show();




                                            //if(!rating.equals("")) {
                                        else {

                                            FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Rating", rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(OrderDetailsActivity.this, "Rating updated successfully in Orders collection!", Toast.LENGTH_SHORT).show();

                                                    } else
                                                        Toast.makeText(OrderDetailsActivity.this, "Error occured in updating rating in Orders collection!", Toast.LENGTH_SHORT).show();


                                                }

                                            });


                                            FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        double workerRating;
                                                        double ratingCount;
                                                        if (task.getResult().getString("Rating").isEmpty())
                                                            workerRating = 0.0;
                                                        else
                                                            workerRating = Double.parseDouble(task.getResult().getString("Rating"));

                                                        if (task.getResult().getString("Rating Count").isEmpty())
                                                            ratingCount = 0.0;
                                                        else
                                                            ratingCount = Double.parseDouble(task.getResult().getString("Rating Count"));

                                                        double newRating = (workerRating * ratingCount + Integer.parseInt(rating)) / (ratingCount + 1);
                                                        ratingCount++;


                                                        String newRatingString = "" + newRating;
                                                        FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).update("Rating", newRatingString.substring(0, 3), "Rating Count", "" + ratingCount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(OrderDetailsActivity.this, "Rating updated successfully in Workers collection!", Toast.LENGTH_SHORT).show();

                                                                } else
                                                                    Toast.makeText(OrderDetailsActivity.this, "Error occured in updating rating in Workers collection!", Toast.LENGTH_SHORT).show();


                                                            }


                                                        });

                                                    }
                                                }
                                            });

                                            if (!m_Text.equals("")) {

                                                FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Review", m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(OrderDetailsActivity.this, "Review updated successfully in Orders collection!", Toast.LENGTH_SHORT).show();

                                                        } else
                                                            Toast.makeText(OrderDetailsActivity.this, "Error occured in updating review in Orders collection!", Toast.LENGTH_SHORT).show();


                                                    }

                                                });


                                                FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            double reviewCount;
                                                            if (task.getResult().getString("Review Count").isEmpty())
                                                                reviewCount = 0.0;
                                                            else
                                                                reviewCount = Double.parseDouble(task.getResult().getString("Review Count"));

                                                            reviewCount++;

                                                            FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).update("Review Count", "" + reviewCount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(OrderDetailsActivity.this, "Review updated successfully in Workers collection!", Toast.LENGTH_SHORT).show();

                                                                    } else
                                                                        Toast.makeText(OrderDetailsActivity.this, "Error occured in updating review in Workers collection!", Toast.LENGTH_SHORT).show();


                                                                }


                                                            });

                                                        }
                                                    }
                                                });


                                            }
                                            FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Work Status", "Completed");

                                            Calendar calendar = Calendar.getInstance();

                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,yyyy");
                                            String endingDate = simpleDateFormat.format(calendar.getTime());

                                            SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat("HH:mm");

                                            int hr = Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(0, 2));
                                            int min = Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(3));

                                            String time = "";

                                            if (hr >= 12) {
                                                time = "PM";
                                            } else {
                                                time = "AM";
                                            }

                                            if (hr >= 13)
                                                hr = hr - 12;
                                            if (hr == 0)
                                                hr = 12;

                                            if(min<=9)
                                                time=hr + ":0" + min + " " + time;
                                            else
                                                time = hr + ":" + min + " " + time;

                                            FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Ending Date", endingDate, "Ending Time", time).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                    } else {

                                                    }

                                                }
                                            });
                                            dialog.dismiss();



                                            Intent intent = new Intent(OrderDetailsActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }











                                    }
                                });

//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                       //-- dialog.show();
//                        builder.create().show();


//                        Intent intent=new Intent(OrderDetailsActivity.this, HomeActivity.class);
//                        startActivity(intent);



                                Button buttonNegative = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);

                                buttonNegative.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();

                                    }
                                });


                            }
                        });
                        dialog.show();


//----------------------------------------------------------

//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//
//                                    String m_Text = etReviews.getText().toString();
//
//                                    if(ivRating6.getVisibility()==View.GONE)
//                                        Toast.makeText(OrderDetailsActivity.this, "Select a valid rating", Toast.LENGTH_SHORT).show();
//
//
//
//
//                                    //if(!rating.equals("")) {
//                                    else {
//
//                                        FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Rating", rating).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(OrderDetailsActivity.this, "Rating updated successfully in Orders collection!", Toast.LENGTH_SHORT).show();
//
//                                                } else
//                                                    Toast.makeText(OrderDetailsActivity.this, "Error occured in updating rating in Orders collection!", Toast.LENGTH_SHORT).show();
//
//
//                                            }
//
//                                        });
//
//
//                                        FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                if (task.isSuccessful()) {
//
//                                                    double workerRating;
//                                                    double ratingCount;
//                                                    if (task.getResult().getString("Rating").isEmpty())
//                                                        workerRating = 0.0;
//                                                    else
//                                                        workerRating = Double.parseDouble(task.getResult().getString("Rating"));
//
//                                                    if (task.getResult().getString("Rating Count").isEmpty())
//                                                        ratingCount = 0.0;
//                                                    else
//                                                        ratingCount = Double.parseDouble(task.getResult().getString("Rating Count"));
//
//                                                    double newRating = (workerRating * ratingCount + Integer.parseInt(rating)) / (ratingCount + 1);
//                                                    ratingCount++;
//
//
//                                                    String newRatingString = "" + newRating;
//                                                    FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).update("Rating", newRatingString.substring(0, 3), "Rating Count", "" + ratingCount).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if (task.isSuccessful()) {
//                                                                Toast.makeText(OrderDetailsActivity.this, "Rating updated successfully in Workers collection!", Toast.LENGTH_SHORT).show();
//
//                                                            } else
//                                                                Toast.makeText(OrderDetailsActivity.this, "Error occured in updating rating in Workers collection!", Toast.LENGTH_SHORT).show();
//
//
//                                                        }
//
//
//                                                    });
//
//                                                }
//                                            }
//                                        });
//
//                                        if (!m_Text.equals("")) {
//
//                                            FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Review", m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(OrderDetailsActivity.this, "Review updated successfully in Orders collection!", Toast.LENGTH_SHORT).show();
//
//                                                    } else
//                                                        Toast.makeText(OrderDetailsActivity.this, "Error occured in updating review in Orders collection!", Toast.LENGTH_SHORT).show();
//
//
//                                                }
//
//                                            });
//
//
//                                            FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//
//                                                        double reviewCount;
//                                                        if (task.getResult().getString("Review Count").isEmpty())
//                                                            reviewCount = 0.0;
//                                                        else
//                                                            reviewCount = Double.parseDouble(task.getResult().getString("Review Count"));
//
//                                                        reviewCount++;
//
//                                                        FirebaseFirestore.getInstance().collection("Workers").document(ob.workerPhoneNumber).update("Review Count", "" + reviewCount).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()) {
//                                                                    Toast.makeText(OrderDetailsActivity.this, "Review updated successfully in Workers collection!", Toast.LENGTH_SHORT).show();
//
//                                                                } else
//                                                                    Toast.makeText(OrderDetailsActivity.this, "Error occured in updating review in Workers collection!", Toast.LENGTH_SHORT).show();
//
//
//                                                            }
//
//
//                                                        });
//
//                                                    }
//                                                }
//                                            });
//
//
//                                        }
//                                        FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Work Status", "Completed");
//
//                                        Calendar calendar = Calendar.getInstance();
//
//                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,yyyy");
//                                        String endingDate = simpleDateFormat.format(calendar.getTime());
//
//                                        SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat("HH:mm");
//
//                                        int hr = Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(0, 2));
//                                        int min = Integer.parseInt(newSimpleDateFormat.format(calendar.getTime()).substring(3));
//
//                                        String time = "";
//
//                                        if (hr >= 12) {
//                                            time = "PM";
//                                        } else {
//                                            time = "AM";
//                                        }
//
//                                        if (hr >= 13)
//                                            hr = hr - 12;
//                                        if (hr == 0)
//                                            hr = 12;
//
//                                        time = hr + ":" + min + " " + time;
//
//                                        FirebaseFirestore.getInstance().collection("Orders").document(ob.orderId).update("Ending Date", endingDate, "Ending Time", time).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//
//                                                } else {
//
//                                                }
//
//                                            }
//                                        });
//
//
//                                        Intent intent = new Intent(OrderDetailsActivity.this, HomeActivity.class);
//                                        startActivity(intent);
//                                    }
//
//
//
//
//
//
//
//
//
//
//
//                                }
//                            });
//
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                       //-- dialog.show();
//                        builder.create().show();


//                        Intent intent=new Intent(OrderDetailsActivity.this, HomeActivity.class);
//                        startActivity(intent);
//-----------------------------------------------------------------------------------


                    }
                });
                alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
                android.app.AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();

            }
        });










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
                    Toast.makeText(OrderDetailsActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}