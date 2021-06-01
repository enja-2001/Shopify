package com.example.shopify;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

class RecyclerViewOrderConfirmationAdapter extends RecyclerView.Adapter<RecyclerViewOrderConfirmationAdapter.MyViewHolder> {

    ArrayList<OrderNode> al;
    Context context;
    int lastPosition;

    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvItem;
        public TextView tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=(TextView)itemView.findViewById(R.id.tvItem);
            tvPrice=(TextView)itemView.findViewById(R.id.tvPrice);
        }
    }

    public RecyclerViewOrderConfirmationAdapter(ArrayList<OrderNode> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_confirmation,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Log.d("InsideOngoing",""+al);
        Log.d("InsideOngoing",""+al.get(0));


        holder.tvItem.setText(""+al.get(position).subCategory);
        Log.d("Inside",""+al.get(position).subCategory);

        String pricePerPiece=al.get(position).price;

        if(pricePerPiece.isEmpty())
            holder.tvPrice.setText("100");
        else {
            StringTokenizer token = new StringTokenizer(pricePerPiece);
            int price = Integer.parseInt(token.nextToken());
            int quantity = Integer.parseInt(al.get(position).quantity);
            holder.tvPrice.setText("" + (price * quantity));
        }
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

