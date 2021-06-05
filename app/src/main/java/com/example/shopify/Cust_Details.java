package com.example.shopify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopify.adapter.CustomerAdapter;
import com.example.shopify.helper.Orders;
import com.example.shopify.helper.SharedPrefManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class Cust_Details extends AppCompatActivity {

    private TextView timetxt;
    private Button back;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    ArrayList<Orders> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_details);

         Bundle bundle = getIntent().getExtras();

        String time = bundle.getString("time");
        int cust = bundle.getInt("value");
        timetxt = findViewById(R.id.timecust);
        timetxt.setText(time);
        back = findViewById(R.id.backcust);

        recyclerView = findViewById(R.id.recyclerCust);

        getData(cust, time);
    }

    public void getData(int cust, String time) {

        String shop = SharedPrefManager.getInstance(Cust_Details.this).getShopPH();
        Task<QuerySnapshot> doc = FirebaseFirestore.getInstance().collection("Ongoing Orders").get();
        doc.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String t = "", sp = "";
                int c = 0;
                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                {
                    t = documentSnapshot.getString("Time Slot");
                    sp = documentSnapshot.getString("Shop phone number");
                    if(t.equals(time) && sp.equals(shop))
                    {
                        c++;
                        String userph = documentSnapshot.getString("User phone number");
                        int rem = Integer.parseInt(documentSnapshot.getString("Remaining payment"));
                        int price = Integer.parseInt(documentSnapshot.getString("Total price"));
                        orders.add(new Orders(t, rem, userph, price));

                    }
                }

            }

        });

//        FirebaseFirestore.getInstance().collection("Ongoing")

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cust_Details.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerAdapter(orders, Cust_Details.this);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Cust_Details.this, ShopkeeperOrderDetails.class);
                startActivity(intent);
            }
        });


    }
}
