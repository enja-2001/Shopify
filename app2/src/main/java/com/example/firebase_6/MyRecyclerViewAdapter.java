package com.example.firebase_6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_6.ViewHolder.MyViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<worker> al;
    Context context;


    public MyRecyclerViewAdapter(ArrayList<worker> al,Context context){
        this.context=context;
        this.al=al;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(al.get(holder.getAdapterPosition()).getName());
        holder.tvRating.setText(al.get(holder.getAdapterPosition()).getRating());
        Picasso.get().load(al.get(holder.getAdapterPosition()).uri).into(holder.iv);
        //holder.tvGender.setText(al.get(holder.getAdapterPosition()).gender);
        //holder.tvAge.setText(al.get(holder.getAdapterPosition()).age);




        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("workerPhoneNumber",al.get(position).phoneNumber);


                editor.apply();

                Intent intent=new Intent(context,com.example.firebase_6.WorkerDetailsActivity.class);
                //intent.putExtra("object",(al.get(holder.getAdapterPosition())));
                //intent.putExtra("hi",MyRecyclerViewAdapter.this);
                worker ob=al.get(position);
                intent.putExtra("obj",ob);
                context.startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return al.size();
    }


}
