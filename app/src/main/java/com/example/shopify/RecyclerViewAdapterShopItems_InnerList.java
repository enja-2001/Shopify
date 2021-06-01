package com.example.shopify;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class RecyclerViewAdapterShopItems_InnerList extends RecyclerView.Adapter<RecyclerViewAdapterShopItems_InnerList.MyViewHolder>{

    ArrayList<HashMap<String ,HashMap>> al;
    Context context;
    int lastPosition;
    public OnItemCheckListener onItemCheckListener;




    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvSubCategory;
        public TextView tvPrice;
        public TextView tvDescription;
        public CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubCategory=(TextView)itemView.findViewById(R.id.single_row_address_name);
            tvPrice=(TextView)itemView.findViewById(R.id.single_row_address_phone_number);
            tvDescription=(TextView)itemView.findViewById(R.id.single_row_address_user);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    interface OnItemCheckListener{
        void onItemCheck(OrderNode orderNode);
        void onItemUncheck(OrderNode orderNode);
    }

    public RecyclerViewAdapterShopItems_InnerList(ArrayList<HashMap<String,HashMap>> al, Context context,OnItemCheckListener onItemCheckListener){
        this.al=al;
        this.context=context;
        this.onItemCheckListener=onItemCheckListener;
        lastPosition=-1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shop_items_inner_list,parent,false);

        return new MyViewHolder(view);
    }

    HashMap<String,HashMap> hashMap;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        hashMap=new HashMap<>();
        hashMap=al.get(position);
        String key="";

        HashMap<String,String> innerHashMap=new HashMap<>();

        for(Map.Entry m:hashMap.entrySet()){
            key=(String)m.getKey();
            innerHashMap=(HashMap<String,String>)m.getValue();
        }
        holder.tvSubCategory.setText(key);

       holder.tvPrice.setText(innerHashMap.get("Price"));

//        holder.tvDescription.append(""+al.size());


        if(!innerHashMap.get("Description").isEmpty())
           holder.tvDescription.setText(innerHashMap.get("Description"));
       else
           holder.tvDescription.setVisibility(View.GONE);

       String quantity="1";
        OrderNode orderNode=new OrderNode(key,innerHashMap.get("Description"),innerHashMap.get("Price"),innerHashMap.get("Image"),quantity);

        setAnimation(holder.itemView, position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked())
                    onItemCheckListener.onItemCheck(orderNode);
                else
                    onItemCheckListener.onItemUncheck(orderNode);
            }
        });

//        holder.radioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               if(!holder.radioButton.isChecked())
//                   holder.radioButton.setChecked(true);
//               else
//                   holder.radioButton.setChecked(false);
//
//            }
//
//        });

//        holder.checkBox.setOnCheckedChangeListener(null);    //this will prevent any unwanted selections
//
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                    holder.checkBox.setChecked(false);
//                else
//                    holder.checkBox.setChecked(true);
//
//            }
//        });

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

