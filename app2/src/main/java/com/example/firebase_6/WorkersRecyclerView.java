package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkersRecyclerView extends AppCompatActivity {

    String title;

    TextView tvTitle;

    RecyclerView recyclerView;
    ArrayList<worker> al;
    TextView tvNotificationEmpty;


    MyRecyclerViewAdapter adapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_recycler_view);

        title=getIntent().getStringExtra("toolbar");


        recyclerView=(RecyclerView)findViewById(R.id.workersRecyclerView);
        tvTitle=(TextView)findViewById(R.id.toolbarTitle);


        progressBar = (ProgressBar) findViewById(R.id.progressBarReviews);
        progressBar.setVisibility(View.VISIBLE);

        tvNotificationEmpty = (TextView) findViewById(R.id.tvReviewsEmpty);

        tvTitle.setText(title);

        al=new ArrayList<worker>();

        recyclerView.setVisibility(View.GONE);
        tvNotificationEmpty.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        FirebaseFirestore.getInstance().collection("Workers").whereEqualTo("Profession",title.substring(0,(title.length()-1))).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressBar.setVisibility(View.GONE);

                for(DocumentSnapshot documentSnapshot:task.getResult()){
                    worker ob=new worker(documentSnapshot.getString("Phone number"),(documentSnapshot.getString("First name")+" "+documentSnapshot.getString("Last name")),documentSnapshot.getString("Rating"),documentSnapshot.getString("Photo"),documentSnapshot.getString("Gender"),documentSnapshot.getString("Date of birth"),documentSnapshot.getString("Reviews"),documentSnapshot.getString("Working since"),documentSnapshot.getString("Profession"));

                    al.add(ob);
                }

                if(al.size()>0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNotificationEmpty.setVisibility(View.GONE);
                    adapter=new MyRecyclerViewAdapter(al,WorkersRecyclerView.this);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    recyclerView.setVisibility(View.GONE);
                    tvNotificationEmpty.setVisibility(View.VISIBLE);
                }

                //Toast.makeText(MaidActivity.this, al.get(0).getName()+" "+al.get(1).getName(), Toast.LENGTH_SHORT).show();
//                adapter=new MyRecyclerViewAdapter(al,WorkersRecyclerView.this);
//
//                recyclerView.setAdapter(adapter);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(WorkersRecyclerView.this, "Error occured!", Toast.LENGTH_SHORT).show();
            }
        });


        /*FirebaseFirestore.getInstance().collection("Workers").document("+911111111111").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    worker ob = new worker(documentSnapshot.getString("Phone number"), (documentSnapshot.getString("First name") + " " + documentSnapshot.getString("Last name")), documentSnapshot.getString("Rating"), documentSnapshot.getString("Photo"), documentSnapshot.getString("Gender"), documentSnapshot.getString("Date of birth"), documentSnapshot.getString("Reviews"), documentSnapshot.getString("Working since"), documentSnapshot.getString("Profession"));

                    al.add(ob);
                }
            }

        });*/

    }
}
