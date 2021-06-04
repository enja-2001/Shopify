package com.example.shopify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdpaterFragmentHome recyclerViewAdpater;
    ArrayList<ShopCategories> al;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        al=new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        //recyclerView.setItemAnimator();


        recyclerViewAdpater=new RecyclerViewAdpaterFragmentHome(al,getContext());
        recyclerView.setAdapter(recyclerViewAdpater);

        FirebaseFirestore.getInstance().collection("ShopCategories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String cat = documentSnapshot.getString("Category");
                        String img = documentSnapshot.getString("Image");

                        ShopCategories ob = new ShopCategories(cat, img);
                        al.add(ob);

                        recyclerViewAdpater.notifyDataSetChanged();

                    }
//
//                    recyclerViewAdpater=new RecyclerViewAdpaterFragmentHome(al,getContext());
//                    recyclerView.setAdapter(recyclerViewAdpater);

                }
                else{
                    Toast.makeText(getContext(), "Error occured in fetching data!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
