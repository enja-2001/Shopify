package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddressesActivity extends AppCompatActivity {
    Toolbar addressesToolbar;

    RecyclerView recyclerView;

    ArrayList<UserAddress> al;
    String phoneNumber;
    RecyclerViewMyAddressAdapter adapter;

    Button but1;
    Button but2;
    Button but3;

    View view1;
    View view2;



    String address;
    String addressName;
    String addressPhoneNumber;
    String addressHouseNumber;



    FusedLocationProviderClient fusedLocationProviderClient;

    EditText etAddress;
    EditText etName;
    EditText etPhoneNUmber;
    EditText etHouseNumber;




    TextView tvCurrentLocation;
    public TextView tvTotalAddresses;
    ImageView ivLocationSearch;
    TextInputLayout textInputLayout;
    TextInputLayout textInputLayoutName;

    TextInputLayout textInputLayoutPhoneNumber;

    TextInputLayout textInputLayoutHouseNumber;

    TextView tvAddingAddress;

    TextView tvAddingAddress2;

    ProgressBar progressBar;
    ProgressDialog progressDialog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        addressesToolbar=findViewById(R.id.addressesToolbar);
        setSupportActionBar(addressesToolbar);
        ActionBar ob=getSupportActionBar();
        // ob.setElevation(50);
        ob.setHomeAsUpIndicator(R.drawable.back);
        ob.setHomeButtonEnabled(true);
        ob.setDisplayShowHomeEnabled(true);
        ob.setDisplayHomeAsUpEnabled(true);
        ob.setTitle("");


        progressDialog=new ProgressDialog(AddressesActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.myAddressRecyclerView);


        but1 = (Button) findViewById(R.id.buttonMyAddress);
        but2 = (Button) findViewById(R.id.buttonMyAddress2Submit);
        but3 = (Button) findViewById(R.id.buttonMyAddress2Cancel);

        view1=(View)findViewById(R.id.view);
        view2=(View)findViewById(R.id.view2);






        etName = (EditText) findViewById(R.id.etMyAddressAddressName);

        etPhoneNUmber = (EditText) findViewById(R.id.etMyAddressAddressPhoneNumber);

        etHouseNumber = (EditText) findViewById(R.id.etMyAddressAddressHouseNumber);

        tvAddingAddress=(TextView)findViewById(R.id.tvAddingAddress);
        tvAddingAddress2=(TextView)findViewById(R.id.tvAddingAddress2);

        tvTotalAddresses=(TextView)findViewById(R.id.tvMyAddressTotalAddresses);

        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.VISIBLE);




        etAddress = (EditText) findViewById(R.id.etMyAddressAddress);
        tvCurrentLocation = (TextView) findViewById(R.id.tvMyAddressCurrentLocation);
        ivLocationSearch = (ImageView) findViewById(R.id.ivMyAddressLocationSearch);

        textInputLayout=(TextInputLayout)findViewById(R.id.text_input_layout_my_address);

        textInputLayoutName=(TextInputLayout)findViewById(R.id.text_input_layout_my_address_name);

        textInputLayoutPhoneNumber=(TextInputLayout)findViewById(R.id.text_input_layout_my_address_phone_number);

        textInputLayoutHouseNumber=(TextInputLayout)findViewById(R.id.text_input_layout_my_address_house_number);



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        but2.setVisibility(View.GONE);
        but3.setVisibility(View.GONE);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);


        ivLocationSearch.setVisibility(View.GONE);
        tvCurrentLocation.setVisibility(View.GONE);
        textInputLayout.setVisibility(View.GONE);
        textInputLayoutName.setVisibility(View.GONE);

        textInputLayoutPhoneNumber.setVisibility(View.GONE);

        textInputLayoutHouseNumber.setVisibility(View.GONE);
        tvAddingAddress.setVisibility(View.GONE);
        tvAddingAddress2.setVisibility(View.GONE);





        al = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddressesActivity.this);

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
                    adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);
                    recyclerView.setAdapter(adapter);
                    tvTotalAddresses.setText(adapter.getItemCount()+" saved addresses");

                    progressBar.setVisibility(View.GONE);





                }
                else{
                    Toast.makeText(AddressesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }

            }
        });



        /*FirebaseFirestore.getInstance().collection("Addresses").document("+91" + phoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        al = (ArrayList<UserAddress>) documentSnapshot.get("Addresses");
                        //documentSnapshot.
                        adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);

                        //Toast.makeText(AddressesActivity.this, al.size()+" "+al.get(0).getAddress(), Toast.LENGTH_SHORT).show();
                        //UserAddress ob=new UserAddress("Oh my","98766","yugvhghj");
                       // Toast.makeText(AddressesActivity.this, ob.toString(), Toast.LENGTH_SHORT).show();
                        String str="hello";
                        Map<String, Object> map = documentSnapshot.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("Addresses")) {
                                str=entry.getValue().toString();
                                //Toast.makeText(AddressesActivity.this, entry.getValue().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(AddressesActivity.this, str, Toast.LENGTH_SHORT).show();

                        recyclerView.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(AddressesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/



        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but1.setVisibility(View.GONE);


                but2.setVisibility(View.VISIBLE);
                but3.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);

                ivLocationSearch.setVisibility(View.VISIBLE);
                tvCurrentLocation.setVisibility(View.VISIBLE);
                textInputLayout.setVisibility(View.VISIBLE);
                textInputLayoutName.setVisibility(View.VISIBLE);
                textInputLayoutPhoneNumber.setVisibility(View.VISIBLE);
                textInputLayoutHouseNumber.setVisibility(View.VISIBLE);
                etName.setVisibility(View.VISIBLE);
                etPhoneNUmber.setVisibility(View.VISIBLE);

                etAddress.setVisibility(View.VISIBLE);

                etHouseNumber.setVisibility(View.VISIBLE);


                tvAddingAddress.setVisibility(View.VISIBLE);
                tvAddingAddress2.setVisibility(View.VISIBLE);




            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address=etAddress.getText().toString();
                addressName=etName.getText().toString();

                addressPhoneNumber=etPhoneNUmber.getText().toString();
                addressHouseNumber=etHouseNumber.getText().toString();



                if(addressName.isEmpty()) {
                    etName.setError("Enter name");
                    etName.requestFocus();

                }

                else if(addressPhoneNumber.length()!=10) {
                    etPhoneNUmber.setError("Enter a valid phone number");
                    etPhoneNUmber.requestFocus();

                }

                else if(address.isEmpty()) {
                    etAddress.setError("Enter address");
                    etAddress.requestFocus();

                }

                else if(addressHouseNumber.isEmpty()) {
                    etHouseNumber.setError("Field required");
                    etHouseNumber.requestFocus();

                }
                else{

                    //al=new ArrayList<>();
                    //al.add(address);

                    progressDialog.setTitle("Adding address");
                    progressDialog.setMessage("Please wait until the address is added successfully" );
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    DocumentReference docref = FirebaseFirestore.getInstance().collection("Addresses").document();

                    Map<String, Object> mymap = new HashMap<>();
                    mymap.put("Account Phone Number","+91"+phoneNumber);
                    mymap.put("User Name", addressName);
                    mymap.put("Address", address);
                    mymap.put("House Number", addressHouseNumber);

                    mymap.put("User Phone Number", addressPhoneNumber);

                    docref.set(mymap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressDialog.dismiss();


                            but1.setVisibility(View.VISIBLE);


                            but2.setVisibility(View.GONE);
                            but3.setVisibility(View.GONE);
                            view1.setVisibility(View.GONE);
                            view2.setVisibility(View.GONE);

                            ivLocationSearch.setVisibility(View.GONE);
                            tvCurrentLocation.setVisibility(View.GONE);
                            textInputLayout.setVisibility(View.GONE);

                            textInputLayoutName.setVisibility(View.GONE);

                            textInputLayoutPhoneNumber.setVisibility(View.GONE);

                            textInputLayoutHouseNumber.setVisibility(View.GONE);
                            tvAddingAddress.setVisibility(View.GONE);

                            tvAddingAddress2.setVisibility(View.GONE);

                            UserAddress ob=new UserAddress(addressName,addressPhoneNumber,address,addressHouseNumber,"+91"+phoneNumber);
                            al.add(ob);



                            adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);

                            recyclerView.setAdapter(adapter);
                            tvTotalAddresses.setText(adapter.getItemCount()+" saved addresses");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddressesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                        }
                    });



                    /*docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    al = (ArrayList<UserAddress>) documentSnapshot.get("Addresses");


                                    Map<String, Object> map = documentSnapshot.getData();
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        if (entry.getKey().equals("Addresses")) {
                                            //Log.d("TAG", entry.getValue().toString());
                                            al=(ArrayList<UserAddress>)entry.getValue();
                                        }
                                    }


                                    UserAddress ob=new UserAddress(addressName,addressPhoneNumber,addressHouseNumber+","+address);

                                    al.add(ob);

                                    //adapter = new RecyclerViewAddressAdapter(al, PlaceOrderAddress.this);

                                    //recyclerView.setAdapter(adapter);
                                    docref.update("Addresses", al)

                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    but1.setVisibility(View.VISIBLE);


                                                    but2.setVisibility(View.GONE);

                                                    ivLocationSearch.setVisibility(View.GONE);
                                                    tvCurrentLocation.setVisibility(View.GONE);
                                                    textInputLayout.setVisibility(View.GONE);

                                                    textInputLayoutName.setVisibility(View.GONE);

                                                    textInputLayoutPhoneNumber.setVisibility(View.GONE);

                                                    textInputLayoutHouseNumber.setVisibility(View.GONE);
                                                    tvAddingAddress.setVisibility(View.GONE);

                                                    tvAddingAddress2.setVisibility(View.GONE);





                                                    Toast.makeText(AddressesActivity.this, "Address added successfully", Toast.LENGTH_SHORT).show();
                                                    adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);

                                                    recyclerView.setAdapter(adapter);

                                                }
                                            })

                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AddressesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                }

                            } else {
                                Toast.makeText(AddressesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/

                }

            }
        });

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                but1.setVisibility(View.VISIBLE);


                but2.setVisibility(View.GONE);
                but3.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);

                ivLocationSearch.setVisibility(View.GONE);
                tvCurrentLocation.setVisibility(View.GONE);
                textInputLayout.setVisibility(View.GONE);

                textInputLayoutName.setVisibility(View.GONE);

                textInputLayoutPhoneNumber.setVisibility(View.GONE);

                textInputLayoutHouseNumber.setVisibility(View.GONE);
                etName.setVisibility(View.GONE);
                etPhoneNUmber.setVisibility(View.GONE);

                etAddress.setVisibility(View.GONE);

                etHouseNumber.setVisibility(View.GONE);

                tvAddingAddress.setVisibility(View.GONE);

                tvAddingAddress2.setVisibility(View.GONE);

                adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);

                recyclerView.setAdapter(adapter);
                tvTotalAddresses.setText(adapter.getItemCount()+" saved addresses");

            }
        });

        tvCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddressesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //---getLocation();

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {

                                    Geocoder geocoder = new Geocoder(AddressesActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    etAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });


                } else {
                    ActivityCompat.requestPermissions(AddressesActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                        .build(AddressesActivity.this);
                startActivityForResult(intent, 100);
            }


        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            //tvAddress.setText(feature.placeName());
            etAddress.setText(feature.placeName());


        }

        else if (requestCode == 101 && resultCode == RESULT_OK) {

            if(data!=null) {
                Toast.makeText(this, "data is not null!", Toast.LENGTH_SHORT).show();
                CarmenFeature feature = PlaceAutocomplete.getPlace(data);

                //String id=getIntent().getStringExtra("EditText");
                int id = 0;
                String strid="";

                //id = Integer.parseInt(data.getStringExtra("EditText"));
                strid =data.getStringExtra("EditText");

                //--------------------Toast.makeText(this, ""+feature.placeName(), Toast.LENGTH_SHORT).show();


//            if (data.hasExtra("EditText"))
//            {
//                //Get the selected file.
////                Toast.makeText(AddressesActivity.this, "true", Toast.LENGTH_SHORT).show();
//                id = data.getExtras().getInt("EditText");
//            }


                //EditText et=(EditText)findViewById(Integer.parseInt(id));

                //et.setText(feature.placeName());
                Toast.makeText(this, "" +strid, Toast.LENGTH_SHORT).show();

                //tvAddress.setText(feature.placeName());
                //holder.etUserAddress.setText(feature.placeName());
                //MyViewHolder ob=new MyViewHolder();

                //etUserAddress
            }
            else{
                Toast.makeText(this, "data is null!", Toast.LENGTH_SHORT).show();
            }

        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }
    }





}
