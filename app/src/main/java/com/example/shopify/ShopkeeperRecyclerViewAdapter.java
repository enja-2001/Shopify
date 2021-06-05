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

class ShopkeeperRecyclerViewAdapter extends RecyclerView.Adapter<ShopkeeperRecyclerViewAdapter.MyViewHolder> {

    ArrayList<OngoingOrderModel> al;
    Context context;
    int lastPosition;

    public static class MyViewHolder extends RecyclerView.ViewHolder {  //view holder class

        public TextView tvDate;
        public TextView tvOrderId;
        public TextView tvPhoneNumber;
        public TextView tvTimeSlot;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvOrderId=(TextView)itemView.findViewById(R.id.tvOrderId);
            tvPhoneNumber=(TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvTimeSlot=(TextView) itemView.findViewById(R.id.tvTimeSlot);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }


    public ShopkeeperRecyclerViewAdapter(ArrayList<OngoingOrderModel> al, Context context){
        this.al=al;
        this.context=context;
        lastPosition=-1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shopkeeper,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvOrderId.setText("Order Id - "+al.get(position).orderId);
        holder.tvPhoneNumber.setText("Phone Number  +91 "+al.get(position).shopPhoneNumber);
        holder.tvDate.setText(al.get(position).date);
        holder.tvTimeSlot.setText("Time Slot - "+al.get(position).timeSlot);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShopkeeperOrderDetails.class);
                intent.putExtra("obj",al.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }
}

