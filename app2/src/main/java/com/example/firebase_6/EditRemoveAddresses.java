package com.example.firebase_6;

import java.util.ArrayList;

class EditRemoveAddresses {

    public void editAddress(ArrayList<UserAddress> al,int position){

    }

    public void removeAddress(ArrayList<UserAddress> al,int position){
        al.remove(position);
        //RecyclerViewMyAddressAdapter adapter = new RecyclerViewMyAddressAdapter(al, AddressesActivity.this);

       // recyclerView.setAdapter(adapter);

    }
}
