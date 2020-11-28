package com.example.shopify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class RecyclerViewAdapterShopList extends RecyclerView.Adapter<RecyclerViewAdapterShopList.MyViewHolder> {

    ArrayList<Shop> al;
    Context context;
    int lastPosition;



    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvShopName;
        public TextView tvShopkeeperName;
        public TextView tvShopAddress;

        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShopName=(TextView)itemView.findViewById(R.id.tvShopName);
            tvShopkeeperName=(TextView)itemView.findViewById(R.id.tvShopkeeperName);
            tvShopAddress=(TextView)itemView.findViewById(R.id.tvShopAddress);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
        }
    }


    public RecyclerViewAdapterShopList(ArrayList<Shop> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shop_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvShopName.setText(""+al.get(position).name);
        holder.tvShopkeeperName.setText(""+al.get(position).shopkeeper);
        holder.tvShopAddress.setText(""+al.get(position).address);


        setAnimation(holder.itemView, position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("Shop Phone Number",al.get(position).phoneNumber);
                editor.apply();

                Intent intent=new Intent(context,RecyclerViewShopItems.class);
                intent.putExtra("Title",al.get(position).name);
                context.startActivity(intent);
            }
        });

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

