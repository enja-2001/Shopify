package com.example.shopify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopify.SK_Dashboard;
import com.example.shopify.helper.SharedPrefManager;


import com.example.shopify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.Executor;


public class SK_Login extends Fragment {

    private FirebaseAuth auth;


    EditText email_id;
    EditText password;
    TextView sign_up;
    Button login;
    ProgressBar pg;
    TextView welc;
    TextView welc2;
    EditText shop_ph;

    @Override
    public void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(requireContext()).isUserLoggedIn())
        {
            Intent intent = new Intent(getContext(), SK_Dashboard.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sk_login, container, false);

        email_id = root.findViewById(R.id.etUsername);
        password = root.findViewById(R.id.etPassword);
        sign_up = root.findViewById((R.id.butRegister));
        login = root.findViewById((R.id.butLogin));
        pg = root.findViewById(R.id.progressBarReviews);

        pg.setVisibility(View.GONE);

        welc = root.findViewById(R.id.textView11);
        welc2 = root.findViewById(R.id.textView16);
        shop_ph = root.findViewById(R.id.sk_ph);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welc.setVisibility(View.GONE);
                welc2.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
                sign_up.setVisibility(View.GONE);
                email_id.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                shop_ph.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);

                String email = email_id.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty())
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                else {

                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener( requireActivity(), new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPrefManager.getInstance(getContext()).login();
                                getData(email, shop_ph.getText().toString());
                                Toast.makeText(getContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), SK_Dashboard.class);
                                startActivity(intent);

                            } else
                            {
                                Toast.makeText(getContext(), "Email id not found. Please Register!", Toast.LENGTH_LONG).show();
                                welc.setVisibility(View.VISIBLE);
                                welc2.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);
                                sign_up.setVisibility(View.VISIBLE);
                                email_id.setVisibility(View.VISIBLE);
                                password.setVisibility(View.VISIBLE);
                                shop_ph.setVisibility(View.VISIBLE);
                                pg.setVisibility(View.GONE);

                            }
                        }
                    });
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SK_Register();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return root;
    }

    private void getData(String email, String phone) {

        FirebaseFirestore.getInstance().collection("Shops").document(phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                String name = doc.getString("Shopkeeper");
                String shname = doc.getString("Name");
                String address = doc.getString("Address") + " " + doc.getString("PIN code");
                SharedPrefManager.getInstance(getContext()).loginUser(name, email, shname, address, phone);
            }
        });
    }
}