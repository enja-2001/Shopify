package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HireActivity extends AppCompatActivity {

    Button butHire;
    Button butGetHired;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);

        butHire=(Button)findViewById(R.id.butHire);
        butGetHired=(Button)findViewById(R.id.butGetHired);

        phoneNumber=getIntent().getStringExtra("data");

        butHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HireActivity.this,com.example.firebase_6.NameAddressActivity.class);
                intent.putExtra("data",phoneNumber);
                startActivity(intent);
            }
        });

        butGetHired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(HireActivity.this,com.example.firebase_6.AddWorkersActivity.class);
                intent.putExtra("data",phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
