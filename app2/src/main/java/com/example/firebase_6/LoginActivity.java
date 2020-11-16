package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText et21;
    EditText et22;
    Button but21;
    TextView tv21;
    TextView tv22;
    ProgressBar pb21;

    FirebaseAuth ob1;
    FirebaseAuth.AuthStateListener ob2;
    FirebaseUser ob3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et21=(EditText)findViewById(R.id.et21);
        et22=(EditText)findViewById(R.id.et22);
        but21=(Button) findViewById(R.id.but21);
        tv21=(TextView)findViewById(R.id.tv21);
        tv22=(TextView)findViewById(R.id.tv22);
        pb21=(ProgressBar)findViewById(R.id.pb21);

        pb21.setIndeterminate(true);
        pb21.setVisibility(View.GONE);

        ob1=FirebaseAuth.getInstance();
        ob2=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                ob3=ob1.getCurrentUser();
                if(ob3!=null){
                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent ob4=new Intent(LoginActivity.this,com.example.firebase_6.HomeActivity.class);
                    startActivity(ob4);

                }

            }
        };

        but21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=et21.getText().toString().trim();
                String s2=et22.getText().toString().trim();
                if(s1.isEmpty()) {
                    et21.setError("Please enter a valid email id");
                    et21.requestFocus();
                }

                else if(s2.isEmpty()) {
                    et22.setError("Please enter a valid password");
                    et22.requestFocus();
                }

                else if(s1.isEmpty() && s2.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!s1.isEmpty() && !s2.isEmpty()) {

                    pb21.setVisibility(View.VISIBLE);
                    tv21.setVisibility(View.GONE);
                    tv22.setVisibility(View.GONE);
                    ob1.signInWithEmailAndPassword(s1, s2).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                pb21.setVisibility(View.GONE);
                                Intent ob2 = new Intent(LoginActivity.this, com.example.firebase_6.HomeActivity.class);
                                startActivity(ob2);
                            }
                            else {
                                pb21.setVisibility(View.GONE);
                                tv21.setVisibility(View.VISIBLE);
                                tv22.setVisibility(View.VISIBLE);

                                Toast.makeText(LoginActivity.this, "Error occured!User not registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else
                    Toast.makeText(LoginActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

            }
        });

        tv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ob4=new Intent(LoginActivity.this,com.example.firebase_6.MainActivity.class);
                startActivity(ob4);
            }
        });

        tv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ob4=new Intent(LoginActivity.this,com.example.firebase_6.ForgotPasswordActivity.class);
                startActivity(ob4);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ob1.addAuthStateListener(ob2);
    }
}
