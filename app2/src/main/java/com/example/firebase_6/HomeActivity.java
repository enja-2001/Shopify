package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView tvNavigationName;
    public static int notificationBadgeCount;
    SharedPreferences.Editor editor;


    public TextView tvNotificationBadge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        ob=FirebaseAuth.getInstance();

        nv=(NavigationView)findViewById(R.id.nv);
        bn=(BottomNavigationView) findViewById(R.id.bn);
        // sv=(MaterialSearchView)findViewById(R.id.sv);



        tvNavigationName=(TextView)nv.getHeaderView(0).findViewById(R.id.tvNavigationName);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();
        tvNavigationName.setText(preferences.getString("First Name",null));




        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl,toolbar, R.string.str1, R.string.str2);

        setSupportActionBar(toolbar);

        dl.addDrawerListener(abdt);

        phoneNumber=getIntent().getStringExtra("data");

        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();
        abdt.setDrawerArrowDrawable(new HamburgerDrawable(this));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new Fragment_1()).commit();
            //nv.setCheckedItem(R.id.item4);
        }

        //notificationBadgeCount=10;

        String strNotificationBadge=preferences.getString("notificationBadgeCount", "");

        if(strNotificationBadge=="")
            notificationBadgeCount=0;
        else
            notificationBadgeCount=Integer.parseInt(strNotificationBadge);


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bn.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);

        View notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        tvNotificationBadge = notificationBadge.findViewById(R.id.counter_badge);

        if(notificationBadgeCount>0) {
            tvNotificationBadge.setText(Integer.toString(notificationBadgeCount));

            itemView.addView(notificationBadge);
        }





        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        itemView.removeView(notificationBadge);


                        editor.putString("notificationBadgeCount", Integer.toString(0));
                        editor.apply();


                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new Fragment_3()).commit();
                        //---Intent intent=new Intent(HomeActivity.this,com.example.firebase_6.UserProfileActivity.class);
                        //---intent.putExtra("data",phoneNumber);
                        //---startActivity(intent);
                        break;
                    case R.id.item2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new Fragment_4()).commit();
                        break;
                    case R.id.item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new Fragment_1()).commit();
                        break;
                    case R.id.item10:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myframe, new Fragment_2()).commit();
                        break;
                }
                return true;
            }
        });
        setNavigationViewListener();



       /* if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }*/

        /*FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg="Successful";

                        if(!task.isSuccessful())
                            msg="Failed";

                        //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem mi=menu.findItem(R.id.item10);
        sv.setMenuItem(mi);

        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        //--final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.item10));
        //--SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //---searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        //---searchView.setQueryHint(getResources().getString(R.string.search_hint));
        // getMenuInflater().inflate(R.menu.mymenu,menu);
        // return super.onCreateOptionsMenu(menu);


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
                Intent intent=new Intent(HomeActivity.this,com.example.firebase_6.UserProfileActivity.class);
                startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

           /* case R.id.item4:
                getSupportFragmentManager().beginTransaction().replace(R.id.myframe,new Fragment_1()).commit();
                break;
            case R.id.item5:
                getSupportFragmentManager().beginTransaction().replace(R.id.myframe,new Fragment_2()).commit();
                break;
            case R.id.item6:
                Intent i1=new Intent(this,com.example.firebase_6.TestLoginActivity.class);
                startActivity(i1);
                break;*/
            case R.id.item7:
                Intent i2=new Intent(this,com.example.firebase_6.UserProfileActivity.class);
                startActivity(i2);
                break;
            case R.id.item8:
                //getSupportFragmentManager().beginTransaction().replace(R.id.myframe,new TestFragment()).commit();
                Toast.makeText(this, "You will be redirected to a webpage", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item9:
                ob.signOut();
                Intent in=new Intent(this,com.example.firebase_6.OTPActivity.class);
                startActivity(in);
                break;
            case R.id.item11:
                Intent in2=new Intent(this,com.example.firebase_6.AboutUsActivity.class);
                startActivity(in2);
                break;
        }

        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }

    private void setNavigationViewListener(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);

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
        //editor = preferences.edit();
        tvNavigationName.setText(preferences.getString("First Name",null));


    }

}
