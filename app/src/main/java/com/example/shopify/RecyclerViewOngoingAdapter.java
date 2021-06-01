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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class RecyclerViewOngoingAdapter extends RecyclerView.Adapter<RecyclerViewOngoingAdapter.MyViewHolder> {

    ArrayList<OngoingOrderModel> al;
    Context context;
    int lastPosition;

    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvDate;
        public TextView tvOrderId;
        public TextView tvShopName;
        public TextView tvPhoneNumber;
        public TextView tvAddress;
        public TextView tvTimeSlot;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvOrderId=(TextView)itemView.findViewById(R.id.tvOrderId);
            tvAddress=(TextView)itemView.findViewById(R.id.tvAddres);
            tvPhoneNumber=(TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvShopName=(TextView) itemView.findViewById(R.id.tvShopName);
            tvTimeSlot=(TextView) itemView.findViewById(R.id.tvTimeSlot);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }


    public RecyclerViewOngoingAdapter(ArrayList<OngoingOrderModel> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_ongoing,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvAddress.setText(al.get(position).address);
        holder.tvOrderId.setText("Order Id - "+al.get(position).orderId);
        holder.tvShopName.setText(al.get(position).shopName);
        holder.tvPhoneNumber.setText("Phone Number  +91 "+al.get(position).shopPhoneNumber);
        holder.tvDate.setText(al.get(position).date);
        holder.tvTimeSlot.setText("Time Slot - "+al.get(position).timeSlot);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OrderDetailsActivity.class);
                intent.putExtra("obj",al.get(position));
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

