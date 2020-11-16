package com.example.firebase_6;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class SplashScreen extends AppCompatActivity {
    ImageView iv;

    private static final int SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        iv=(ImageView)findViewById(R.id.iv);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent ob=new Intent(SplashScreen.this,com.example.firebase_6.OTPActivity.class);
                startActivity(ob);
            }
        },SPLASH_TIME_OUT);


    }
}
