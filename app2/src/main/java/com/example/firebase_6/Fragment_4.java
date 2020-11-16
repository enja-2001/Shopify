package com.example.firebase_6;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_4 extends Fragment {
    LinearLayout linearLayoutMaids;
    LinearLayout linearLayoutCooks;

    LinearLayout linearLayoutMasons;

    LinearLayout linearLayoutPlumbers;

    LinearLayout linearLayoutElectricians;
    LinearLayout linearLayoutDeliveryBoys;
    LinearLayout linearLayoutShopkeeperStaffs;
    LinearLayout linearLayoutDrivers;
    LinearLayout linearLayoutPainters;
    LinearLayout linearLayoutCarpenters;
    LinearLayout linearLayoutBeauticians;
    LinearLayout linearLayoutCleaners;
    LinearLayout linearLayoutGardeners;
    LinearLayout linearLayoutCatereers;












    public Fragment_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_4, container, false);

        linearLayoutBeauticians=view.findViewById(R.id.linearLayoutBeauticiansExplore);
        linearLayoutCarpenters=view.findViewById(R.id.linearLayoutCarpentersExplore);

        linearLayoutMaids=view.findViewById(R.id.linearLayoutMaidsExplore);
        linearLayoutCooks=view.findViewById(R.id.linearLayoutCooksExplore);
        linearLayoutMasons=view.findViewById(R.id.linearLayoutMasonsExplore);
        linearLayoutPlumbers=view.findViewById(R.id.linearLayoutPlumbersExplore);
        linearLayoutElectricians=view.findViewById(R.id.linearLayoutElectriciansExplore);
        linearLayoutDeliveryBoys=view.findViewById(R.id.linearLayoutDeliveryBoysExplore);
        linearLayoutShopkeeperStaffs=view.findViewById(R.id.linearLayoutShopkeeperStaffsExplore);
        linearLayoutDrivers=view.findViewById(R.id.linearLayoutDriversExplore);
        linearLayoutPainters=view.findViewById(R.id.linearLayoutPaintersExplore);

        linearLayoutCleaners=view.findViewById(R.id.linearLayoutCleanersExplore);
        linearLayoutGardeners=view.findViewById(R.id.linearLayoutGardenersExplore);
        linearLayoutCatereers=view.findViewById(R.id.linearLayoutCatereersExplore);


        linearLayoutMaids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Maids");
                startActivity(intent);

            }
        });

        linearLayoutCooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Cooks");
                startActivity(intent);

            }
        });

        linearLayoutMasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Masons");
                startActivity(intent);

            }
        });

        linearLayoutPlumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Plumbers");
                startActivity(intent);

            }
        });

        linearLayoutElectricians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Electricians");
                startActivity(intent);

            }
        });

        linearLayoutDeliveryBoys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","DeliveryBoys");
                startActivity(intent);

            }
        });

        linearLayoutShopkeeperStaffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","ShopkeeperStaffs");
                startActivity(intent);

            }
        });

        linearLayoutDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Drivers");
                startActivity(intent);

            }
        });

        linearLayoutPainters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Painters");
                startActivity(intent);

            }
        });

        linearLayoutCarpenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Carpenters");
                startActivity(intent);

            }
        });

        linearLayoutBeauticians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Beauticians");
                startActivity(intent);

            }
        });

        linearLayoutCleaners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Cleaners");
                startActivity(intent);

            }
        });

        linearLayoutGardeners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Gardeners");
                startActivity(intent);

            }
        });

        linearLayoutCatereers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),com.example.firebase_6.WorkersRecyclerView.class);
                intent.putExtra("toolbar","Catereers");
                startActivity(intent);
            }
        });















        return view;
    }
}
