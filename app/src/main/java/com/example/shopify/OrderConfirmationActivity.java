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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class OrderConfirmationActivity extends AppCompatActivity {

    HashMap<String,Object> hashMap;
    String ph;
    String timeSlot;
    Button butNext;
    Button butBack;
    TextView tvShopName;
    TextView tvShopAddress;
    TextView tvShopNumber;
    TextView tvTimeSlot;
    TextView tvTotalPrice;
    RecyclerView recyclerView;
    RecyclerViewOrderConfirmationAdapter adapter;

    ArrayList<OrderNode> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        hashMap= (HashMap<String,Object>) getIntent().getSerializableExtra("OrderHashMap");
        ph=(String)hashMap.get("Shop phone number");
        timeSlot=(String)hashMap.get("Time slot");
        al=(ArrayList<OrderNode>)hashMap.get("Order list");

        tvShopAddress=findViewById(R.id.tvConfirmationUserName);
        tvShopName=findViewById(R.id.tvConfirmationWorkerName);
        tvShopNumber=findViewById(R.id.tvConfirmationUserPhoneNumber);
        tvTimeSlot=findViewById(R.id.tvConfirmationUserAddress);
        recyclerView=findViewById(R.id.recyclerView);
        butBack=findViewById(R.id.buttonConfirmationBack);
        butNext=findViewById(R.id.buttonConfirmationNext);
        tvTotalPrice=findViewById(R.id.tvTotalPrice);

        tvShopNumber.setText("+91 "+hashMap.get("Shop phone number"));
        tvShopName.setText(""+hashMap.get("Shop Name"));
        tvShopAddress.setText(""+hashMap.get("Shop Address"));
        tvTimeSlot.setText(""+hashMap.get("Time slot"));

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderConfirmationActivity.this, PaymentActivity.class);
                intent.putExtra("OrderHashMap", hashMap);
                startActivity(intent);

            }
        });
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderConfirmationActivity.this,RecyclerViewShopItems.class);
                intent.putExtra("Title",""+hashMap.get("Shop Name"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderConfirmationActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new RecyclerViewOrderConfirmationAdapter(al, OrderConfirmationActivity.this);
        recyclerView.setAdapter(adapter);

        int total=0;

        for(int i=0;i<=al.size()-1;i++){
            String s=al.get(i).price;
            StringTokenizer token = new StringTokenizer(s);

            if(s.isEmpty())
                total=total+100;
            else {
                int price = Integer.parseInt(token.nextToken());
                int quantity = Integer.parseInt(al.get(i).quantity);
                total = total + price * quantity;
            }
        }

        hashMap.put("Total price",""+total);

        tvTotalPrice.setText(""+total);
    }
}