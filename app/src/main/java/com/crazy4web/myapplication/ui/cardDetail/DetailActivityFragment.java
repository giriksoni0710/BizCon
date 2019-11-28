package com.crazy4web.myapplication.ui.cardDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;
import com.crazy4web.myapplication.MainActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy4web.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivityFragment extends AppCompatActivity {

    FirebaseFirestore database;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    String id;
    private static final String TAG = "DetailActivityFragment";
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    TextView businessDesc, companyName, company_desc;
    ImageView img;
    FirebaseStorage firebaseStorage;
    ArrayList<String> val = new ArrayList<>();
//    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment);
        Toolbar toolbar = findViewById(R.id.toolbarCardDetail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        //        toolbar.setBackgroundColor(Color.parseColor("#000000"));



        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();
        if(i != null)
        {
            id = i.getStringExtra("docId");
        }

        companyName = findViewById(R.id.companyName);
        company_desc = findViewById(R.id.company_desc);
        img = findViewById(R.id.img);

        database = FirebaseFirestore.getInstance();
        SharedPreferences sp = getSharedPreferences("prefFile", Context.MODE_PRIVATE);
        ArrayList<String> arr = new ArrayList<>();
        database.collection("business").document(id).get().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
//                Log.d(TAG, "onCreate: "+task.getResult().getData());
                task.getResult().getData().forEach((key, value)->{
//                    Log.d(TAG, key+" -> "+value);
                    arr.add(value.toString());
                    if(key.contains("services")){

                        val = (ArrayList<String>) value;
                    }
                });
//                Log.d(TAG, ""+arr.get(0));
                sp.edit().putString("videoUrl",arr.get(0)).apply();
                sp.edit().putString("websiteUrl",arr.get(1)).apply();
                sp.edit().putString("businessDesc",arr.get(2)).apply();
                sp.edit().putString("imagePath",arr.get(3)).apply();
                sp.edit().putString("companyName",arr.get(4)).apply();
                sp.edit().putString("tagline",arr.get(5)).apply();
//                sp.edit().putString("service",arr.get(6)).apply();
                sp.edit().putString("category",arr.get(7)).apply();
//                sp.edit().putString("bizEmailID",arr.get(8)).apply();
//                Log.d(TAG, "onCreate: "+arr.get(3));
                updatePageWithData(arr);

                sp.edit().putString("bizname", arr.get(4));
//                Log.d(TAG, arr.get(6));
////                ArrayList<String> val = new ArrayList<>();
//                val = (ArrayList<String>) value;
//                val = arr.get(6);

            }
        });

//        String business_desc = sp.getString("businessDesc","Default");
        sp.edit().putString("bid",id).apply();

        final ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(swipeAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: "+tab.getPosition());

                switch (tab.getPosition()){

                    case 0:
                        recyclerView = findViewById(R.id.recyclerView);
                        recyclerAdapter = new RecyclerAdapter(val);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recyclerAdapter);
                        Log.d(TAG, "from Detail activity fragment: "+arr.get(6));

                        break;

                    case 1:
//                        Log.d(TAG, ""+tab.getPosition());
                        businessDesc = findViewById(R.id.businessDesc1);
//                        Log.d(TAG, "from detail activity fragment"+business_desc);
//                        if(!businessDesc.getText().toString().matches("")){
//                            businessDesc.setText("");
//                            businessDesc.setText(business_desc);
                        businessDesc.setText(sp.getString("businessDesc", ""));
//                        }else {
//                            businessDesc.setText(business_desc);
//                        }
//                        Log.d(TAG, "2"+businessDesc.getText().toString());
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                businessDesc.setText("");
                                finish();
                            }
                        });
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.d(TAG, "onTabUnselected: ");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        viewPager.setOffscreenPageLimit(1); // how many fragments you want to load in the memory
//        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(swipeAdapter);
//        viewPager.setCurrentItem(0);//the first fragment
//        viewPager.setCurrentItem(0);
    }

    private void updatePageWithData(ArrayList<String> arr){

        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(arr.get(3));
        storageReference.getDownloadUrl().addOnSuccessListener(url ->{
            Glide.with(getApplicationContext()).load(url).into(img);
        });

        companyName.setText(arr.get(4));
        company_desc.setText(arr.get(5));


    }


}
