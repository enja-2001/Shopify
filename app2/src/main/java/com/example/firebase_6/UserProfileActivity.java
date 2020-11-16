package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    CircleImageView iv;
    TextView tvName;
    TextView tvPhoneNumber;
    TextView tvEmailId;

    TextView butView1;
    TextView butView2;
    TextView butView3;

    ImageView editBut;

    String phoneNumber;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        iv = (CircleImageView) findViewById(R.id.ivProfile);
        tvName = findViewById(R.id.tvName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);

        butView1 = findViewById(R.id.butView1);
        butView2 = findViewById(R.id.butView2);
//        butView3 = findViewById(R.id.butView3);

        editBut = findViewById(R.id.editBut);

        iv.setImageResource(R.drawable.header);

        phoneNumber = getIntent().getStringExtra("data");

        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.GONE);



        butView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, com.example.firebase_6.ReviewsActivity.class);
                startActivity(intent);
            }
        });

        butView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, com.example.firebase_6.AddressesActivity.class);
                startActivity(intent);
            }
        });

//        butView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserProfileActivity.this, com.example.firebase_6.FamilyFriendsActivity.class);
//                startActivity(intent);
//            }
//        });

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, com.example.firebase_6.EditProfileActivity.class);
                startActivity(intent);

            }
        });






        /*String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docref=FirebaseFirestore.getInstance().collection("Users").document("+91"+phoneNumber);

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    iv.setImageURI(Uri.parse(documentSnapshot.getString("Photo")));
                    tvName.setText(documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name"));
                    tvPhoneNumber.setText(documentSnapshot.getString("Phone number"));
                }
                else
                    Toast.makeText(UserProfileActivity.this,"No document found!", Toast.LENGTH_SHORT).show();

            }
        });*/

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserProfileActivity.this);

        tvPhoneNumber.setText("+91" + preferences.getString("Phone Number", null));
        tvName.setText(preferences.getString("First Name", null) + " " + preferences.getString("Last Name", null));

        String imgstr = preferences.getString("Image", null);


        if (imgstr != null) {
            iv.setBorderWidth(8);
            iv.setBorderColor(Color.parseColor("#FFFFFF"));

            Picasso.get().load(imgstr).into(iv);



        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(UserProfileActivity.this,HomeActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserProfileActivity.this);

        tvPhoneNumber.setText("+91" + preferences.getString("Phone Number", null));
        tvName.setText(preferences.getString("First Name", null) + " " + preferences.getString("Last Name", null));

        String imgstr = preferences.getString("Image", null);


        if (imgstr != null) {
            iv.setBorderWidth(8);
            iv.setBorderColor(Color.parseColor("#FFFFFF"));

            Picasso.get().load(imgstr).into(iv);



        }
    }
}

