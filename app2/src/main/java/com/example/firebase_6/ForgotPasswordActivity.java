package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText et41;
    Button but41;
    ProgressBar pb;
    FirebaseAuth ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et41=(EditText)findViewById(R.id.et41);
        but41=(Button) findViewById(R.id.but41);
        pb=(ProgressBar)findViewById(R.id.pb);

        ob=FirebaseAuth.getInstance();

        pb.setVisibility(View.GONE);
        pb.setIndeterminate(true);

        but41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=et41.getText().toString().trim();
                if(s.isEmpty()){
                    et41.setError("Please enter a valid email id");
                    et41.requestFocus();
                }
                else{
                    pb.setVisibility(View.VISIBLE);
                    ob.sendPasswordResetEmail(s).addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pb.setVisibility(View.GONE);
                                Toast.makeText(ForgotPasswordActivity.this, "Reset password email sent.", Toast.LENGTH_SHORT).show();
                                Intent ob1=new Intent(ForgotPasswordActivity.this,com.example.firebase_6.LoginActivity.class);
                                startActivity(ob1);
                            }
                            else {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(ForgotPasswordActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                }
            }
        });


    }
}
