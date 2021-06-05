package com.example.shopify.navigation_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

    private void getData() {

        String ph = SharedPrefManager.getInstance(getContext()).getShopPH();
//        Log.d("shopPHONE",ph);
        FirebaseFirestore.getInstance().collection("TimeSlots").document(ph).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                HashMap<String,Object> hashMap=(HashMap<String, Object>)documentSnapshot.getData();
                populateArrayList(hashMap);
            }
        });
    }


    private void populateArrayList(HashMap<String,Object> hashMap){
        String key="";
        long val=0;
        TimeSlots ob;

        ArrayList<TimeSlots> a1=new ArrayList<>(); //AM to AM
        ArrayList<TimeSlots> a2=new ArrayList<>(); //PM to PM
        ArrayList<TimeSlots> a3=new ArrayList<>();//AM to PM
        ArrayList<TimeSlots> a4=new ArrayList<>();//PM to PM for 12:00


        for(Map.Entry m:hashMap.entrySet()){
            key=(String)m.getKey();
            val=(long)m.getValue();

            ob=new TimeSlots(key,val);

            int len=key.length();

            if(key.charAt(len-2)=='A' && key.charAt(6)=='A')
                a1.add(ob);

            else if(key.charAt(len-2)=='P' && key.charAt(6)=='P'){
                if(key.startsWith("12"))
                    a4.add(ob);
                else
                    a2.add(ob);
            }
            else
                a3.add(ob);
        }

        Collections.sort(a1, new Comparator<TimeSlots>() {
            @Override
            public int compare(TimeSlots o1, TimeSlots o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        Collections.sort(a2, new Comparator<TimeSlots>() {
            @Override
            public int compare(TimeSlots o1, TimeSlots o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        Collections.sort(a3, new Comparator<TimeSlots>() {
            @Override
            public int compare(TimeSlots o1, TimeSlots o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        Collections.sort(a4, new Comparator<TimeSlots>() {
            @Override
            public int compare(TimeSlots o1, TimeSlots o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        a1.addAll(a3);
        a1.addAll(a4);
        a1.addAll(a2);

        time=a1;

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
}