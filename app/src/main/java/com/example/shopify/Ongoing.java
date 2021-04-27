package com.example.shopify;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ongoing extends Fragment {

    TextView tvNotificationEmpty;
    ImageView ivNotificationEmpty;
    RecyclerView recyclerView;

    ArrayList<OngoingOrderModel> al;
    String phoneNumber;

    RecyclerViewOngoingAdapter adapter;

    public Ongoing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ongoing, container, false);

        tvNotificationEmpty=(TextView)view.findViewById(R.id.tvOngoingEmpty);
        ivNotificationEmpty=(ImageView)view.findViewById(R.id.ivOngoingEmpty);
        recyclerView=(RecyclerView)view.findViewById(R.id.ongoingRecyclerView);

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);
        ivNotificationEmpty.setVisibility(View.GONE);

        al = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

//        adapter=new RecyclerViewOngoingAdapter(al,getContext());
//        recyclerView.setAdapter(adapter);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        phoneNumber = preferences.getString("Phone Number", null);

        FirebaseFirestore.getInstance().collection("Ongoing Orders").whereEqualTo("User phone number",phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult()) {
                        ArrayList<OrderNode> alOrderNode=(ArrayList<OrderNode>)documentSnapshot.get("Order list");
                        String timeSlot = documentSnapshot.getString("Time slot");
                        String shopPhoneNumber = documentSnapshot.getString("Shop phone number");
                        String shopName=documentSnapshot.getString("Shop Name");
                        String shopAddress=documentSnapshot.getString("Shop Address");
                        String date=documentSnapshot.getString("Date");

                        OngoingOrderModel ob=new OngoingOrderModel(shopName,alOrderNode,timeSlot,shopPhoneNumber,date,shopAddress);
                        al.add(ob);

                        Log.d("Inside","YES");
                    }


                    if(al.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNotificationEmpty.setVisibility(View.GONE);
                        ivNotificationEmpty.setVisibility(View.GONE);
                        adapter = new RecyclerViewOngoingAdapter(al, getContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        tvNotificationEmpty.setVisibility(View.VISIBLE);
                        ivNotificationEmpty.setVisibility(View.VISIBLE);
                    }

                }
                else{
                    Toast.makeText(getContext(), "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
