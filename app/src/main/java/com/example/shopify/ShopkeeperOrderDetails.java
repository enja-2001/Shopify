package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopkeeperOrderDetails extends AppCompatActivity {
    OngoingOrderModel obj;
    ArrayList<OrderNode> alOrderNode;

    TextView tvName;
    TextView tvAddress;
    TextView tvPhoneNumber;
    TextView tvTimeSlot;
    TextView tvOrderId;
    TextView tvTotal;
    TextView tvRemaining;
    TextView tvAdvanced;
    Button butOrderPicked;
    RecyclerView recyclerView;
    RecyclerViewOrderConfirmationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_order_details);

        obj= (OngoingOrderModel) getIntent().getSerializableExtra("obj");
        alOrderNode = (ArrayList<OrderNode>)(obj.al);

        tvOrderId=findViewById(R.id.tvOrderId);
        tvName=findViewById(R.id.tvAgeWorkerDetails);
        tvAddress=findViewById(R.id.tvProfessionWorkerDetails);
        tvPhoneNumber=findViewById(R.id.tvGenderWorkerDetails);
        tvTimeSlot=findViewById(R.id.tvNameWorkerDetails);
        recyclerView=findViewById(R.id.recyclerView);
        tvTotal=findViewById(R.id.tvTotalPrice);
        tvRemaining=findViewById(R.id.tvRemainingPrice);
        tvAdvanced=findViewById(R.id.tvAdvancedPrice);
        butOrderPicked=(Button)findViewById(R.id.butOrderPicked);

        tvOrderId.setText(obj.orderId);
        tvTimeSlot.setText(obj.timeSlot);
        tvPhoneNumber.setText("+91 "+obj.shopPhoneNumber);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopkeeperOrderDetails.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewOrderConfirmationAdapter(alOrderNode, ShopkeeperOrderDetails.this);
        recyclerView.setAdapter(adapter);

        int total=Integer.parseInt(obj.totalPrice);
        int remaining=Integer.parseInt(obj.remainingPrice);

        tvTotal.setText(""+total);
        tvRemaining.setText(""+remaining);
        tvAdvanced.setText(""+(total-remaining));

//        Log.d("InsideOngoing",""+alOrderNode.get(0));

        adapter.notifyDataSetChanged();

        String shopkeeperPhoneNumber;
        /******
         shopkeeperPhoneNumber = phone number of shopkeeper
         *****/
        shopkeeperPhoneNumber="4444444444";

        butOrderPicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore.getInstance().collection("Ongoing Orders").document(obj.orderId).update("Status","History").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            finish();
                        } else {
                            Toast.makeText(ShopkeeperOrderDetails.this, "Error occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}