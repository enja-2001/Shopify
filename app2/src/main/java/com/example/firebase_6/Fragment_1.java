package com.example.firebase_6;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.firebase_6.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_1 extends Fragment {

    ViewFlipper flipper;
    ImageView iv;

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




    public Fragment_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_1, container, false);

        linearLayoutMaids=(LinearLayout)view.findViewById(R.id.linearLayoutMaids);
        linearLayoutCooks=(LinearLayout)view.findViewById(R.id.linearLayoutCooks);
        linearLayoutMasons=(LinearLayout)view.findViewById(R.id.linearLayoutMasons);

        linearLayoutPlumbers=(LinearLayout)view.findViewById(R.id.linearLayoutPlumbers);

        linearLayoutElectricians=(LinearLayout)view.findViewById(R.id.linearLayoutElectricians);

        linearLayoutDeliveryBoys=(LinearLayout)view.findViewById(R.id.linearLayoutDeliveryBoys);

        linearLayoutShopkeeperStaffs=(LinearLayout)view.findViewById(R.id.linearLayoutShopkeeperStaffs);

        linearLayoutDrivers=(LinearLayout)view.findViewById(R.id.linearLayoutDrivers);

        linearLayoutPainters=(LinearLayout)view.findViewById(R.id.linearLayoutPainters);

        linearLayoutCarpenters=(LinearLayout)view.findViewById(R.id.linearLayoutCarpenters);



        /*flipper=view.findViewById(R.id.flipper);


        int imgarr[]={R.drawable.slide_image_one,R.drawable.slide_image_two,R.drawable.slide_image_three,R.drawable.slide_image_four,R.drawable.slide_image_five};

        int i=0;
        for(i=0;i<=imgarr.length-1;i++)
            flipperImages(imgarr[i]);*/



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







        return view;
    }
    /*public void flipperImages(int image){
        ImageView iv=new ImageView(getContext());
        iv.setBackgroundResource(image);

        flipper.addView(iv);
        flipper.setFlipInterval(5000);
        flipper.setAutoStart(true);

        flipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
        flipper.setInAnimation(getContext(),android.R.anim.slide_in_left);


    }*/


}
