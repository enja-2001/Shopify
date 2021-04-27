package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

public class OrderSuccessful extends AppCompatActivity {

    Button butDone;
    Button butCancel;
    String userph;
    String shopph;
    SharedPreferences preferences;
    int order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userph = preferences.getString("Phone Number", null);
        shopph = preferences.getString("Shop Phone Number", null);
        butDone=(Button)findViewById(R.id.butDone);
        butCancel = (Button) findViewById(R.id.butCancel);
        butDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderSuccessful.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogue = new AlertDialog.Builder(getApplicationContext());
                dialogue.setTitle("Alert!");
                dialogue.setMessage("Do you want to cancel your booking?");
                dialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String ph = preferences.getString("Phone Number", null);
                        String doc = userph.substring(0,4) + shopph.substring(0,4);
                        FirebaseFirestore.getInstance().collection("Orders").document(doc).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap = (HashMap<String, Object>)documentSnapshot.getData();
                                order_id = (int)hashMap.get("Order id");
                            }
                        });

                        dialogInterface.dismiss();
                    }
                });
                dialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(OrderSuccessful.this, OrderSuccessful.class);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = dialogue.create();
                alert.show();
            }
        });
    }
}