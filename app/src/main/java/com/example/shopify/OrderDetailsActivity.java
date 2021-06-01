package com.example.shopify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {
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
    RecyclerView recyclerView;
    RecyclerViewOrderConfirmationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);

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

        tvOrderId.setText(obj.orderId);
        tvTimeSlot.setText(obj.timeSlot);
        tvPhoneNumber.setText("+91 "+obj.shopPhoneNumber);
        tvAddress.setText(obj.address);
        tvName.setText(obj.shopName);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewOrderConfirmationAdapter(alOrderNode, OrderDetailsActivity.this);
        recyclerView.setAdapter(adapter);

        int total=Integer.parseInt(obj.totalPrice);
        int remaining=Integer.parseInt(obj.remainingPrice);

        tvTotal.setText(""+total);
        tvRemaining.setText(""+remaining);
        tvAdvanced.setText(""+(total-remaining));

//        Log.d("InsideOngoing",""+alOrderNode.get(0));

        adapter.notifyDataSetChanged();

    }
}