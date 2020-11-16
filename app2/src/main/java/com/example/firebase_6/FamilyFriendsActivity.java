package com.example.firebase_6;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class FamilyFriendsActivity extends AppCompatActivity {
    Toolbar familyToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_friends);

        familyToolbar=findViewById(R.id.familyToolbar);
        setSupportActionBar(familyToolbar);
        ActionBar ob=getSupportActionBar();
        // ob.setElevation(50);
        ob.setHomeAsUpIndicator(R.drawable.back);
        ob.setHomeButtonEnabled(true);
        ob.setDisplayShowHomeEnabled(true);
        ob.setDisplayHomeAsUpEnabled(true);
        ob.setTitle("");

    }
}
