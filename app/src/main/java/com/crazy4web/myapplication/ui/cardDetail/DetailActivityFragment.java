package com.crazy4web.myapplication.ui.cardDetail;

import android.content.Context;
import android.content.SharedPreferences;
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

public class DetailActivityFragment extends AppCompatActivity {

    FirebaseFirestore database;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    private String id = "ntLNA7ooPoKL1eOenBBx";
    private static final String TAG = "DetailActivityFragment";
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    TextView businessDesc, companyName, company_desc;
    ImageView img;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment);
        Toolbar toolbar = findViewById(R.id.toolbarCardDetail);
        setSupportActionBar(toolbar);
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

        companyName = findViewById(R.id.companyName);
        company_desc = findViewById(R.id.company_desc);
        businessDesc = findViewById(R.id.businessDesc1);
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
                });
//                Log.d(TAG, ""+arr.get(0));
                sp.edit().putString("videoUrl",arr.get(0)).apply();
                sp.edit().putString("websiteUrl",arr.get(1)).apply();
                sp.edit().putString("businessDesc",arr.get(2)).apply();
                sp.edit().putString("imagePath",arr.get(3)).apply();
                sp.edit().putString("companyName",arr.get(4)).apply();
                sp.edit().putString("tagline",arr.get(5)).apply();
                sp.edit().putString("service",arr.get(6)).apply();
                sp.edit().putString("category",arr.get(7)).apply();
                updatePageWithData(arr);
            }
        });

        String business_desc = sp.getString("businessDesc","Default");

        FloatingActionButton fab = findViewById(R.id.fab);
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

//                if(tab.getPosition() == 1)
//                {
//                    Log.d(TAG, "honTabSelected: "+business_desc);
//                    businessDesc.setText("abcd");
//                }
                switch (tab.getPosition()){

                    case 0:
                        recyclerView = findViewById(R.id.recyclerView);
                        recyclerAdapter = new RecyclerAdapter(arr.get(6));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recyclerAdapter);
                        Log.d(TAG, "onTabSelected: "+arr.get(6));
                        break;

                    case 1:
                        Log.d(TAG, ""+tab.getPosition());
//                        Log.d(TAG, "1"+business_desc);
                        businessDesc.setText(business_desc);
//                        Log.d(TAG, "2"+businessDesc.getText().toString());
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
        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://bizcon-17781.appspot.com/Images/Bizcon.jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(url ->{
            Glide.with(getApplicationContext()).load(url).into(img);
        });
    }

    private void updatePageWithData(ArrayList<String> arr){
        companyName.setText(arr.get(4));
        company_desc.setText(arr.get(5));

    }

    public class MyAppGlideModule extends AppGlideModule {
        public MyAppGlideModule() {
            super();
        }
        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,new FirebaseImageLoader.Factory());
        }
    }

}
