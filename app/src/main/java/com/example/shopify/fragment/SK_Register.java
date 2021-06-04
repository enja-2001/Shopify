package com.example.shopify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shopify.R;
import com.example.shopify.SK_Dashboard;
import com.example.shopify.helper.SharedPrefManager;
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
    Button login, register;
    String name, email, shopName, shopAddr, pass, pincode, phone, category;
    SharedPrefManager sharedPrefManager;
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

        login = root.findViewById(R.id.loginBut);
        register = root.findViewById(R.id.register);

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
                emailtxt.setVisibility(View.INVISIBLE);
                nametxt.setVisibility(View.INVISIBLE);
                shopAddrtxt.setVisibility(View.INVISIBLE);
                shopNametxt.setVisibility(View.INVISIBLE);
                passtxt.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);


                progressBar.setVisibility(View.VISIBLE);
                sharedPrefManager = new SharedPrefManager(getContext());
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(name) || TextUtils.isEmpty(shopName) || TextUtils.isEmpty(shopAddr)) {
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                } else {
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
                                shopNametxt.setVisibility(View.VISIBLE);
                                shopAddrtxt.setVisibility(View.VISIBLE);
                                passtxt.setVisibility(View.VISIBLE);
                                register.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);
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
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_out, R.anim.fade_in);
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
        data.put("Closing Time", closingTime);
        data.put("Maximum Customer", maxCus);
        data.put("Name", shopName);
        data.put("Opening Time", openTime);
        data.put("PIN code", pinCode);
        data.put("Phone number", phone);
        data.put("Shopkeeper", name);
        FirebaseFirestore.getInstance().collection("Shops").document(phone).set(data);
    }
}