package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfileActivity extends AppCompatActivity {

    Toolbar editProfileToolbar;
    EditText editName;
    EditText editEmailId;
    EditText editPhoneNumber;
    Button butUpdate;

    FirebaseAuth ob2;
    FirebaseFirestore fstore;

    Uri imageUri;
    String myUrl = "";
    StorageReference storageReference;
    String checker = "";

    EditText etFirstName;
    EditText etLastName;
    EditText etPhoneNumber;
    CircleImageView ivProfileImage;
    Button but;

    String firstName;
    String lastName;
    String phNum;
    String phoneNumber;

    ProgressBar progressBar;

    ConstraintLayout constraintLayout;

    private StorageReference mStorageReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final int GalleryPick = 1;

    private String currentDate;
    private String currentTime;
    private String userRandomKey;
    String downloadImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /*editName = (EditText) findViewById(R.id.editName);
        editEmailId = (EditText) findViewById(R.id.editEmailId);
        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        butUpdate = (Button) findViewById(R.id.butUpdate);

        editProfileToolbar = (Toolbar) findViewById(R.id.reviewsToolbar);
        setSupportActionBar(editProfileToolbar);
        ActionBar ob = getSupportActionBar();
        // ob.setElevation(50);
        //ob.setHomeAsUpIndicator(R.drawable.back);
        //ob.setHomeButtonEnabled(true);
        //ob.setDisplayShowHomeEnabled(true);
        // ob.setDisplayHomeAsUpEnabled(true);
        // ob.setTitle("");


        ob2 = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        String userid = ob2.getCurrentUser().getUid();
        final DocumentReference docref = fstore.collection("MyUsers").document(userid);

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    editName.setText(documentSnapshot.getString("Name"));
                    editEmailId.setText(documentSnapshot.getString("Email id"));
                    editPhoneNumber.setText(documentSnapshot.getString("Phone number"));
                } else
                    Toast.makeText(EditProfileActivity.this, "No document found!", Toast.LENGTH_SHORT).show();

            }
        });

        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docref.update("Name", editName.getText().toString(),
                        "Email id", editEmailId.getText().toString(),
                        "Phone number", editPhoneNumber.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileActivity.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, "Error updating document!", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });*/


        etFirstName = (EditText) findViewById(R.id.etEditFirstName);
        etLastName = (EditText) findViewById(R.id.etEditLastName);
        etPhoneNumber = (EditText) findViewById(R.id.etEditPhoneNumber);
        ivProfileImage = (CircleImageView) findViewById(R.id.ivEditProfileImage);
        but = (Button) findViewById(R.id.butEditSubmit);

        ivProfileImage.setImageResource(R.drawable.header);
        preferences = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
        editor = preferences.edit();

        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.GONE);

        constraintLayout=(ConstraintLayout)findViewById(R.id.nameAddressConstraintLayout);



        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);

        phoneNumber = preferences.getString("Phone Number", null);
        etPhoneNumber.setText("+91" + phoneNumber);
        etPhoneNumber.setEnabled(false);

        etFirstName.setText(preferences.getString("First Name", null));
        etLastName.setText(preferences.getString("Last Name", null));

        String imgstr = preferences.getString("Image", null);


        if (imgstr != null) {
            Picasso.get().load(imgstr).into(ivProfileImage);
            ivProfileImage.setBorderWidth(8);
            ivProfileImage.setBorderColor(Color.parseColor("#FFFFFF"));
        }




        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(EditProfileActivity.this);

            }
        });




        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etFirstName.getText().toString().trim();
                lastName = etLastName.getText().toString().trim();

                if (firstName.isEmpty()) {
                    etFirstName.setError("Enter first name");
                    etFirstName.requestFocus();
                } else if (lastName.isEmpty()) {
                    etLastName.setError("Enter last name");
                    etLastName.requestFocus();
                } else {

                    //storeUserInfoToFirebaseStorage();
                    //Toast.makeText(NameAddressActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();


                    editor.putString("Phone Number", phoneNumber);
                    editor.putString("First Name", firstName);
                    editor.putString("Last Name", lastName);
                    //editor.putString("Image",documentSnapshot.getString("Photo"));


                    if (imageUri != null)
                        storeUserInfoToFirebaseStorage();
                        //Toast.makeText(NameAddressActivity.this, "condition is always false!", Toast.LENGTH_SHORT).show();
                    else {

                        progressBar.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.GONE);

                        DocumentReference docref = FirebaseFirestore.getInstance().collection("Users").document("+91" + phoneNumber);

                        docref.update("First name", firstName,
                                "Last name", lastName)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        editor.apply();

                                        progressBar.setVisibility(View.GONE);
                                        finish();

                                        //Toast.makeText(EditProfileActivity.this, "User profile updated successfully", Toast.LENGTH_SHORT).show();

//                                        Intent intent = new Intent(EditProfileActivity.this, com.example.firebase_6.UserProfileActivity.class);
//                                        intent.putExtra("data", phoneNumber);
//                                        startActivity(intent);
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        progressBar.setVisibility(View.GONE);
                                        constraintLayout.setVisibility(View.VISIBLE);

                                        Toast.makeText(EditProfileActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                }
            }
        });


    }


    private void storeUserInfoToFirebaseStorage() {

        progressDialog.setTitle("Updating data");
        progressDialog.setMessage("Please wait until the data is updated successfully");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        currentDate = simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime = newSimpleDateFormat.format(calendar.getTime());

        userRandomKey = currentDate + currentTime;

        final StorageReference filepath = mStorageReference.child(imageUri.getLastPathSegment() + userRandomKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(imageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(EditProfileActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {

                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = urlTask.getResult().toString();


                            DocumentReference docref = FirebaseFirestore.getInstance().collection("Users").document("+91" + phoneNumber);

                            docref.update("First name", firstName,
                                    "Last name", lastName, "Photo", downloadImageUrl)

                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            editor.putString("Image", downloadImageUrl);
//                                            editor.putString("Image", downloadImageUrl);
//
//                                            editor.putString("Image", downloadImageUrl);
//
//                                            editor.putString("Image", downloadImageUrl);

                                            editor.apply();

                                            //Toast.makeText(EditProfileActivity.this, "User profile updated successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();

//                                            Intent intent = new Intent(EditProfileActivity.this, com.example.firebase_6.UserProfileActivity.class);
//                                            intent.putExtra("data", phoneNumber);
//                                            startActivity(intent);
                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });


                        }
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

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

}
