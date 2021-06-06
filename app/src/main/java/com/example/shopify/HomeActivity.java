package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopify.helper.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.messaging.FirebaseMessaging;


public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    NavigationView nv;
    DrawerLayout dl;
    ActionBarDrawerToggle abdt;

    BottomNavigationView bn;
    //MaterialSearchView sv;
    FirebaseAuth ob;

    String phoneNumber;
    ImageView ivNavigation;

    TextView tvNavigationName;
    public static int notificationBadgeCount;
    SharedPreferences.Editor editor;


    public TextView tvNotificationBadge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ob = FirebaseAuth.getInstance();

        nv = (NavigationView) findViewById(R.id.nv);
        bn = (BottomNavigationView) findViewById(R.id.bn);
        // sv=(MaterialSearchView)findViewById(R.id.sv);

        ivNavigation=(ImageView)findViewById(R.id.ivNavigation);

        tvNavigationName = (TextView) nv.getHeaderView(0).findViewById(R.id.tvNavigationName);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();
        tvNavigationName.setText(preferences.getString("First Name", null));


        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, toolbar, R.string.str1, R.string.str2);

        setSupportActionBar(toolbar);

        dl.addDrawerListener(abdt);

//        phoneNumber = getIntent().getStringExtra("data");
        phoneNumber = preferences.getString("Phone Number",null);


        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new HomeFragment()).commit();
            //nv.setCheckedItem(R.id.item4);
        }

        //notificationBadgeCount=10;

        String strNotificationBadge = preferences.getString("notificationBadgeCount", "");

        if (strNotificationBadge == "")
            notificationBadgeCount = 0;
        else
            notificationBadgeCount = Integer.parseInt(strNotificationBadge);


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bn.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(4);

        View notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        tvNotificationBadge = notificationBadge.findViewById(R.id.counter_badge);

        if (notificationBadgeCount > 0) {
            tvNotificationBadge.setText(Integer.toString(notificationBadgeCount));

            itemView.addView(notificationBadge);
        }


        ivNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
            }
        });


        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new HomeFragment()).commit();
                        break;
                    case R.id.item10:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new OrdersFragment()).commit();
                        break;

                }
                return true;
            }
        });
        setNavigationViewListener();
    }

    private void setNavigationViewListener(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if (dl.isDrawerOpen(GravityCompat.START))
                    dl.closeDrawer(GravityCompat.START);
                else
                    dl.openDrawer(GravityCompat.START);
                return true;

            case R.id.item10:
                Intent intent=new Intent(HomeActivity.this,com.example.shopify.UserProfileActivity.class);
                startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.item7:
                Intent i2=new Intent(this,com.example.shopify.UserProfileActivity.class);
                startActivity(i2);
                break;
            case R.id.item8:
                //getSupportFragmentManager().beginTransaction().replace(R.id.myframe,new TestFragment()).commit();
                Toast.makeText(this, "You will be redirected to a webpage", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item9:
                AlertDialog.Builder dialogue = new AlertDialog.Builder(HomeActivity.this).setTitle("Confirmation!").setMessage("Do you want to logout? ");
                dialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ob.signOut();
                        SharedPrefManager.getInstance(HomeActivity.this).logoutCust();
                        finishAffinity();
                    }
                });
                dialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(HomeActivity.this, SK_Dashboard.class));
                    }
                });
                AlertDialog alert = dialogue.create();
                alert.show();

                break;
            case R.id.item11:
                Intent in2=new Intent(this,com.example.shopify.AboutUsActivity.class);
                startActivity(in2);
                break;
        }

        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        abdt.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        abdt.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        else
            finishAffinity();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        tvNavigationName.setText(preferences.getString("First Name",null));
    }
}
