package com.example.firebase_6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase_6.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_2 extends Fragment  {

    public Fragment_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_2, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());


        adapter.addFrag(new Ongoing(), "Ongoing");
        adapter.addFrag(new History(), "History");

        viewPager.setAdapter(adapter);
    }
}
