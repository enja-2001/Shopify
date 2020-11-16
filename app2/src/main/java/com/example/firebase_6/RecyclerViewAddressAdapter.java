package com.example.firebase_6;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_6.ViewHolder.MyViewHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class RecyclerViewAddressAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<UserAddress> al;
    Context context;
    int mSelectedItem;


    public RecyclerViewAddressAdapter(ArrayList<UserAddress> al,Context context){
        this.context=context;
        this.al=al;
        mSelectedItem=-1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_address,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvUserName.setText(al.get(position).name);
        holder.tvUserPhoneNumber.setText(al.get(position).phoneNumber);
        holder.tvUserAddress.setText(al.get(position).address);



        /*holder.addressConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mSelectedItem =holder.getAdapterPosition();
                notifyItemRangeChanged(0, al.size());
                holder.radioButton.setChecked(position == mSelectedItem);
            }
        });*/



        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedItem = holder.getAdapterPosition();
                notifyItemRangeChanged(0, al.size());

            }
        };

        holder.addressConstraintLayout.setOnClickListener(l);
        holder.radioButton.setOnClickListener(l);
        holder.itemView.setOnClickListener(l);

        holder.radioButton.setChecked(position == mSelectedItem);

        ((PlaceOrderAddress)context).radioSelectedPosition=mSelectedItem;










    }

    @Override
    public int getItemCount() {
        return al.size();
    }


}
