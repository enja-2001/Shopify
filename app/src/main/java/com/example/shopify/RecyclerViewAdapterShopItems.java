package com.example.shopify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class RecyclerViewAdapterShopItems extends RecyclerView.Adapter<RecyclerViewAdapterShopItems.MyViewHolder> {

    ArrayList<HashMap<String,HashMap>> al;
    Context context;
    int lastPosition;
    public ArrayList<OrderNode> alOrderList;



    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvItem;
        public RecyclerView innerRecyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=(TextView)itemView.findViewById(R.id.single_row_address_name);
            innerRecyclerView=(RecyclerView) itemView.findViewById(R.id.innerRecyclerView);
        }
    }


    public RecyclerViewAdapterShopItems(ArrayList<HashMap<String,HashMap>> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
        alOrderList=new ArrayList<>();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shop_items_outer_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HashMap<String,HashMap> map=al.get(position);
        String key="";
        HashMap<String,HashMap> innerHashMap=new HashMap<String,HashMap>();

        for(Map.Entry m:map.entrySet()){
            key=(String)m.getKey();
            innerHashMap=(HashMap<String, HashMap>)m.getValue();
        }

        holder.tvItem.setText(key);
        ArrayList<HashMap<String,HashMap>> inneral=new ArrayList<>();

        for(Map.Entry m:innerHashMap.entrySet()){
            key=(String)m.getKey();
            HashMap<String,HashMap> hashMap=new HashMap<>();
            hashMap.put(key,(HashMap)m.getValue());
            inneral.add(hashMap);
        }


        holder.innerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        holder.innerRecyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterShopItems_InnerList recyclerViewAdpater=new RecyclerViewAdapterShopItems_InnerList(inneral,context,new RecyclerViewAdapterShopItems_InnerList.OnItemCheckListener(){

            @Override
            public void onItemCheck(OrderNode orderNode) {
                alOrderList.add(orderNode);
                Log.d("ArrayListSize",""+alOrderList.size());
//                Toast.makeText(context, al.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemUncheck(OrderNode orderNode) {
                alOrderList.remove(orderNode);
                Log.d("ArrayListSize",""+alOrderList.size());

//                Toast.makeText(context, al.size(), Toast.LENGTH_SHORT).show();

            }
        });
        holder.innerRecyclerView.setAdapter(recyclerViewAdpater);


        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    private void setAnimation(View view,int position){

//        if(position>lastPosition) {                     //If the current view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.startAnimation(animation);
//            lastPosition=position;
    }
}

