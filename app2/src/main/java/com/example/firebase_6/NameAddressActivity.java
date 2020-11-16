package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameAddressActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etAddress;

    TextView tvCurrentLocation;
    ImageView ivLocationSearch;
    //---ImageView ivProfileImage;
    CircleImageView ivProfileImage;
    Button but;

    String firstName;
    String lastName;
    String address;

    String phoneNumber;

    private static final int GalleryPick=1;

    private Uri imageUri;
    private String currentDate;
    private String currentTime;
    private String userRandomKey;
    String downloadImageUrl;

    private StorageReference mStorageReference;
    private ProgressDialog progressDialog;





    FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<UserAddress> al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_address);


        etFirstName = (EditText) findViewById(R.id.etOTPFirstName);
        etLastName = (EditText) findViewById(R.id.etOTPLastName);
        etAddress = (EditText) findViewById(R.id.etOTPAddress);

        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        ivLocationSearch=(ImageView)findViewById(R.id.ivLocationSearch);
        ivProfileImage=(CircleImageView)findViewById(R.id.ivProfileImage);
        but=(Button)findViewById(R.id.butOTPContinue);

        ivProfileImage.setImageResource(R.drawable.header);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

         preferences = PreferenceManager.getDefaultSharedPreferences(NameAddressActivity.this);
         editor = preferences.edit();



        mFirebaseAuth= FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();
        mStorageReference= FirebaseStorage.getInstance().getReference().child("Users");

        progressDialog=new ProgressDialog(this);


        tvCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NameAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //---getLocation();

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {

                                    Geocoder geocoder = new Geocoder(NameAddressActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    etAddress.setText(addresses.get(0).getFeatureName()+", "+addresses.get(0).getLocality()+", "+addresses.get(0).getSubAdminArea()+", "+addresses.get(0).getAdminArea()+", "+addresses.get(0).getCountryName()+", "+addresses.get(0).getPostalCode());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });


                } else {
                    ActivityCompat.requestPermissions(NameAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                        .build(NameAddressActivity.this);
                startActivityForResult(intent, 100);
            }


        });



        phoneNumber=getIntent().getStringExtra("data");

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---Intent intent=new Intent();
                //---intent.setAction(Intent.ACTION_GET_CONTENT);
                //----intent.setType("image/*");

                CropImage.activity().start(NameAddressActivity.this);


               //---startActivityForResult(Intent.createChooser(intent,"Select Image"),GalleryPick);
               /* try {
                    Intent myCropIntent = new Intent("com.android.camera.action.CROP");
                    //myCropIntent.setDataAndType(imageUri, "image/*");
                    myCropIntent.setType("image/*");
                    myCropIntent.putExtra("crop", "true");
                    myCropIntent.putExtra("aspectX", 1);
                    myCropIntent.putExtra("aspectY", 1);
                    myCropIntent.putExtra("outputX", 128);
                    myCropIntent.putExtra("outputY", 128);
                    myCropIntent.putExtra("return-data", true);
                    startActivityForResult(myCropIntent, GalleryPick);
                }
                catch(ActivityNotFoundException e) {
                    Toast.makeText(NameAddressActivity.this, "Activity not found!", Toast.LENGTH_SHORT).show();
                }*/


            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName=etFirstName.getText().toString().trim();
                lastName=etLastName.getText().toString().trim();
                address=etAddress.getText().toString().trim();


                if(firstName.isEmpty()) {
                    etFirstName.setError("Enter first name");
                    etFirstName.requestFocus();
                }


                else if(lastName.isEmpty()) {
                    etLastName.setError("Enter last name");
                    etLastName.requestFocus();
                }


                else if(address.isEmpty()) {
                    etAddress.setError("Enter address");
                    etAddress.requestFocus();
                }
                else {

                    //storeUserInfoToFirebaseStorage();
                    //Toast.makeText(NameAddressActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();


                    editor.putString("Phone Number", phoneNumber);
                    editor.putString("First Name",firstName);
                    editor.putString("Last Name", lastName);
                    //editor.putString("Image",documentSnapshot.getString("Photo"));

                    al=new ArrayList<>();
                    //UserAddress ob=new UserAddress(firstName+lastName,phoneNumber,address);
                    //al.add(ob);


                    if (imageUri != null)
                        storeUserInfoToFirebaseStorage();
                        //Toast.makeText(NameAddressActivity.this, "condition is always false!", Toast.LENGTH_SHORT).show();
                    else {
                        DocumentReference docref = FirebaseFirestore.getInstance().collection("Users").document("+91" + phoneNumber);



                        Map<String, Object> mymap = new HashMap<>();
                        mymap.put("First name", firstName);
                        mymap.put("Last name", lastName);
                       // mymap.put("Addresses",al);
                        mymap.put("Photo", downloadImageUrl);
                        mymap.put("Reviews", "");
                        mymap.put("Ongoing booking", "");
                        mymap.put("History", "");
                        mymap.put("Family and friends", "");
                        mymap.put("Phone number", phoneNumber);


                        docref.set(mymap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                DocumentReference docref2 = FirebaseFirestore.getInstance().collection("Addresses").document();

                                Map<String, Object> mymap2 = new HashMap<>();
                                mymap2.put("Account Phone Number","+91"+phoneNumber);
                                mymap2.put("User Name", firstName+" "+lastName);
                                mymap2.put("Address",address);
                                mymap2.put("User Phone Number", phoneNumber);

                                docref2.set(mymap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        editor.putString("Image",null);
                                        editor.apply();

                                        Toast.makeText(NameAddressActivity.this, "User profile created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(NameAddressActivity.this,com.example.firebase_6.HomeActivity.class);
                                        intent.putExtra("data",phoneNumber);
                                        startActivity(intent);




                                                                            }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NameAddressActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NameAddressActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

    }

    /*private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {

                        Geocoder geocoder = new Geocoder(NameAddressActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        etAddress.setText(addresses.get(0).getFeatureName()+", "+addresses.get(0).getLocality()+", "+addresses.get(0).getSubAdminArea()+", "+addresses.get(0).getAdminArea()+", "+addresses.get(0).getCountryName()+", "+addresses.get(0).getPostalCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }*/

    @Override
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

        }

        //---else if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null) {
            //imageUri=data.getData();
            //----ivProfileImage.setImageURI(imageUri);
            //------ivProfileImage.setBorderWidth(8);
            //------ivProfileImage.setBorderColor(Color.parseColor("#FFFFFF"));
            //storeUserInfoToFirebaseStorage();
           /* Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);

            }*/
            //CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            //CropImage.activity().start(NameAddressActivity.this);


       //---}
        else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                imageUri=result.getUri();

                try{
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    ivProfileImage.setImageBitmap(bitmap);
                    ivProfileImage.setBorderWidth(8);
                    ivProfileImage.setBorderColor(Color.parseColor("#FFFFFF"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private  void storeUserInfoToFirebaseStorage(){

        progressDialog.setTitle("Storing information");
        progressDialog.setMessage("Please wait until the information is added successfully" );
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        currentDate=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat newSimpleDateFormat=new SimpleDateFormat("HH:mm:ss a");
        currentTime=newSimpleDateFormat.format(calendar.getTime());

        userRandomKey=currentDate+currentTime;

        final StorageReference filepath=mStorageReference.child(imageUri.getLastPathSegment()+userRandomKey+".jpg");

        final UploadTask uploadTask=filepath.putFile(imageUri);

        /*uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NameAddressActivity.this, "Error:"+e.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NameAddressActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(NameAddressActivity.this, "got image url successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            //saveWorkersInfoToDatabase();
                        }
                    }
                });
            }
        });*/

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NameAddressActivity.this, "Error:"+e.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NameAddressActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {

                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = urlTask.getResult().toString();
                            progressDialog.dismiss();


                            DocumentReference docref = FirebaseFirestore.getInstance().collection("Users").document("+91" + phoneNumber);

                            Map<String, Object> mymap = new HashMap<>();
                            mymap.put("First name", firstName);
                            mymap.put("Last name", lastName);
                            //mymap.put("Addresses", al);
                            mymap.put("Photo", downloadImageUrl);
                            mymap.put("Reviews", "");
                            mymap.put("Ongoing booking", "");
                            mymap.put("History", "");
                            mymap.put("Family and friends", "");
                            mymap.put("Phone number", phoneNumber);


                            docref.set(mymap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    DocumentReference docref2 = FirebaseFirestore.getInstance().collection("Addresses").document();

                                    Map<String, Object> mymap2 = new HashMap<>();
                                    mymap2.put("Account Phone Number","+91"+phoneNumber);
                                    mymap2.put("User Name", firstName+" "+lastName);
                                    mymap2.put("Address",address);
                                    mymap2.put("User Phone Number", phoneNumber);

                                    docref2.set(mymap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            editor.putString("Image",downloadImageUrl);
                                            editor.apply();

                                            Toast.makeText(NameAddressActivity.this, "User profile created successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            Intent intent=new Intent(NameAddressActivity.this,com.example.firebase_6.HomeActivity.class);
                                            intent.putExtra("data",phoneNumber);
                                            startActivity(intent);




                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(NameAddressActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                        }
                                    });






                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(NameAddressActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            });


                        }
                        else {
                            Toast.makeText(NameAddressActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });

            }
        });


    }

    public void onBackPressed() {
        mFirebaseAuth.signOut();
        finish();
    }



}

