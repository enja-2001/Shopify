package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddWorkersActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {

    Spinner spinner;
    Spinner spinnerGender;
    String profession;
    String gender;
    String dateOfBirth="";
    String phoneNumber;
    String workingExperience="";
    Button but;

    TextView tvDateOfBirth;
    TextView tvExperience;

    TextView tvErrorGender;
    CircleImageView ivWorkersProfileImage2;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workers2);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
        tvExperience = (TextView) findViewById(R.id.tvExperience);
        but=(Button)findViewById(R.id.butWorkers2Continue);


        ivWorkersProfileImage2=(CircleImageView)findViewById(R.id.ivWorkersProfileImage2);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseUser=mFirebaseAuth.getCurrentUser();


        tvErrorGender = (TextView) findViewById(R.id.tvErrorGender);

        tvErrorGender.setVisibility(View.GONE);


        phoneNumber=getIntent().getStringExtra("data");


        preferences = PreferenceManager.getDefaultSharedPreferences(AddWorkersActivity2.this);
        editor = preferences.edit();





        String imgstr = preferences.getString("Image", null);


        if (imgstr != null) {
            Picasso.get().load(imgstr).into(ivWorkersProfileImage2);
            ivWorkersProfileImage2.setBorderWidth(8);
            ivWorkersProfileImage2.setBorderColor(Color.parseColor("#FFFFFF"));
        }




        String[] professionArray = getResources().getStringArray(R.array.profession);
        List<String> professionList = Arrays.asList(professionArray);
        Collections.sort(professionList);

        List al = new ArrayList<String>();
        al.addAll(professionList);
        al.add(0, "Select profession");

        ArrayAdapter adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, al) {


            @Override
            public boolean isEnabled(int position) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    return false;
                }
                return true;
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                // TODO Auto-generated method stub
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;
                if (position == 0) {
                    mTextView.setTextColor(Color.GRAY);
                } else {
                    mTextView.setTextColor(Color.BLACK);
                }
                return mView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        String[] genderArray = getResources().getStringArray(R.array.gender);
        List<String> genderList = Arrays.asList(genderArray);

        List alGender = new ArrayList<String>();
        alGender.addAll(genderList);
        alGender.add(0, "Select gender");

        ArrayAdapter adapterGender = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, alGender) {


            @Override
            public boolean isEnabled(int position) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    return false;
                }
                return true;
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                // TODO Auto-generated method stub
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;
                if (position == 0) {
                    mTextView.setTextColor(Color.GRAY);
                } else {
                    mTextView.setTextColor(Color.BLACK);
                }
                return mView;
            }
        };
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setOnItemSelectedListener(this);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub          R.style.CustomDatePickerDialogTheme
                DatePickerDialog ob = new DatePickerDialog(AddWorkersActivity2.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                ob.getDatePicker().setSpinnersShown(true);
                ob.getDatePicker().setCalendarViewShown(false);



                /*final AlertDialog.Builder d = new AlertDialog.Builder(AddWorkersActivity2.this);
                //LayoutInflater inflater = AddWorkersActivity2.this.getLayoutInflater();
                //View dialogView = inflater.inflate(R.layout.dialog, null);

                d.setTitle("Work Experience");
                d.setMessage("Select a year");
                d.setView(ob.getDatePicker());

                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Log.d(TAG, "onClick: " + numberPicker.getValue());
                        //tvExperience.setText(Integer.toString(numberPicker.getValue()));
                        //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();*/



               /* if (ob.getWindow() != null) {
                    //ob.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    //      WindowManager.LayoutParams.WRAP_CONTENT);

                    WindowManager.LayoutParams params = ob.getWindow().getAttributes();
                    params.gravity = Gravity.CENTER;
                    params.width = 600;
                    params.height = 10000;

                    ob.getWindow().setAttributes(params);


                }*/
                ob.show();
            }
        });



        tvExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createDialogWithoutDateField().show();
                show();

            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profession = spinner.getSelectedItem().toString();
                gender = spinnerGender.getSelectedItem().toString();




                if (profession.equals("Select profession") ||gender.equals("Select gender")||dateOfBirth.isEmpty()||workingExperience.isEmpty()) {
                    tvErrorGender.setVisibility(View.VISIBLE);
                }


                //storeUserInfoToFirebaseStorage();
                //Toast.makeText(NameAddressActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();


                //editor.putString("Image",documentSnapshot.getString("Photo"));


                //Toast.makeText(NameAddressActivity.this, "condition is always false!", Toast.LENGTH_SHORT).show();
                else {

                    tvErrorGender.setVisibility(View.GONE);

                    DocumentReference docref= FirebaseFirestore.getInstance().collection("Workers").document("+91"+phoneNumber);

                    docref.update("Date of birth", dateOfBirth,
                            "Gender", gender,"Profession",profession,"Working since",workingExperience)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    editor.putString("Phone Number", phoneNumber);
                                    editor.putString("Gender",gender);
                                    editor.putString("Profession", profession);
                                    editor.putString("Working since",workingExperience);
                                    editor.putString("Date of birth",dateOfBirth);


                                    editor.apply();

                                    Toast.makeText(AddWorkersActivity2.this, "User profile updated successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(AddWorkersActivity2.this, com.example.firebase_6.HomeActivity.class);
                                    intent.putExtra("data", phoneNumber);
                                    startActivity(intent);
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddWorkersActivity2.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                }
                            });




                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateLabel() {
        String myFormat = "MMM dd,yyyy";    //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirth=sdf.format(myCalendar.getTime());

        tvDateOfBirth.setText(dateOfBirth);
    }


    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, 2014, 1, 24);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public void show() {

        final AlertDialog.Builder d = new AlertDialog.Builder(AddWorkersActivity2.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Work Experience");
        d.setMessage("You have been working since the year");
        d.setView(dialogView);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));//myCalendar.get(Calendar.YEAR)
        numberPicker.setMinValue(2000);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });
        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                workingExperience=Integer.toString(numberPicker.getValue());
                 tvExperience.setText(workingExperience);

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }

    public void onBackPressed() {
        mFirebaseAuth.signOut();
        finish();
    }
}

