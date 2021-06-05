package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopify.adapter.CustomerAdapter;
import com.example.shopify.helper.Orders;
import com.example.shopify.helper.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class Cust_Details extends AppCompatActivity {

    private TextView timetxt;
    TextView tvNotificationEmpty;
    ImageView ivNotificationEmpty;
    private Button back;
    private RecyclerView recyclerView;
//    private CustomerAdapter adapter;
    String phoneNumber;
    ArrayList<Orders> orders;
    ArrayList<OngoingOrderModel> alOngoing;
    ShopkeeperRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_details);

        String time = getIntent().getStringExtra("time");
        Long cust = getIntent().getLongExtra("value", 0);
        phoneNumber = SharedPrefManager.getInstance(Cust_Details.this).getShopPH();

        alOngoing = new ArrayList<>();
        tvNotificationEmpty = (TextView) findViewById(R.id.tvOngoingEmpty);
        ivNotificationEmpty = (ImageView) findViewById(R.id.ivOngoingEmpty);
        timetxt = findViewById(R.id.timecust);
        timetxt.setText(time);

        recyclerView = findViewById(R.id.recyclerCust);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cust_Details.this));

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);
        ivNotificationEmpty.setVisibility(View.GONE);

        getOngoingOrder(phoneNumber, time);
    }

    private void getOngoingOrder(String ph,String timeSlot){

        FirebaseFirestore.getInstance().collection("Ongoing Orders")
                .whereEqualTo("Shop phone number",ph)
                .whereEqualTo("Time slot",timeSlot)
                .whereEqualTo("Status","Ongoing")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        String orderId=documentSnapshot.getId();
                        String date=documentSnapshot.getString("Date");
                        String totalPrice=documentSnapshot.getString("Total price");
                        String remainingPayment=documentSnapshot.getString("Remaining payment");
                        String shopAddress=documentSnapshot.getString("Shop Address");
                        String shopName=documentSnapshot.getString("Shop Name");
                        String shopPhoneNumber=documentSnapshot.getString("Shop phone number");
                        String timeSlot=documentSnapshot.getString("Time slot");
                        String userPhoneNumber=documentSnapshot.getString("User phone number");
                        ArrayList<HashMap<String,String>> al=(ArrayList<HashMap<String,String>>)documentSnapshot.get("Order list");


                        int len=al.size();
                        int i=0;
                        HashMap<String,String> hashMap=new HashMap<>();
                        ArrayList<OrderNode> alOrderNode=new ArrayList<>();

                        for(i=0;i<=len-1;i++){
                            hashMap=al.get(i);
                            String nm = hashMap.get("subCategory");
                            String quantity=hashMap.get("quantity");
                            String price=hashMap.get("price");

                            if(price.isEmpty())
                                price="100";

                            OrderNode ob=new OrderNode(nm,"",price,"",quantity);
                            alOrderNode.add(ob);
                        }

                        OngoingOrderModel ongoingOrderModel=new OngoingOrderModel(totalPrice,remainingPayment,orderId,shopName,alOrderNode,timeSlot,userPhoneNumber,date,shopAddress);
                        alOngoing.add(ongoingOrderModel);
                    }

                    Log.d("sizeDD",""+alOngoing.size());

                    if(alOngoing.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNotificationEmpty.setVisibility(View.GONE);
                        ivNotificationEmpty.setVisibility(View.GONE);
                        adapter = new ShopkeeperRecyclerViewAdapter(alOngoing, Cust_Details.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        tvNotificationEmpty.setVisibility(View.VISIBLE);
                        ivNotificationEmpty.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(Cust_Details.this, "Oops! Some error occured...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
