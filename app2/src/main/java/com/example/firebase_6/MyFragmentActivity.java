package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MyFragmentActivity extends AppCompatActivity {

    public static class SampleFragment extends Fragment{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
    }
}
