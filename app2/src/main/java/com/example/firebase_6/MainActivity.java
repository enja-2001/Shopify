package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et11;
    EditText et12;
    EditText et13;
    EditText et14;
    Button but11;
    TextView tv11;
    ProgressBar pb11;

    FirebaseAuth ob;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et11=(EditText)findViewById(R.id.et11);
        et12=(EditText)findViewById(R.id.et12);
        et13=(EditText)findViewById(R.id.et13);
        et14=(EditText)findViewById(R.id.et14);
        but11=(Button) findViewById(R.id.but11);
        tv11=(TextView) findViewById(R.id.tv11);
        pb11=(ProgressBar)findViewById(R.id.pb11);

        pb11.setIndeterminate(true);
        pb11.setVisibility(View.GONE);

        ob=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();


        but11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1=et11.getText().toString().trim();
                final String s2=et12.getText().toString();
                final String s3=et13.getText().toString().trim();
                final String s4=et14.getText().toString().trim();

                if(s3.isEmpty()){
                    et13.setError("Please enter a valid name");
                    et13.requestFocus();
                }

                else if(s4.isEmpty()){
                    et14.setError("Please enter a valid phone number");
                    et14.requestFocus();
                }

                else if(s1.isEmpty()) {
                    et11.setError("Please enter a valid email id");
                    et11.requestFocus();
                }

                else if(s2.isEmpty()) {
                    et12.setError("Please enter a valid password");
                    et12.requestFocus();
                }
                else if(s1.isEmpty() && s2.isEmpty() && s3.isEmpty() && s4.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty()) {

                    pb11.setVisibility(View.VISIBLE);
                    tv11.setVisibility(View.GONE);

                    ob.createUserWithEmailAndPassword(s1, s2).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String userid=ob.getCurrentUser().getUid();
                                DocumentReference docref=fstore.collection("MyUsers").document(userid);

                                Map<String,Object> mymap=new HashMap<>();
                                mymap.put("Name",s3);
                                mymap.put("Phone number",s4);
                                mymap.put("Email id",s1);
                                mymap.put("Password",s2);

                                docref.set(mymap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Successfully user profile created", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Intent ob2 = new Intent(MainActivity.this, com.example.firebase_6.HomeActivity.class);
                                startActivity(ob2);
                            } else
                                Toast.makeText(MainActivity.this, "Error occured!User not registered!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                    Toast.makeText(MainActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

            }
        });


        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ob3=new Intent(MainActivity.this,com.example.firebase_6.LoginActivity.class);
                startActivity(ob3);

            }
        });


    }
}
