package com.example.firebase_6;

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


      /*  butTimer.setText("START");
        butTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button butTimer = (Button) v;
                //if (butTimer.getText().equals("STOP")) {
                 // if(millis==0){
                   // timerHandler.removeCallbacks(timerRunnable);
                    //butTimer.setText("START");
                //} else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    butTimer.setText("STOP");
                //}
            }
        });*/

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OTPVerificationActivity.this,com.example.firebase_6.OTPActivity.class);
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
                            addUserToDatabase();
                           // Intent intent=new Intent(OTPVerificationActivity.this,com.example.firebase_6.HomeActivity.class);
                           // startActivity(intent);

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

    private void addUserToDatabase(){
        //DocumentReference docref=FirebaseFirestore.getInstance().collection("Users").document("+91"+phoneNumber);

        /*docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if (task.getResult() != null) {
                        //user exists
                        Intent intent=new Intent(OTPVerificationActivity.this,com.example.firebase_6.HomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        //user does not exist

                        Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.NameAddressActivity.class);
                        intent.putExtra(phoneNumber, "data");
                        startActivity(intent);
                    }


                }
                else
                    Toast.makeText(OTPVerificationActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

        }
    });*/

        /*CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");
        Query query = usersRef.whereEqualTo("username",phoneNumber);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        String user = documentSnapshot.getString("username");

                        if(user.equals("+91"+phoneNumber)){
                            Toast.makeText(OTPVerificationActivity.this, "Username exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if(task.getResult().size() == 0 ){
                    Toast.makeText(OTPVerificationActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        //You can store new user information here

                }
            }
        });*/

        List al=new ArrayList<String>();

       /* DocumentReference docref= FirebaseFirestore.getInstance().collection("Users").document();
        HashMap<String,Object> map=new HashMap<>();
        map.put("Name",phoneNumber);
        docref.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(OTPVerificationActivity.this, "Task is succesful", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(OTPVerificationActivity.this, "Task failed!", Toast.LENGTH_SHORT).show();

            }

        });*/
       //------ Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.NameAddressActivity.class);
        //-----intent.putExtra("data",phoneNumber);
        //-------startActivity(intent);

        /*Query query=FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Phone number",phoneNumber);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                        //result is guarrented to be non null
                        //user exists
                        Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.HomeActivity.class);
                        startActivity(intent);


                }
                else{
                    //user does not exist
                    //result is guarrented to be null

                    Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.NameAddressActivity.class);
                    intent.putExtra("data",phoneNumber);
                    startActivity(intent);
                    //Toast.makeText(OTPVerificationActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        DocumentReference docref= FirebaseFirestore.getInstance().collection("Users").document("+91"+phoneNumber);
        DocumentReference docrefWorkers= FirebaseFirestore.getInstance().collection("Workers").document("+91"+phoneNumber);

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        //document exists in Users collection
                        insertUserDataIntoSharedPreference();
                        //-----Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.HomeActivity.class);
                        //-----intent.putExtra("data",phoneNumber);
                       //----- startActivity(intent);
                    }
                    else{

                        //document doesnot exist in Users collection

                        docrefWorkers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        //document exists in Workers collection
                                        insertWorkerDataIntoSharedPreference();
                                    }
                                    else{
                                        //document exists no where
                                        Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.HireActivity.class);
                                        intent.putExtra("data",phoneNumber);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });


                    }
                }
                else{
                    Toast.makeText(OTPVerificationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });



        /*FirebaseFirestore.getInstance().collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Toast.makeText(OTPVerificationActivity.this, "Arrraylist is empty", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<String> types = documentSnapshots.toObjects(String.class);

                            // Add all to your list
                            al.addAll(types);
                            Toast.makeText(OTPVerificationActivity.this, "onSuccess arrraylist found", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "onSuccess: " + mArrayList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();

                    }
                });*/
    }


   /* @Override
    public void onPause() {
        super.onPause();
        //timerHandler.removeCallbacks(timerRunnable);
        Button butTimer = (Button)findViewById(R.id.butTimer);
        butTimer.setText("START");
    }*/

   public void insertUserDataIntoSharedPreference(){
       progressDialog.setTitle("Storing data");
       progressDialog.setMessage("Please wait until the data is stored successfully" );
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.show();

       DocumentReference docref= FirebaseFirestore.getInstance().collection("Users").document("+91"+phoneNumber);
       docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot documentSnapshot=task.getResult();

                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                   SharedPreferences.Editor editor = preferences.edit();

                   editor.putString("Phone Number", phoneNumber);
                   editor.putString("First Name",documentSnapshot.getString("First name"));
                   editor.putString("Last Name", documentSnapshot.getString("Last name"));
                   editor.putString("Image",documentSnapshot.getString("Photo"));

                   editor.apply();

                   progressDialog.dismiss();

                   Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.HomeActivity.class);
                   intent.putExtra("data",phoneNumber);
                   startActivity(intent);


               }
               else {
                   progressDialog.dismiss();
                   Toast.makeText(OTPVerificationActivity.this, "No document found!", Toast.LENGTH_SHORT).show();
               }

           }
       });



   }


    public void insertWorkerDataIntoSharedPreference() {
        progressDialog.setTitle("Storing data");
        progressDialog.setMessage("Please wait until the data is stored successfully");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DocumentReference docref = FirebaseFirestore.getInstance().collection("Workers").document("+91" + phoneNumber);
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();


                    String first = documentSnapshot.getString("First name");
                    String last = documentSnapshot.getString("Last name");

                    String addr = documentSnapshot.getString("Address");

                    String dob = documentSnapshot.getString("Date of birth");
                    String prof = documentSnapshot.getString("Profession");


                    String exp = documentSnapshot.getString("Working since");

                    String gen = documentSnapshot.getString("Gender");

                    String rate = documentSnapshot.getString("Rating");

                    String image = documentSnapshot.getString("Photo");



                    if (first=="" || last==""|| addr=="" || image=="" || prof=="" || gen =="" || dob=="" || exp=="") {
                        progressDialog.dismiss();

                        Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.AddWorkersActivity.class);
                        intent.putExtra("data", phoneNumber);
                        startActivity(intent);

                    }
                    else {


                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();


                        editor.putString("Phone Number", phoneNumber);
                        editor.putString("First Name", first);
                        editor.putString("Last Name", last);
                        editor.putString("Image", image);
                        editor.putString("Address", addr);
                        editor.putString("Date of birth", dob);
                        editor.putString("Gender", gen);
                        editor.putString("Profession", prof);
                        editor.putString("Working since", exp);
                        editor.putString("Rating", rate);

                        editor.apply();

                        progressDialog.dismiss();

                        Intent intent = new Intent(OTPVerificationActivity.this, com.example.firebase_6.HomeActivity.class);
                        intent.putExtra("data", phoneNumber);
                        startActivity(intent);


                    }
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(OTPVerificationActivity.this, "No document found!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

        public void onBackPressed() {
        mFirebaseAuth.signOut();
        finish();
    }




}
