package com.crazy4web.myapplication.ui.cardDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.chat.chat_screen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ServicesTab extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    LinearLayoutManager lm;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    private static final String TAG = "ServicesTab";
    FloatingActionButton fab;
    String bizname;
    FirebaseFirestore database;
    ArrayList<String> val = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.services_tab, container, false);
        View root = inflater.inflate(R.layout.services_tab, container, false);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        String bid = sp.getString("bid","");
        bizname = sp.getString("bizname","Default");
//        Log.d(TAG, "Service: "+sp.getString("service","def"));

        recyclerView = root.findViewById(R.id.recyclerView);
        getServices(bid);

        fab = root.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), chat_screen.class);
                i.putExtra("businessname", bizname);
                startActivity(i);

            }
        });
        return root;

    }
    public ArrayList<String> getServices(String bid){
        database = FirebaseFirestore.getInstance();
        database.collection("business").document(bid).get().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                Log.d(TAG, ""+task.getResult().getData());
                task.getResult().getData().forEach((key, value)->{
                    if(key.contains("services")){
                        Log.d(TAG, "onBindViewHolder: ");

                        val = (ArrayList<String>) value;
                        recyclerAdapter = new RecyclerAdapter(val);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recyclerAdapter);
                        Log.d(TAG, "from service tab after recycler view loaded");
                    }
                });
            }
        });
        return val;
    }
}
