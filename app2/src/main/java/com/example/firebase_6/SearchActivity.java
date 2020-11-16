package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        if (Intent.ACTION_SEARCH.equals(getParentActivityIntent().getAction())) {
            String query = getParentActivityIntent().getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
