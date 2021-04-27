package com.example.shopify;

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
import android.widget.Toast;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    Button but3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        but3=findViewById(R.id.button2);

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi("1");
            }
        });



        /*tvCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(PlaceOrderAddress.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //---getLocation();

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {

                                    Geocoder geocoder = new Geocoder(PlaceOrderAddress.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    etAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });


                } else {
                    ActivityCompat.requestPermissions(PlaceOrderAddress.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        ivLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken("pk.eyJ1IjoiZW5qYS0yMDAxIiwiYSI6ImNrYnkyajU4eDB2dDMydGxnc2oxam41dDkifQ.i77C__pKEuCCagjXv89drQ")
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(PlaceOrderAddress.this);
                startActivityForResult(intent, 100);
            }


        });*/

    }

    private void payUsingUpi(String amt) {
        Uri uri = Uri.parse("upi://pay")
                .buildUpon()
                .appendQueryParameter("pa", "8927354558@ybl")
                .appendQueryParameter("pn", "Snehasish Dhar")
                .appendQueryParameter("tn", "Testing")
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
                Intent intent=new Intent(PaymentActivity.this,OrderSuccessful.class);
                startActivity(intent);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent(PaymentActivity.this,HomeActivity.class);
        startActivity(intent);
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
}