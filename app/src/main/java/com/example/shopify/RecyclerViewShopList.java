package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewShopList extends AppCompatActivity {

    String category;
    TextView tvTitle;
    RecyclerView recyclerView;
    RecyclerViewAdapterShopList recyclerViewAdpater;
    ArrayList<Shop> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_shop_list);


        category=getIntent().getStringExtra("Category");
        tvTitle=(TextView)findViewById(R.id.tvToolbarTitle);
        tvTitle.setText(category);

        al=new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewShopList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerViewAdpater=new RecyclerViewAdapterShopList(al,this);
        recyclerView.setAdapter(recyclerViewAdpater);

        FirebaseFirestore.getInstance().collection("Shops").whereEqualTo("Category",category).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String cat = documentSnapshot.getString("Category");
                        String img = documentSnapshot.getString("Image");

                        Shop ob = new Shop(documentSnapshot.getString("Address"),documentSnapshot.getString("Category"),documentSnapshot.getString("Closing time"),documentSnapshot.getString("Name"),documentSnapshot.getString("Opening time"),documentSnapshot.getString("PIN code"),documentSnapshot.getString("Phone number"),documentSnapshot.getString("Rating"),documentSnapshot.getString("Rating count"),documentSnapshot.getString("Review count"),documentSnapshot.getString("Shopkeeper"));
                        al.add(ob);

                        recyclerViewAdpater.notifyDataSetChanged();

                    }
//
//                    recyclerViewAdpater=new RecyclerViewAdpaterFragmentHome(al,getContext());
//                    recyclerView.setAdapter(recyclerViewAdpater);

                }
                else{
                    Toast.makeText(RecyclerViewShopList.this, "Error occured in fetching data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}