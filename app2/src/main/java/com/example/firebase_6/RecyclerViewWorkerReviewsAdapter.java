package com.example.firebase_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_6.ViewHolder.MyViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class RecyclerViewWorkerReviewsAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<MyOrder> al;
    Context context;
    String phoneNumber;
    String nameAddress;


    public RecyclerViewWorkerReviewsAdapter(ArrayList<MyOrder> al,Context context){
        this.context=context;
        this.al=al;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_review_worker,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.tvWorkerReviews.setText(al.get(position).review);
        holder.tvWorkerReviewsRating.setText(al.get(position).rating);
        holder.tvWorkerReviewsDate.setText(al.get(position).endingDate);

        if(al.get(position).rating.equals("1"))
            holder.tvWorkerReviewsTitle.setText("Very Poor!");
        if(al.get(position).rating.equals("2"))
            holder.tvWorkerReviewsTitle.setText("Satisfactory!");
        if(al.get(position).rating.equals("3"))
            holder.tvWorkerReviewsTitle.setText("Good!");
        if(al.get(position).rating.equals("4"))
            holder.tvWorkerReviewsTitle.setText("Very Good!");
        if(al.get(position).rating.equals("5"))
            holder.tvWorkerReviewsTitle.setText("Excellent!");





//        FirebaseFirestore.getInstance().collection("Workers").document(al.get(position).workerPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot documentSnapshot = task.getResult();
//
////                    holder.tvOngoingProfession.setText(documentSnapshot.getString(""));
//                    holder.tvReviewsName.setText(documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name"));
////                    Picasso.get().load(documentSnapshot.getString("Photo")).into(holder.ivReviews);
//                    holder.tvReviewsProfession.setText(documentSnapshot.getString("Profession"));
//
//                } else {
//                    Toast.makeText(context, "Error occured!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        FirebaseFirestore.getInstance().collection("Users").document(al.get(position).userPhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

//                    holder.tvOngoingProfession.setText(documentSnapshot.getString(""));
                    nameAddress=documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name");
                    holder.tvWorkerReviewsName.setText("Verified user "+nameAddress);
//                    Picasso.get().load(documentSnapshot.getString("Photo")).into(holder.ivReviews);

                } else {
                    Toast.makeText(context, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public ArrayList<MyOrder> getItems(){
        return al;
    }



}
