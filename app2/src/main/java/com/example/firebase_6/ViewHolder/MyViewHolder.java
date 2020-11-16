package com.example.firebase_6.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_6.R;
import com.google.android.material.textfield.TextInputLayout;

public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView iv;
    public ImageView ivOverflow;

    public TextView tvName;
    public TextView tvRating;
    public TextView tvGender;

    public TextView tvAge;
    public TextView tvUserAddress;
    public TextView tvUserName;

    public TextView tvUserPhoneNumber;

    public  EditText etUserName;
    public  EditText etUserPhoneNumber;

    public  EditText etUserAddress;
    public  EditText etUserHouseNo;

    public TextInputLayout textInputLayoutName;
    public TextInputLayout textInputLayoutPhoneNUmber;

    public TextInputLayout textInputLayoutAddress;

    public TextInputLayout textInputLayoutHouseNumber;


    public TextView tvViewHolderCurrentLocation;
    public ImageView ivViewHolderLocationSearch;












    public ConstraintLayout constraintLayout;
    public ConstraintLayout addressConstraintLayout;
    public RadioButton radioButton;

    public ConstraintLayout notificationConstraintLayout;
    public TextView tvNotificationOrderReceived;

    public TextView tvNotificationOrderID;
    public TextView tvNotificationDate;

    public TextView tvOngoingName;
    public TextView tvOngoingProfession;
    public TextView tvOngoingStartingDate;
    public TextView tvOngoingEndingDate;

    public ImageView ivReviews;
    public TextView tvReviewsName;
    public TextView tvReviewsRating;
    public TextView tvReviews;
    public TextView tvReviewsDate;
    public TextView tvReviewsProfession;
    public TextView tvReviewsOrderID;

    public TextView tvWorkerReviewsName;
    public TextView tvWorkerReviewsRating;
    public TextView tvWorkerReviews;
    public TextView tvWorkerReviewsDate;
    public TextView tvWorkerReviewsTitle;














    public Button buttonViewHolderSubmit;
    public Button buttonViewHolderCancel;



    public CardView ongoingCardView;

    public View view;
    public View view2;


    public MyViewHolder(@NonNull View itemView) {

        super(itemView);
        iv=(ImageView)itemView.findViewById(R.id.ivSingleRow);
        tvName=(TextView)itemView.findViewById(R.id.tvNameSingleRow);
        tvRating=(TextView)itemView.findViewById(R.id.tvRatingSingleRow);
        //tvGender=(TextView)itemView.findViewById(R.id.tvGenderSingleRow);

        //tvAge=(TextView)itemView.findViewById(R.id.tvAgeSingleRow);
        constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.singleRowLayout);
        notificationConstraintLayout=(ConstraintLayout)itemView.findViewById(R.id.single_row_notification_constraint_layout);



        tvUserAddress=(TextView)itemView.findViewById(R.id.single_row_address_user);


        tvUserName=(TextView)itemView.findViewById(R.id.single_row_address_name);

        tvUserPhoneNumber=(TextView)itemView.findViewById(R.id.single_row_address_phone_number);


        addressConstraintLayout=(ConstraintLayout)itemView.findViewById(R.id.addressConstraintLayout);
        radioButton=(RadioButton)itemView.findViewById(R.id.radioBut);
        ivOverflow=(ImageView)itemView.findViewById(R.id.ivMyAddressOverflow);


        etUserName=(EditText)itemView.findViewById(R.id.etViewHolderAddressName);
        etUserPhoneNumber=(EditText)itemView.findViewById(R.id.etViewHolderAddressPhoneNumber);

        etUserAddress=(EditText)itemView.findViewById(R.id.etViewHolderAddress);

        etUserHouseNo=(EditText)itemView.findViewById(R.id.etViewHolderAddressHouseNo);

        textInputLayoutName=(TextInputLayout)itemView.findViewById(R.id.text_input_layout_my_address_name);
        textInputLayoutPhoneNUmber=(TextInputLayout)itemView.findViewById(R.id.text_input_layout_my_address_phone_number);

        textInputLayoutAddress=(TextInputLayout)itemView.findViewById(R.id.text_input_layout_my_address);
        textInputLayoutHouseNumber=(TextInputLayout)itemView.findViewById(R.id.text_input_layout_my_address_house_number);




        tvViewHolderCurrentLocation=(TextView)itemView.findViewById(R.id.tvViewHolderCurrentLocation);
        ivViewHolderLocationSearch=(ImageView)itemView.findViewById(R.id.ivViewHolderLocationSearch);

        buttonViewHolderSubmit=(Button)itemView.findViewById(R.id.buttonViewHolderSubmit);
        buttonViewHolderCancel=(Button)itemView.findViewById(R.id.buttonViewHolderCancel);


        tvNotificationDate=(TextView)itemView.findViewById(R.id.tvNotificationDate);
        tvNotificationOrderID=(TextView)itemView.findViewById(R.id.tv_single_row_notification_order_id);
        tvNotificationOrderReceived=(TextView)itemView.findViewById(R.id.tv_single_row_notification_order_received);

        tvOngoingName=(TextView)itemView.findViewById(R.id.tv_single_ongoing_name);

        tvOngoingProfession=(TextView)itemView.findViewById(R.id.tv_single_ongoing_profession);
        tvOngoingStartingDate=(TextView)itemView.findViewById(R.id.tv_single_ongoing_starting_date);
        tvOngoingEndingDate=(TextView)itemView.findViewById(R.id.tv_single_ongoing_ending_date);
        ongoingCardView=(CardView)itemView.findViewById(R.id.single_row_ongoing_cardview);

        tvReviews=(TextView)itemView.findViewById(R.id.single_row_review);
        tvReviewsDate=(TextView)itemView.findViewById(R.id.single_row_review_date);

        tvReviewsRating=(TextView)itemView.findViewById(R.id.single_row_review_rating);
        tvReviewsName=(TextView)itemView.findViewById(R.id.single_row_review_name);

//        ivReviews=(ImageView)itemView.findViewById(R.id.single_row_review_imageview);
        tvReviewsProfession=(TextView)itemView.findViewById(R.id.single_row_review_profession);
        tvReviewsOrderID=(TextView)itemView.findViewById(R.id.single_row_review_orderID);

        tvWorkerReviewsRating=(TextView)itemView.findViewById(R.id.single_row_review_worker_rating);
        tvWorkerReviewsTitle=(TextView)itemView.findViewById(R.id.single_row_review_worker_title);
        tvWorkerReviewsDate=(TextView)itemView.findViewById(R.id.single_row_review_worker_date);
        tvWorkerReviews=(TextView)itemView.findViewById(R.id.single_row_review_worker_review);
        tvWorkerReviewsName=(TextView)itemView.findViewById(R.id.single_row_review_worker_name);

        view=(View)itemView.findViewById(R.id.view);
        view2=(View)itemView.findViewById(R.id.view2);





















    }



}
