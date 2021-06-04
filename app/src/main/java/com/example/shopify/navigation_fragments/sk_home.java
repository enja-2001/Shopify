package com.example.shopify.navigation_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopify.Cust_Details;
import com.example.shopify.R;
import com.example.shopify.TimeSlots;
import com.example.shopify.adapter.TimeSlotAdapter;
import com.example.shopify.helper.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class sk_home extends Fragment {


    TimeSlotAdapter adapter;
    RecyclerView recyclerView;

    ArrayList<TimeSlots> time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_sk_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerTime);
        getData();

        return root;
    }
    public String convert12HourTo24HourTime(String s) { //input format "hh:mm a"  output format "hh:mm"
        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        char a = s.charAt(6);

        if (a == 'P' && hr != 12)
            hr = hr + 12;
        else if (a == 'A' && hr == 12)
            hr = 0;

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin);

    }

    public String convert24HourTo12HourTime(String s) { //input format "hh:mm"  output format "hh:mm a"

        int hr = Integer.parseInt(s.substring(0, 2));
        int min = Integer.parseInt(s.substring(3, 5));
        String a="";

        if(hr==12){
            a="PM";
        }
        else if(hr==0){
            hr=12;
            a="AM";
        }
        else if(hr>=13){
            hr=hr-12;
            a="PM";
        }
        else if(hr<=12){
            a="AM";
        }

        String strhr = "";
        if (hr >= 0 && hr <= 9)
            strhr = "0" + hr;
        else
            strhr = "" + hr;

        String strmin = "";
        if (min >= 0 && min <= 9)
            strmin = "0" + min;
        else
            strmin = "" + min;

        return (strhr + ":" + strmin+" "+a);
    }

    private void getData() {

        String ph = SharedPrefManager.getInstance(getContext()).getShopPH();
        FirebaseFirestore.getInstance().collection("Timeslots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                String s1 = "09:00 AM";
                String s2 = "10:00 PM";
                String t1 = convert12HourTo24HourTime(s1);
                String t2 = convert12HourTo24HourTime(s2);

                int hrOpen = Integer.parseInt(t1.substring(0, 2));
                int hrClose = Integer.parseInt(t2.substring(0, 2));
                int minOpen = Integer.parseInt(t1.substring(3, 5));
                int minClose = Integer.parseInt(t2.substring(3, 5));

                int hr = hrOpen;
                int min = minOpen;
                String temp1 = s1;
                String temp2 = "";

                while (hr < hrClose) {
                    min = min + 15;
                    if (min >= 60) {
                        min = min - 60;
                        hr = hr + 1;
                    }

                    String strhr = "";
                    if (hr >= 0 && hr <= 9)
                        strhr = "0" + hr;
                    else
                        strhr = "" + hr;

                    String strmin = "";
                    if (min >= 0 && min <= 9)
                        strmin = "0" + min;
                    else
                        strmin = "" + min;

                    temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
                    int cust = Integer.parseInt(doc.getString(temp1 + " - " + temp2));
                    time.add(new TimeSlots(temp1 + " - " + temp2, cust));
                    temp1 = temp2;

                }
                //   hr=hrClose
                while (min < minClose) {
                    min = min + 15;

                    String strhr = "";
                    if (hr >= 0 && hr <= 9)
                        strhr = "0" + hr;
                    else
                        strhr = "" + hr;

                    String strmin = "";
                    if (min >= 0 && min <= 9)
                        strmin = "0" + min;
                    else
                        strmin = "" + min;

                    temp2 = convert24HourTo12HourTime(strhr + ":" + strmin);
                    int cust = Integer.parseInt(doc.getString(temp1 + " - " + temp2));
                    time.add(new TimeSlots(temp1 + " - " + temp2, cust));
                    temp1 = temp2;

                }
            recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new TimeSlotAdapter(time, getContext());
                recyclerView.setAdapter(adapter);

                adapter.setOnClickListener(new TimeSlotAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        Intent intent = new Intent(requireActivity(), Cust_Details.class);
                        intent.putExtra("time", time.get(position).getTime());
                        intent.putExtra("value", time.get(position).getValue());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}