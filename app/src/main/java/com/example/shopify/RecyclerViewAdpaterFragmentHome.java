package com.example.shopify;

import android.content.Context;
import android.content.Intent;
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

class RecyclerViewAdpaterFragmentHome extends RecyclerView.Adapter<RecyclerViewAdpaterFragmentHome.MyViewHolder> {

    ArrayList<ShopCategories> al;
    Context context;
    int lastPosition;



    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public ImageView iv;
        public TextView tv;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=(ImageView)itemView.findViewById(R.id.ivSingleRowFragmentHome);
            tv=(TextView)itemView.findViewById(R.id.tvSingleRowFragmentHome);
            cardView=(CardView)itemView.findViewById(R.id.cardViewSingleRowFragmentHome);
        }
    }


    public RecyclerViewAdpaterFragmentHome(ArrayList<ShopCategories> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_fragment_home,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(""+al.get(position).category);

        if(al.get(position).imgstr!=null)
            Picasso.get().load(al.get(position).imgstr).into(holder.iv);
        setAnimation(holder.itemView, position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,RecyclerViewShopList.class);
                intent.putExtra("Category",al.get(position).category);
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

