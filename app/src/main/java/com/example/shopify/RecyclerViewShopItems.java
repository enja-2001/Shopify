package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewShopItems extends AppCompatActivity {

    String shopName;
    String shopPhoneNumber;
    TextView tvTitle;
    Button butProceed;
    RecyclerView recyclerView;
    RecyclerViewAdapterShopItems recyclerViewAdpater;
    ArrayList<HashMap<String,HashMap>> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_shop_items);


        shopName=getIntent().getStringExtra("Title");
        tvTitle=(TextView)findViewById(R.id.tvToolbarTitle);
        tvTitle.setText(shopName);

        butProceed=(Button)findViewById(R.id.butProceed);

        al=new ArrayList<>();

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        shopPhoneNumber=sharedPreferences.getString("Shop Phone Number",null);

        recyclerView = (RecyclerView)findViewById(R.id.addressRecyclerView);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerViewAdpater=new RecyclerViewAdapterShopItems(al,this);
        recyclerView.setAdapter(recyclerViewAdpater);

        FirebaseFirestore.getInstance().collection("Shops").document(shopPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    Map<String,Object> map=documentSnapshot.getData();
                    HashMap<String,HashMap> hashMap=new HashMap<>();


                    for(Map.Entry<String,Object> entry:map.entrySet()){
                        if(entry.getKey().equals("Items")){
//                            al=(ArrayList<HashMap<String, HashMap>>)entry.getValue();
                            hashMap=(HashMap<String,HashMap>)entry.getValue();
                        }
                    }

                    for(Map.Entry<String,HashMap> entry:hashMap.entrySet()){
                        String key=(String)entry.getKey();
//                        newHashMap.put(key,(HashMap<String,HashMap<String,String>>)entry.getValue());
                        HashMap newHashMap=new HashMap();
                        newHashMap.put(key,(HashMap)entry.getValue());

                        al.add(newHashMap);
                        recyclerViewAdpater.notifyDataSetChanged();

                    }
                }
                else{
                    Toast.makeText(RecyclerViewShopItems.this, "Oops! Some error has occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        butProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecyclerViewShopItems.this,timeSlotInput.class);
                intent.putExtra("OrderList",recyclerViewAdpater.alOrderList);
                startActivity(intent);
            }
        });
    }
}