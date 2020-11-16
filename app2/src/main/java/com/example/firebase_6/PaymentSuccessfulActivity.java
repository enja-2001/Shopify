package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentSuccessfulActivity extends AppCompatActivity {

    Button butDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successful);

        butDone=(Button)findViewById(R.id.butDone);

        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentSuccessfulActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}