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
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeHolder> {

    ArrayList<TimeSlots> time;
    Context context;
    public OnItemClickListener listener;

    public TimeSlotAdapter(ArrayList<TimeSlots> time, Context context) {
        this.time = time;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_card, parent, false);
        return new TimeHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.TimeHolder holder, int position) {

        holder.time.setText(time.get(position).getTime());
        holder.cust_count.setText(time.get(position).getValue()+ " customers");

    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class TimeHolder extends RecyclerView.ViewHolder {

        TextView cust_count, time;
        public TimeHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.Time);
            cust_count = itemView.findViewById(R.id.count);

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
