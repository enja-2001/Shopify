package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopify.helper.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    Button butVerify;
    EditText etOtp;

    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    EditText et6;

    TextView tvResult;
    TextView tvError;
    TextView tvResendOTP;
    TextView tvPhoneNumber;
    ImageView ivEdit;

    String otp="";
    private ProgressDialog progressDialog;


    public PhoneAuthProvider.ForceResendingToken mResendToken;
    public String mVerificationId;
    public String phoneNumber;

    long startTime;
    long millis=25000;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            millis = 25000-(System.currentTimeMillis()-startTime);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            tvResult.setText(String.format("%02d:%02d", minutes, seconds));
            if(millis>0) {
                timerHandler.postDelayed(this, 500);
            }
            else{
                tvResult.setVisibility(View.GONE);
                tvResendOTP.setVisibility(View.VISIBLE);

            }
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);


        butVerify=(Button)findViewById(R.id.butVerify);
        ivEdit=(ImageView)findViewById(R.id.ivOTPEdit);

        //etOtp=(EditText)findViewById(R.id.etOtp);


        tvResult=(TextView)findViewById(R.id.tvResult);
        tvError=(TextView)findViewById(R.id.tvError);
        tvResendOTP=(TextView)findViewById(R.id.tvResendOTP);
        tvPhoneNumber=(TextView)findViewById(R.id.tvOTPPhoneNumber);

        tvError.setVisibility(View.GONE);
        tvResult.setVisibility(View.VISIBLE);
        tvResendOTP.setVisibility(View.GONE);
        progressDialog=new ProgressDialog(this);



        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        et5=(EditText)findViewById(R.id.et5);
        et6=(EditText)findViewById(R.id.et6);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);

                if(s.length()==1)
                {
                    et2.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.requestFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);
                if(s.length()==1)
                {
                    et3.requestFocus();
                }
                else if(s.length()==0)
                {
                    et2.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);
                if(s.length()==1)
                {

                    et4.requestFocus();
                }
                else if(s.length()==0)
                {
                    et3.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);
                if(s.length()==1){

                    et5.requestFocus();

                }
                else if(s.length()==0)
                {
                    et4.requestFocus();
                }
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);
                if(s.length()==1)
                {
                    et6.requestFocus();
                }
                else if(s.length()==0)
                {
                    et5.requestFocus();
                }
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvError.setVisibility(View.GONE);
                if(s.length()==1)
                {

                    et6.clearFocus();
                }
                else if(s.length()==0)
                {
                    et6.requestFocus();
                }
            }
        });



        mFirebaseAuth= FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        mVerificationId=getIntent().getStringExtra("verificationId");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        mResendToken=(PhoneAuthProvider.ForceResendingToken)getIntent().getSerializableExtra("token");

        tvPhoneNumber.setText("+91 "+phoneNumber);



        butVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp=et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString();

                if(otp.length()!=6){
                    tvError.setText("Invalid OTP!");
                    tvError.setVisibility(View.VISIBLE);
                }
                else{
                    //tvResult.setVisibility(View.GONE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp);
                    signInWithPhoneAuthCredential(credential);
                }
                //-------addUserToDatabase();
            }
        });



        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendVerificationCode("+91"+phoneNumber,mResendToken);
                //tvResult.setText("+91"+phoneNumber+mResendToken.toString());

            }
        });


        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OTPVerificationActivity.this,com.example.shopify.OTPActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                             SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("Phone Number", phoneNumber);
                            editor.apply();
                            SharedPrefManager.getInstance(OTPVerificationActivity.this).logincust();
                            Intent intent=new Intent(OTPVerificationActivity.this,com.example.shopify.HomeActivity.class);
                             startActivity(intent);

                        } else {
                            Toast.makeText(OTPVerificationActivity.this,"Error occured", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                tvError.setText("Invalid OTP!");
                                tvError.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }


    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                20,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                OTPVerificationActivity.this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callback
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // signInWithPhoneAuthCredential(phoneAuthCredential);
            //Toast.makeText(MainActivity.this, "Verifying code automatically", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            // Toast.makeText(context, "Verification failed!!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mResendToken = forceResendingToken;
            mVerificationId = s;

            tvResendOTP.setVisibility(View.GONE);
            tvResult.setVisibility(View.VISIBLE);
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);



        }
    };


    public void onBackPressed() {
        mFirebaseAuth.signOut();
        finish();
    }
}
