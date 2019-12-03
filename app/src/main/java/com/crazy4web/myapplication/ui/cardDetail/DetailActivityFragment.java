package com.crazy4web.myapplication.ui.cardDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy4web.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivityFragment extends AppCompatActivity {

    FirebaseFirestore database;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    SharedPreferences sp;
    String id;
    private static final String TAG = "DetailActivityFragmentn";
    RecyclerView recyclerView,recyclerViewReviews;
    RecyclerAdapter recyclerAdapter;
    RecyclerAdapterReviews recyclerAdapterReviews;
    TextView businessDesc, companyName, company_desc;
    ImageView img;
    FirebaseStorage firebaseStorage;
    ArrayList<String> val,arr = new ArrayList<>();
    TextView write_a_review;
    List<Map> ratingsList = new ArrayList<>();
//    int count =0;
    Boolean likeFlag = false;
    ImageView like;
    ProgressBar progress;
    Set<String> hash_set = new HashSet<>();
    Set<String> likedIds = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment);
        like = findViewById(R.id.likeWhite);
        sp = getSharedPreferences("prefFile", Context.MODE_PRIVATE);
        Intent i = getIntent();
        if(i != null)
        {
            id = i.getStringExtra("docId");
        }

//        Set<String> abcd = new HashSet<>();
//        Set<String> hashs = sp.getStringSet("likeFlag", new HashSet<>());
//        if(hashs != null) {
//            hashs.forEach(likes -> {
//                if(id.equals(likes.split(",")[0])){
//                    if(likeFlag.equals(likes.split(",")[1]) && likeFlag == true){
//                    like.setImageResource(R.drawable.like);
//                }
//                like.setImageResource(R.drawable.like_red);
//                }
//            });
//        }

//        hash_set = sp.getStringSet("likedIds",new HashSet<>());

        String likedDoc = sp.getString(id,"");
        if(likedDoc != null){
            if(id.equals(likedDoc.split(",")[0])){

                if(likedDoc.split(",")[1].contains("true")){
                    like.setImageResource(R.drawable.like_red);
                    likeFlag = false;
                }else {
                    like.setImageResource(R.drawable.like);
                    likeFlag = true;
                }

            }else{
                Log.d(TAG, "id does not match");
            }
        }

        progress = findViewById(R.id.progressBarDetailActivity);
        progress.setVisibility(View.VISIBLE);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                like.setForegroundGravity(Gravity.TOP);
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Log.d(TAG, "tapped like");
                        setLike(like);
                    }
                });
//            }
//        },1000);


        Toolbar toolbar = findViewById(R.id.toolbarCardDetail);
        setSupportActionBar(toolbar);
//        Log.d(TAG, "onCreate: "+like.getForegroundGravity());
//        like.bringToFront();
//        like.setClickable(true);
//        like.setElevation(5);

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

//                        if()
                        if(sp.getStringSet("likedIds", new HashSet<>()).isEmpty()){
                            sp.edit().putStringSet("likedIds",likedIds).apply();
                        }
                            hash_set = sp.getStringSet("likedIds", new HashSet<>());
                            Log.d(TAG, "hash_set"+hash_set);
                            Iterator<String> iter = likedIds.iterator();
                            while(iter.hasNext()){
                                hash_set.add(iter.next());
                            }
                            sp.edit().putStringSet("hashSet", hash_set).apply();

                        finish();
                    }
                });



                companyName = findViewById(R.id.companyName);
                company_desc = findViewById(R.id.company_desc);
                img = findViewById(R.id.img);
                database = FirebaseFirestore.getInstance();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database.collection("business").document(id).get().addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        task.getResult().getData().forEach((key, value)->{
//                    Log.d(TAG, key+" -> "+value);
                            arr.add(value.toString());
                            if(key.contains("services")){

                                val = (ArrayList<String>) value;
                            }
                        });
                        sp.edit().putString("videoUrl",arr.get(0)).apply();
                        sp.edit().putString("websiteUrl",arr.get(1)).apply();
                        sp.edit().putString("businessDesc",arr.get(2)).apply();
                        sp.edit().putString("imagePath",arr.get(3)).apply();
                        sp.edit().putString("companyName",arr.get(4)).apply();
                        sp.edit().putString("tagline",arr.get(6)).apply();
//                sp.edit().putString("service",arr.get(6)).apply();
                        sp.edit().putString("category",arr.get(7)).apply();
                        updatePageWithData(arr);

                        sp.edit().putString("bizname", arr.get(4));

//                Log.d(TAG, ""+arr);

                    }
                });
            }
        });
                getReviews();

                sp.edit().putString("bid",id).apply();

                final ViewPager viewPager = findViewById(R.id.viewPager);
                TabLayout tabLayout = findViewById(R.id.tab_layout);
                tabLayout.addTab(tabLayout.newTab().setText("Services"));
                tabLayout.addTab(tabLayout.newTab().setText("About"));
                tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setBackgroundColor(Color.WHITE);
                tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#fe9b18"));

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
//                        tabLayout.setBackgroundColor(Color.WHITE);
//                        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#fe9b18"));
                                recyclerView = findViewById(R.id.recyclerView);
                                recyclerAdapter = new RecyclerAdapter(val);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerView.setAdapter(recyclerAdapter);
                                Log.d(TAG, "from Detail activity fragment: "+arr.get(6));

                                break;

                            case 1:
//                        Log.d(TAG, ""+tab.getPosition());
//                        tabLayout.setBackgroundColor(Color.WHITE);
//                        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#fe9b18"));
                                businessDesc = findViewById(R.id.businessDesc1);
                                businessDesc.setText(sp.getString("businessDesc", ""));

                                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        businessDesc.setText("");
                                        finish();
                                    }
                                });
                                break;

                            case 2:
//                        tabLayout.setBackgroundColor(Color.WHITE);
//                        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#fe9b18"));
//                        getReviews(count);
                                recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
                                //        write a review
                                write_a_review = findViewById(R.id.write_a_review);
                                write_a_review.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intentReview = new Intent(getApplicationContext(), WriteReview.class);
                                        startActivity(intentReview);
                                    }
                                });
                                recyclerAdapterReviews = new RecyclerAdapterReviews(ratingsList);
                                recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recyclerViewReviews.setAdapter(recyclerAdapterReviews);

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
    }

    private void updatePageWithData(ArrayList<String> arr){

        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(arr.get(3));
        storageReference.getDownloadUrl().addOnSuccessListener(url ->{
            Glide.with(getApplicationContext()).load(url).into(img);

        });

        companyName.setText(arr.get(4));
        company_desc.setText(arr.get(6));

        progress.setVisibility(View.GONE);
    }

    public void getReviews(){

            database.collection("reviews").whereEqualTo("bid",id).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
//                                        Log.d(TAG, task.getResult().getDocuments().toString());

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ratingsList.add(document.getData());
                        }
                    }
                });
//            count = ratingsList.size();
    }

    public void setLike(ImageView like){
        if(likeFlag == false){
            like.setImageResource(R.drawable.like_red);
            likeFlag = true;
            sp.edit().putString(id,id+","+likeFlag.toString()).apply();
            likedIds.add(id);
        }else{
            like.setImageResource(R.drawable.like);
            likeFlag = false;
            sp.edit().putString(id, id + "," + likeFlag.toString()).apply();
            likedIds.remove(id);
            sp.edit().putStringSet("likedIds", new HashSet<>()).apply();
        }
    }

}
