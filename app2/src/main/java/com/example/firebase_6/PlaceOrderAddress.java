package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PlaceOrderAddress extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<UserAddress> al;
    String phoneNumber;
    RecyclerViewAddressAdapter adapter;

    Button but1;
    Button but2;

    TextView tvEditAddress;

    String address;
    //FusedLocationProviderClient fusedLocationProviderClient;

    EditText etAddress;

    //TextView tvCurrentLocation;
    TextView tvTotalAddresses;

    //ImageView ivLocationSearch;
    //TextInputLayout textInputLayout;
    public int radioSelectedPosition;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_address);

        recyclerView = (RecyclerView) findViewById(R.id.addressRecyclerView);


        but1 = (Button) findViewById(R.id.buttonPlaceOrderNext);
        but2 = (Button) findViewById(R.id.buttonPlaceOrderBack);

        tvEditAddress = (TextView) findViewById(R.id.tvEditAddress);





        /*etAddress = (EditText) findViewById(R.id.etPlaceOrderAddress);
        tvCurrentLocation = (TextView) findViewById(R.id.tvPlaceOrderCurrentLocation);
        ivLocationSearch = (ImageView) findViewById(R.id.ivPlaceOrderLocationSearch);
        textInputLayout=(TextInputLayout)findViewById(R.id.text_input_layout_place_order);*/
        tvTotalAddresses=(TextView)findViewById(R.id.tvTotalAddresses);

        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //---but2.setVisibility(View.GONE);
        /*but4.setVisibility(View.GONE);

        ivLocationSearch.setVisibility(View.GONE);
        tvCurrentLocation.setVisibility(View.GONE);
        textInputLayout.setVisibility(View.GONE);*/



        al = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        preferences = PreferenceManager.getDefaultSharedPreferences(PlaceOrderAddress.this);

        phoneNumber = preferences.getString("Phone Number", null);


        FirebaseFirestore.getInstance().collection("Addresses").whereEqualTo("Account Phone Number","+91"+phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult()){
                        String s1= documentSnapshot.getString("User Name");
                        String s2= documentSnapshot.getString("User Phone Number");
                        String s3= documentSnapshot.getString("Address");


                        String s4=documentSnapshot.getString("House Number");

                        UserAddress ob=new UserAddress(s1,s2,s3,s4,"+91"+phoneNumber);
                        al.add(ob);


                    }
                    tvTotalAddresses.setText(al.size()+" SAVED ADDRESSES");
                    adapter = new RecyclerViewAddressAdapter(al, PlaceOrderAddress.this);
                    recyclerView.setAdapter(adapter);




                }
                else{
                    Toast.makeText(PlaceOrderAddress.this, "Error occured!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlaceOrderAddress.this,AddressesActivity.class);
                startActivity(intent);
            }
        });


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*but1.setVisibility(View.GONE);
                but3.setVisibility(View.GONE);


                but2.setVisibility(View.VISIBLE);
                but4.setVisibility(View.VISIBLE);

                ivLocationSearch.setVisibility(View.VISIBLE);
                tvCurrentLocation.setVisibility(View.VISIBLE);
                textInputLayout.setVisibility(View.VISIBLE);*/

                //adapter = new RecyclerViewAddressAdapter(al, PlaceOrderAddress.this);

                //int pos=getAtLeastOneIsChecked();
                //int pos=adapter.mSelectedItem;

                if(radioSelectedPosition==-1)
                    Toast.makeText(PlaceOrderAddress.this, "Select an address!", Toast.LENGTH_SHORT).show();
                else{

//                    Toast.makeText(PlaceOrderAddress.this, Integer.toString(radioSelectedPosition), Toast.LENGTH_SHORT).show();



                    String userPhoneNumber="+91" + preferences.getString("Phone Number", null);

                    String workerPhoneNumber="+91" + preferences.getString("workerPhoneNumber", null);

                    String orderUserName=al.get(radioSelectedPosition).name;
                    String orderUserPhoneNumber="+91"+al.get(radioSelectedPosition).phoneNumber;

                    String orderUserAddress=al.get(radioSelectedPosition).houseNumber+","+al.get(radioSelectedPosition).address;



                    MyOrder ob=new MyOrder("","","","","","","","","","","","", userPhoneNumber, workerPhoneNumber, orderUserName, orderUserAddress, orderUserPhoneNumber, "", "");

                    Intent intent=new Intent(PlaceOrderAddress.this,DateTimeActivity.class);
                    intent.putExtra("obj",ob);

                    startActivity(intent);





                }


            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PlaceOrderAddress.this);

                String workerPhoneNumber= "+91" + preferences.getString("workerPhoneNumber", null);


                FirebaseFirestore.getInstance().collection("Workers").document(workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){


                            //Toast.makeText(PlaceOrder2Address.this, workerPhoneNumber, Toast.LENGTH_SHORT).show();

                            worker ob=new worker(task.getResult().getString("Phone number"),(task.getResult().getString("First name")+" "+task.getResult().getString("Last name")),task.getResult().getString("Rating"),task.getResult().getString("Photo"),task.getResult().getString("Gender"),task.getResult().getString("Date of birth"),task.getResult().getString("Reviews"),task.getResult().getString("Working since"),task.getResult().getString("Profession"));

                            Intent intent=new Intent(PlaceOrderAddress.this,WorkerDetailsActivity.class);
                            intent.putExtra("obj",ob);


                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(PlaceOrderAddress.this, "Error occured!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });






              /*  address=etAddress.getText().toString();

                if(address.isEmpty()) {
                    etAddress.setError("Enter address");
                    etAddress.requestFocus();

                }
                else{

                    //al=new ArrayList<>();
                    //al.add(address);


                    DocumentReference docref = FirebaseFirestore.getInstance().collection("Users").document("+91" + phoneNumber);

                    docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    al = (ArrayList<UserAddress>) documentSnapshot.get("Addresses");

                                    al.add(address);

                                    //adapter = new RecyclerViewAddressAdapter(al, PlaceOrderAddress.this);

                                    //recyclerView.setAdapter(adapter);
                                    docref.update("Addresses", al)

                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    but1.setVisibility(View.VISIBLE);
                                                    but3.setVisibility(View.VISIBLE);


                                                    but2.setVisibility(View.GONE);
                                                    but4.setVisibility(View.GONE);

                                                    ivLocationSearch.setVisibility(View.GONE);
                                                    tvCurrentLocation.setVisibility(View.GONE);
                                                    textInputLayout.setVisibility(View.GONE);


                                                    Toast.makeText(PlaceOrderAddress.this, "Address added successfully", Toast.LENGTH_SHORT).show();
                                                    adapter = new RecyclerViewAddressAdapter(al, PlaceOrderAddress.this);

                                                    recyclerView.setAdapter(adapter);

                                                }
                                            })

                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(PlaceOrderAddress.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                }

                            } else {
                                Toast.makeText(PlaceOrderAddress.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/

                    /*docref.update("Addresses", al)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    but1.setVisibility(View.VISIBLE);

                                    but2.setVisibility(View.GONE);
                                    ivLocationSearch.setVisibility(View.GONE);
                                    tvCurrentLocation.setVisibility(View.GONE);
                                    textInputLayout.setVisibility(View.GONE);


                                    Toast.makeText(PlaceOrderAddress.this, "Address added successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PlaceOrderAddress.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                }
                            });*/

                //}

            }
        });

       /* but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                but1.setVisibility(View.VISIBLE);
                but3.setVisibility(View.VISIBLE);


                but2.setVisibility(View.GONE);
                but4.setVisibility(View.GONE);

                ivLocationSearch.setVisibility(View.GONE);
                tvCurrentLocation.setVisibility(View.GONE);
                textInputLayout.setVisibility(View.GONE);

            }
        });*/

        /*------------but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi("1");


            }
        });------------*/



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




    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }*/

    /*------------------@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            //tvAddress.setText(feature.placeName());
            etAddress.setText(feature.placeName());
        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }----------------------------------*/

        /*--------------------------if(requestCode==10 && resultCode==RESULT_OK){

            if(data!=null){
                ArrayList<String> al=new ArrayList<>();
                al.add(data.getStringExtra("response"));
                upiPaymentOperation(al);
            }
            else{
                ArrayList<String> al=new ArrayList<>();
                al.add("nothing");
                upiPaymentOperation(al);
            }
        }
        else{
            ArrayList<String> al=new ArrayList<>();
            al.add("nothing");
            upiPaymentOperation(al);
        }
    }--------------------------------------------------------*/

    /*--------------------------------------------private void payUsingUpi(String amt) {
        Uri uri=Uri.parse("upi://pay").buildUpon().appendQueryParameter("pa","8927782759@upi").appendQueryParameter("pn","Md. Enjamum Hossain").appendQueryParameter("tn","Testing").appendQueryParameter("am",amt).appendQueryParameter("cu","INR").build();

        Intent intent=new Intent(Intent.ACTION_VIEW);
        //Intent intent=new Intent();
        intent.setData(uri);

        //intent.setClassName("net.one97", "net.one97.paytm");


        Intent chooser=Intent.createChooser(intent,"Pay with");

        if(chooser.resolveActivity(getPackageManager())!=null)
            startActivityForResult(chooser,10);
        else
            Toast.makeText(this, "No apps found! ", Toast.LENGTH_SHORT).show();

        //startActivityForResult(intent,101);




    }--------------------------------*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10 && resultCode==RESULT_OK){

            if(data!=null){
                ArrayList<String> al=new ArrayList<>();
                al.add(data.getStringExtra("response"));
                upiPaymentOperation(al);
            }
            else{
                ArrayList<String> al=new ArrayList<>();
                al.add("nothing");
                upiPaymentOperation(al);
            }
        }
        else{
            ArrayList<String> al=new ArrayList<>();
            al.add("nothing");
            upiPaymentOperation(al);
        }
    }*/

    /*----------------------------------------private void upiPaymentOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PlaceOrderAddress.this)) {
            String str = data.get(0);
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
                Toast.makeText(PlaceOrderAddress.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PlaceOrderAddress.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PlaceOrderAddress.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PlaceOrderAddress.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
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
    }---------------------------------*/


    private int getAtLeastOneIsChecked(){
        /*for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItemAt(i).isChecked()) {
                return i;
            }
        }
        return -1;*/
        if(adapter.mSelectedItem==-1)
            return -1;
        else
            return adapter.mSelectedItem;
    }

}