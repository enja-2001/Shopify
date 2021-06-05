package com.example.shopify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopify.R;
import com.example.shopify.TimeSlots;
import com.example.shopify.helper.Orders;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {

    ArrayList<Orders> time;
    Context context;
    public OnItemClickListener listener;

    public CustomerAdapter(ArrayList<Orders> time, Context context)
    {
        this.time = time;
        this.context = context;
    }



    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_card, parent, false);
        return new CustomerHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerHolder holder, int position) {
       holder.cust_phone.setText(""+time.get(position).getUserph());
       holder.rempay.setText(""+time.get(position).getRempay());

    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {

        TextView cust_phone, rempay;
        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            cust_phone = itemView.findViewById(R.id.custphone);
            rempay = itemView.findViewById(R.id.pay);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                            listener.onItemClick(pos);
                    }

                }
            });
        }
    }
}
