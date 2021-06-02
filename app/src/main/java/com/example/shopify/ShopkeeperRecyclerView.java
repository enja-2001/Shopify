package com.example.shopify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopkeeperRecyclerView extends AppCompatActivity {

    TextView tvNotificationEmpty;
    ImageView ivNotificationEmpty;
    RecyclerView recyclerView;

    ArrayList<OngoingOrderModel> alOngoing;
    String phoneNumber;

    ShopkeeperRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_recycler_view);


        tvNotificationEmpty=(TextView)findViewById(R.id.tvOngoingEmpty);
        ivNotificationEmpty=(ImageView)findViewById(R.id.ivOngoingEmpty);
        recyclerView=(RecyclerView)findViewById(R.id.ongoingRecyclerView);

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);
        ivNotificationEmpty.setVisibility(View.GONE);

        alOngoing = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopkeeperRecyclerView.this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ShopkeeperRecyclerView.this);

        /******
        phoneNumber = phone number of shopkeeper
         *****/

        phoneNumber="4444444444";
        getOngoingOrder(phoneNumber);

    }

    private void getOngoingOrder(String ph){

        FirebaseFirestore.getInstance().collection("Ongoing Orders")
                .whereEqualTo("Shop phone number",ph)
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

                    ///////// just for fun testing

                    if(alOngoing.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNotificationEmpty.setVisibility(View.GONE);
                        ivNotificationEmpty.setVisibility(View.GONE);
                        adapter = new ShopkeeperRecyclerViewAdapter(alOngoing, ShopkeeperRecyclerView.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        tvNotificationEmpty.setVisibility(View.VISIBLE);
                        ivNotificationEmpty.setVisibility(View.VISIBLE);
                    }

                }
                else{
                    Toast.makeText(ShopkeeperRecyclerView.this, "Oops! Some error occured...", Toast.LENGTH_SHORT).show();
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