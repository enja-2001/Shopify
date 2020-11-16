package com.example.firebase_6;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_6.ViewHolder.MyViewHolder;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RecyclerViewMyAddressAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<UserAddress> al;
    Context context;
    String phoneNumber;


    public RecyclerViewMyAddressAdapter(ArrayList<UserAddress> al,Context context){
        this.context=context;
        this.al=al;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_my_address,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvUserAddress.setText(al.get(position).houseNumber+","+al.get(position).getAddress());
        holder.tvUserName.setText(al.get(position).getName());

        holder.tvUserPhoneNumber.setText(al.get(position).getPhoneNumber());

        holder.etUserName.setVisibility(View.GONE);
        holder.etUserHouseNo.setVisibility(View.GONE);
        holder.etUserAddress.setVisibility(View.GONE);

        holder.etUserPhoneNumber.setVisibility(View.GONE);

        holder.tvViewHolderCurrentLocation.setVisibility(View.GONE);

        holder.ivViewHolderLocationSearch.setVisibility(View.GONE);


        holder.buttonViewHolderSubmit.setVisibility(View.GONE);
        holder.buttonViewHolderCancel.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);






        // holder.tvUserAddress.setEnabled(false);

       /* holder.tvUserAddress.setText("Hi there");
        holder.tvUserName.setText("Kaka");

        holder.tvUserPhoneNumber.setText("894894589");*/


       /* if(al.isEmpty())
            Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "false", Toast.LENGTH_SHORT).show();*/
        //Toast.makeText(context, al.get(holder.getAdapterPosition()).getPhoneNumber(), Toast.LENGTH_SHORT).show();



        holder.ivOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.ivOverflow);
                //inflating menu from xml resource
                popup.inflate(R.menu.address_menu);
                //adding click listener

                AddressesActivity ob=new AddressesActivity();

                ProgressDialog progressDialog=new ProgressDialog(context);


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item12:
                                //handle menu1 click

                                holder.etUserName.setVisibility(View.VISIBLE);

                                holder.etUserHouseNo.setVisibility(View.VISIBLE);
                                holder.etUserAddress.setVisibility(View.VISIBLE);

                                holder.etUserPhoneNumber.setVisibility(View.VISIBLE);

                                holder.textInputLayoutName.setVisibility(View.VISIBLE);
                                holder.textInputLayoutPhoneNUmber.setVisibility(View.VISIBLE);

                                holder.textInputLayoutAddress.setVisibility(View.VISIBLE);

                                holder.textInputLayoutHouseNumber.setVisibility(View.VISIBLE);


                                holder.tvViewHolderCurrentLocation.setVisibility(View.VISIBLE);

                                holder.ivViewHolderLocationSearch.setVisibility(View.VISIBLE);

                                holder.buttonViewHolderSubmit.setVisibility(View.VISIBLE);
                                holder.buttonViewHolderCancel.setVisibility(View.VISIBLE);
                                holder.view.setVisibility(View.VISIBLE);
                                holder.view2.setVisibility(View.VISIBLE);


                                holder.tvUserName.setVisibility(View.GONE);
                                holder.tvUserPhoneNumber.setVisibility(View.GONE);

                                holder.tvUserAddress.setVisibility(View.GONE);
                                holder.ivOverflow.setVisibility(View.GONE);

                                holder.etUserName.setText(al.get(position).getName());
                                holder.etUserPhoneNumber.setText(al.get(position).getPhoneNumber());

                                holder.etUserAddress.setText(al.get(position).getAddress());
                                holder.etUserHouseNo.setText(al.get(position).houseNumber);

                                //holder.etUserHouseNo.setText(al.get(position).get);






                                holder.buttonViewHolderSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String address = holder.etUserAddress.getText().toString();
                                        String addressName = holder.etUserName.getText().toString();

                                        String addressPhoneNumber = holder.etUserPhoneNumber.getText().toString();
                                        String addressHouseNumber = holder.etUserHouseNo.getText().toString();


                                        if (addressName.isEmpty()) {
                                            holder.etUserName.setError("Enter name");
                                            holder.etUserName.requestFocus();

                                        } else if (addressPhoneNumber.length() != 10) {
                                            holder.etUserPhoneNumber.setError("Enter a valid phone number");
                                            holder.etUserPhoneNumber.requestFocus();

                                        } else if (address.isEmpty()) {
                                            holder.etUserAddress.setError("Enter address");
                                            holder.etUserAddress.requestFocus();

                                        } else if (addressHouseNumber.isEmpty()) {
                                            holder.etUserHouseNo.setError("Field required");
                                            holder.etUserHouseNo.requestFocus();

                                        } else {

                                            //al=new ArrayList<>();
                                            //al.add(address);

//                                            Toast.makeText(context, al.get(position).getAccountNumber()+" "+al.get(position).getPhoneNumber()+" "+al.get(position).getAddress()+" "+al.get(position).houseNumber, Toast.LENGTH_SHORT).show();


                                            progressDialog.setTitle("Adding address");
                                            progressDialog.setMessage("Please wait until the address is added successfully" );
                                            progressDialog.setCanceledOnTouchOutside(false);
                                            progressDialog.show();

                                            FirebaseFirestore.getInstance().collection("Addresses").whereEqualTo("Account Phone Number", al.get(position).getAccountNumber())
                                                    .whereEqualTo("User Phone Number", al.get(position).getPhoneNumber())
                                                    .whereEqualTo("User Name", al.get(position).getName())
                                                    .whereEqualTo("Address", al.get(position).getAddress())
                                                    .whereEqualTo("House Number", al.get(position).houseNumber)
                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {


                                                    if (task.isSuccessful()) {




                                                        String documentId = task.getResult().getDocuments().get(0).getId();

                                                        FirebaseFirestore.getInstance().collection("Addresses").document(documentId).update("Address", address, "House Number",addressHouseNumber,"User Name", addressName, "User Phone Number", addressPhoneNumber)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                        UserAddress ob = new UserAddress(addressName, addressPhoneNumber,address,addressHouseNumber, al.get(position).getAccountNumber());
                                                                        al.set(position, ob);

                                                                        notifyItemChanged(position);

                                                                        holder.etUserName.setVisibility(View.GONE);

                                                                        holder.etUserHouseNo.setVisibility(View.GONE);
                                                                        holder.etUserAddress.setVisibility(View.GONE);

                                                                        holder.etUserPhoneNumber.setVisibility(View.GONE);


                                                                        holder.textInputLayoutName.setVisibility(View.GONE);
                                                                        holder.textInputLayoutPhoneNUmber.setVisibility(View.GONE);

                                                                        holder.textInputLayoutAddress.setVisibility(View.GONE);

                                                                        holder.textInputLayoutHouseNumber.setVisibility(View.GONE);


                                                                        holder.tvViewHolderCurrentLocation.setVisibility(View.GONE);

                                                                        holder.ivViewHolderLocationSearch.setVisibility(View.GONE);
                                                                        holder.buttonViewHolderSubmit.setVisibility(View.GONE);
                                                                        holder.buttonViewHolderCancel.setVisibility(View.GONE);
                                                                        holder.view.setVisibility(View.GONE);
                                                                        holder.view2.setVisibility(View.GONE);


                                                                        holder.tvUserName.setVisibility(View.VISIBLE);
                                                                        holder.tvUserPhoneNumber.setVisibility(View.VISIBLE);

                                                                        holder.tvUserAddress.setVisibility(View.VISIBLE);
                                                                        holder.ivOverflow.setVisibility(View.VISIBLE);

                                                                        progressDialog.dismiss();



                                                                    }
                                                                });


                                                    }
                                                    progressDialog.dismiss();

                                                }
                                            });
                                        }
                                    }
                                });
                                holder.buttonViewHolderCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        holder.etUserName.setVisibility(View.GONE);
                                        holder.textInputLayoutName.setVisibility(View.GONE);
                                        holder.textInputLayoutPhoneNUmber.setVisibility(View.GONE);

                                        holder.textInputLayoutAddress.setVisibility(View.GONE);

                                        holder.textInputLayoutHouseNumber.setVisibility(View.GONE);


                                        holder.etUserHouseNo.setVisibility(View.GONE);
                                        holder.etUserAddress.setVisibility(View.GONE);

                                        holder.etUserPhoneNumber.setVisibility(View.GONE);

                                        holder.tvViewHolderCurrentLocation.setVisibility(View.GONE);

                                        holder.ivViewHolderLocationSearch.setVisibility(View.GONE);
                                        holder.buttonViewHolderSubmit.setVisibility(View.GONE);
                                        holder.buttonViewHolderCancel.setVisibility(View.GONE);
                                        holder.view.setVisibility(View.GONE);
                                        holder.view2.setVisibility(View.GONE);


                                        holder.tvUserName.setVisibility(View.VISIBLE);
                                        holder.tvUserPhoneNumber.setVisibility(View.VISIBLE);

                                        holder.tvUserAddress.setVisibility(View.VISIBLE);
                                        holder.ivOverflow.setVisibility(View.VISIBLE);



                                    }
                                });












                                return true;


                            case R.id.item13:


                                progressDialog.setTitle("Adding address");
                                progressDialog.setMessage("Please wait until the address is removed successfully" );
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();

                                FirebaseFirestore.getInstance().collection("Addresses").whereEqualTo("Account Phone Number",al.get(position).getAccountNumber())
                                        .whereEqualTo("User Phone Number",al.get(position).getPhoneNumber())
                                        .whereEqualTo("User Name",al.get(position).getName())
                                        .whereEqualTo("Address",al.get(position).getAddress())
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if(task.isSuccessful()){


                                            String documentId=task.getResult().getDocuments().get(0).getId();

                                            FirebaseFirestore.getInstance().collection("Addresses").document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    al.remove(position);
                                                    notifyItemRemoved(position);
                                                    notifyItemRangeChanged(position,al.size());
                                                    ((AddressesActivity)context).tvTotalAddresses.setText(al.size()+" saved addresses");
                                                    progressDialog.dismiss();






                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();

                                                }


                                            });





                                        }

                                    }
                                }) ;






                                return true;

                            default:
                                return false;
                        }
                    }
                });

                //displaying the popup
                popup.show();

            }
        });


        holder.tvViewHolderCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //---getLocation();

                    LocationServices.getFusedLocationProviderClient(context).getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {

                                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    holder.etUserAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });


                } else {
                    ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        holder.ivViewHolderLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken("pk.eyJ1IjoiZW5qYS0yMDAxIiwiYSI6ImNrYnkyajU4eDB2dDMydGxnc2oxam41dDkifQ.i77C__pKEuCCagjXv89drQ")
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build((Activity)context);


                intent.putExtra("EditText", Integer.toString(holder.etUserAddress.getId()));
                //Toast.makeText(context, Integer.toString(holder.etUserAddress.getId()), Toast.LENGTH_SHORT).show();
                ((Activity)context).startActivityForResult(intent, 101);

            }


        });




    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public ArrayList<UserAddress> getItems(){
        return al;
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            //tvAddress.setText(feature.placeName());
            .setText(feature.placeName());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(context, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }
    }*/


}
