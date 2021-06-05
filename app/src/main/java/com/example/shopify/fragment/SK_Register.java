package com.example.shopify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopify.R;
import com.example.shopify.SK_Dashboard;
import com.example.shopify.helper.SharedPrefManager;
import com.example.shopify.timeSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SK_Register extends Fragment {

    EditText nametxt, shopNametxt, shopAddrtxt, emailtxt, passtxt, pintxt, phonetxt, cat;
    ProgressBar progressBar;
    TextView tvProfile;
    Button register;
    TextView login;
    String name, email, shopName, shopAddr, pass, pincode, phone, category;
//    SharedPrefManager sharedPrefManager;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_s_k__register, container, false);

        nametxt = root.findViewById(R.id.editTextTextPersonName);
        shopNametxt = root.findViewById(R.id.editTextTextPersonName2);
        shopAddrtxt = root.findViewById(R.id.editTextTextPersonName3);
        passtxt = root.findViewById(R.id.editTextTextPassword);
        emailtxt = root.findViewById(R.id.editTextTextEmailAddress);
        progressBar = root.findViewById(R.id.progress);
        pintxt = root.findViewById(R.id.pincode);
        phonetxt = root.findViewById(R.id.phone);
        cat = root.findViewById(R.id.category);
        tvProfile=root.findViewById(R.id.profile);

        login = root.findViewById(R.id.butLogin);
        register = root.findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailtxt.getText().toString();
                name = nametxt.getText().toString();
                shopName = shopNametxt.getText().toString();
                pass = passtxt.getText().toString();
                pincode = pintxt.getText().toString();
                phone = phonetxt.getText().toString();
                category = cat.getText().toString();
                shopAddr = shopAddrtxt.getText().toString();


                emailtxt.setVisibility(View.INVISIBLE);
                nametxt.setVisibility(View.INVISIBLE);
                shopAddrtxt.setVisibility(View.INVISIBLE);
                shopNametxt.setVisibility(View.INVISIBLE);
                passtxt.setVisibility(View.INVISIBLE);
                pintxt.setVisibility(View.INVISIBLE);
                cat.setVisibility(View.INVISIBLE);
                phonetxt.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                tvProfile.setVisibility(View.INVISIBLE);


                progressBar.setVisibility(View.VISIBLE);
//                sharedPrefManager = new SharedPrefManager(getContext());

                if(email.isEmpty() || pass.isEmpty() || name.isEmpty() || shopName.isEmpty() || shopAddr.isEmpty() || phone.isEmpty() || category.isEmpty() || pincode.isEmpty())
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPrefManager.getInstance(getContext()).loginUser(name, email, shopName, shopAddr, phone);
                                Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                sendData(shopAddr, category, 3, "10:00 PM", shopName,"09:00 AM", pincode, phone, name);
                                Intent intent = new Intent(getContext(), SK_Dashboard.class);
                                startActivity(intent);
                            } else {

                                emailtxt.setVisibility(View.VISIBLE);
                                nametxt.setVisibility(View.VISIBLE);
                                shopAddrtxt.setVisibility(View.VISIBLE);
                                shopNametxt.setVisibility(View.VISIBLE);
                                passtxt.setVisibility(View.VISIBLE);
                                pintxt.setVisibility(View.VISIBLE);
                                cat.setVisibility(View.VISIBLE);
                                phonetxt.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);
                                register.setVisibility(View.VISIBLE);
                                tvProfile.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SK_Login();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                emailtxt.setVisibility(View.INVISIBLE);
                nametxt.setVisibility(View.INVISIBLE);
                shopNametxt.setVisibility(View.INVISIBLE);
                shopAddrtxt.setVisibility(View.INVISIBLE);
                passtxt.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


            }
        });





        return root;
    }

    public void sendData(String shopAddr, String category, int maxCus, String closingTime, String shopName, String openTime, String pinCode, String phone, String name)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("Address", shopAddr);
        data.put("Category", category);
        data.put("Closing time", closingTime);
        data.put("Maximum customer", maxCus);
        data.put("Name", shopName);
        data.put("Opening time", openTime);
        data.put("PIN code", pinCode);
        data.put("Phone number", phone);
        data.put("Shopkeeper", name);

        FirebaseFirestore.getInstance().collection("Shops").document(phone).set(data);

        addTimeSlots(openTime,closingTime,phone);
    }

    private void addTimeSlots(String s1,String s2,String phone){
        String t1 = convert12HourTo24HourTime(s1);
        String t2 = convert12HourTo24HourTime(s2);

        int hrOpen = Integer.parseInt(t1.substring(0, 2));
        int hrClose = Integer.parseInt(t2.substring(0, 2));
        int minOpen = Integer.parseInt(t1.substring(3, 5));
        int minClose = Integer.parseInt(t2.substring(3, 5));

        HashMap<String, Integer> hashMap = new HashMap<>();
        int hr = hrOpen;
        int min = minOpen;
        String temp1 =s1;
        String temp2="";

        while (hr < hrClose) {
            min = min + 15;
            if (min >= 60) {
                min = min - 60;
                hr = hr + 1;
            }

            String strhr = "";
            if (hr >= 0 && hr <= 9)
                strhr = "0" + hr;
            else
                strhr = "" + hr;

            String strmin = "";
            if (min >= 0 && min <= 9)
                strmin = "0" + min;
            else
                strmin = "" + min;

            temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
            hashMap.put(temp1+" - "+temp2,0);
            temp1=temp2;

        }
        //   hr=hrClose
        while (min < minClose) {
            min = min + 15;

            String strhr = "";
            if (hr >= 0 && hr <= 9)
                strhr = "0" + hr;
            else
                strhr = "" + hr;

            String strmin = "";
            if (min >= 0 && min <= 9)
                strmin = "0" + min;
            else
                strmin = "" + min;

            temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
            hashMap.put(temp1+" - "+temp2,0);
            temp1=temp2;
        }

        FirebaseFirestore.getInstance().collection("TimeSlots").document(phone).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "TimeSlots added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Error occured!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String convert12HourTo24HourTime(String s) { //input format "hh:mm a"  output format "hh:mm"
        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        char a = s.charAt(6);

        if (a == 'P' && hr != 12)
            hr = hr + 12;
        else if (a == 'A' && hr == 12)
            hr = 0;

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin);

    }

    public String convert24HourTo12HourTime(String s) { //input format "hh:mm"  output format "hh:mm a"

        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        String a="";

        if(hr==12){
            a="PM";
        }
        else if(hr==0){
            hr=12;
            a="AM";
        }
        else if(hr>=13){
            hr=hr-12;
            a="PM";
        }
        else if(hr<=12){
            a="AM";
        }

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin+" "+a);
    }

}