package com.example.firebase_6;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText et;
    Button but;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    public PhoneAuthProvider.ForceResendingToken mResendToken;
    public String mVerificationId;
    public String phoneNumber;

    ProgressBar progressBar;

    ImageView iv;
    TextView tv1;
    TextView tv2;
    TextView tv3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);
        et=(EditText)findViewById(R.id.etOTPPhoneNumber);
        but=(Button)findViewById(R.id.butProceed);


        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.GONE);

        iv=(ImageView)findViewById(R.id.imageView4);

        tv1=(TextView)findViewById(R.id.textView11);
        tv2=(TextView)findViewById(R.id.textView2);

        tv3=(TextView)findViewById(R.id.textView16);







        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str=et.getText().toString().trim();
                if(str.length()!=10){
                    et.setError("Enter a valid phone number");
                    et.requestFocus();
                    return;
                }
                phoneNumber=str;

                progressBar.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                et.setVisibility(View.GONE);






                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        ("+91"+str),        // Phone number to verify
                        20,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        OTPActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


        Toast.makeText(OTPActivity.this, "+91"+phoneNumber, Toast.LENGTH_SHORT).show();
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // signInWithPhoneAuthCredential(phoneAuthCredential);
            //Toast.makeText(MainActivity.this, "Verifying code automatically", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            progressBar.setVisibility(View.GONE);

            iv.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            but.setVisibility(View.VISIBLE);
            et.setVisibility(View.VISIBLE);

            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


//             Toast.makeText(OTPActivity.this, "Verification failed!!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            super.onCodeSent(s, forceResendingToken);
            mResendToken = forceResendingToken;
            mVerificationId = s;

            progressBar.setVisibility(View.GONE);




            Intent intent = new Intent(OTPActivity.this,com.example.firebase_6. OTPVerificationActivity.class);
            intent.putExtra("verificationId",mVerificationId);
            intent.putExtra("phoneNumber",phoneNumber);
            intent.putExtra("token",mResendToken);
            startActivity(intent);


//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent intent = new Intent(OTPActivity.this,com.example.firebase_6. OTPVerificationActivity.class);
//                            intent.putExtra("verificationId",mVerificationId);
//                            intent.putExtra("phoneNumber",phoneNumber);
//                            intent.putExtra("token",mResendToken);
//                            startActivity(intent);
//                        }
//                    },
//                    1000);
        }
    };



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Intent intent=new Intent(OTPActivity.this,com.example.firebase_6.HomeActivity.class);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivity.this, "There was an error verifying OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mFirebaseUser!=null){
            Intent intent=new Intent(this,com.example.firebase_6.HomeActivity.class);
            startActivity(intent);
        }

    }
    public void onBackPressed() {
        mFirebaseAuth.signOut();
        finishAffinity();
    }
}
