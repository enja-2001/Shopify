package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shopify.helper.SharedPrefManager;
import com.example.shopify.navigation_fragments.covid;
import com.example.shopify.navigation_fragments.sk_add;
import com.example.shopify.navigation_fragments.sk_home;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SK_Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView name, shopName, shopAddr;
    SharedPrefManager sharedPrefManager;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sk_dashboard);
        name = findViewById(R.id.name);

        shopName = findViewById(R.id.shopName);
        shopAddr = findViewById(R.id.shopAddr);
        sharedPrefManager = new SharedPrefManager(SK_Dashboard.this);

        name.setText(sharedPrefManager.getName());
        shopName.setText(sharedPrefManager.getShopName());
        shopAddr.setText(sharedPrefManager.getShopAddress());

        nav = findViewById(R.id.bottomnav);
        nav.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState==null) {
            nav.getMenu().findItem(R.id.time).setChecked(true);
            getSupportFragmentManager().beginTransaction().add(R.id.container_dashboard, new sk_home()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.time:
                sk_home sk = new sk_home();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_dashboard, sk).commit();
                break;

            case R.id.add:
                sk_add add = new sk_add();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_dashboard, add).commit();
                break;

            case R.id.logout:
                AlertDialog.Builder dialogue = new AlertDialog.Builder(SK_Dashboard.this).setTitle("Confirmation!").setMessage("Do you want to logout? ");
                dialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPrefManager.getInstance(SK_Dashboard.this).logout();
                        finishAffinity();
                    }
                });
                dialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(SK_Dashboard.this, SK_Dashboard.class));
                    }
                });
                AlertDialog alert = dialogue.create();
                alert.show();
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
        nav.getMenu().findItem(R.id.time).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
