package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {

    HashMap<String,Object> hashMap;
    String ph;
    String timeSlot;
    String totalPrice;
    ArrayList<OrderNode> al;

    Button but3;
    TextView tvAdvanced;
    TextView tvTotal;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        hashMap= (HashMap<String,Object>) getIntent().getSerializableExtra("OrderHashMap");
        ph=(String)hashMap.get("Shop phone number");
        timeSlot=(String)hashMap.get("Time slot");
        totalPrice=(String)hashMap.get("Total price");
        al=(ArrayList<OrderNode>)hashMap.get("Order list");

        but3=findViewById(R.id.button2);
        radioGroup=findViewById(R.id.radioGroup);
        tvTotal=findViewById(R.id.tvTotalPrice);
        tvAdvanced=findViewById(R.id.tvAdvancedPrice);

        int advanced=(int)((40.0/100.0)*Integer.parseInt(totalPrice));
        int remaining=Integer.parseInt(totalPrice)-advanced;

        tvTotal.setText("Total Price - "+totalPrice);
        tvAdvanced.setText("Advanced Payment - "+advanced);

        hashMap.put("Remaining payment",""+remaining);

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = radioGroup.getCheckedRadioButtonId();

                switch(radioId){
                    case R.id.radioButton1:
                        payUsingUpi(""+advanced);
                        break;

                    case R.id.radioButton2:
                        onTransactionSuccessful();
                        break;
                    case R.id.radioButton3:
                        onTransactionFailure();
                        break;

                }
            }
        });
    }

    private void payUsingUpi(String amt) {
        Uri uri = Uri.parse("upi://pay")
                .buildUpon()
                .appendQueryParameter("pa", "8927354558@ybl")
                .appendQueryParameter("pn", "Snehasish Dhar")
                .appendQueryParameter("tn", "")
                .appendQueryParameter("am", amt)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Intent intent=new Intent();
        intent.setData(uri);

        //intent.setClassName("net.one97", "net.one97.paytm");

        Intent chooser = Intent.createChooser(intent, "Pay with");

        if (chooser.resolveActivity(getPackageManager()) != null)
            startActivityForResult(chooser, 10);
        else
            Toast.makeText(this, "No upi apps found! ", Toast.LENGTH_SHORT).show();

        //startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10 && resultCode==RESULT_OK){

            if(data!=null){
                ArrayList<String> al=new ArrayList<>();
                String s=data.getStringExtra("response");
                al.add(s);
                Log.d("payment",s);
                upiPaymentOperation(al);
            }
            else{
                Log.d("payment","nothing1");
                ArrayList<String> al=new ArrayList<>();
                al.add("nothing");
                upiPaymentOperation(al);
            }
        }
        else{
            Log.d("payment","nothing2");
            ArrayList<String> al=new ArrayList<>();
            al.add("nothing");
            upiPaymentOperation(al);
        }
    }

    private void upiPaymentOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PaymentActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);

                onTransactionSuccessful();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                onTransactionFailure();
            }
            else {
                onTransactionFailure();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
            onTransactionFailure();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void onTransactionSuccessful(){
        FirebaseFirestore.getInstance().collection("Ongoing Orders").document().set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updateTimeSlot(timeSlot);
                }
                else{
                    Toast.makeText(PaymentActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void onTransactionFailure(){
        Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(PaymentActivity.this,RecyclerViewShopItems.class);
        intent.putExtra("Title",""+hashMap.get("Shop Name"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void updateTimeSlot(String s) {
        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    long val = task.getResult().getLong(s);
                    val++;
                    FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).update(s, val).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(PaymentActivity.this,OrderSuccessful.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PaymentActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(PaymentActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}