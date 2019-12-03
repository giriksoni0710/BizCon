package com.crazy4web.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.categoryview.category_page;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {

    ImageView categoryImage;
    private HomeViewModel homeViewModel;

    CardView cardView;
    Intent i;
    Intent intent;
    FirebaseFirestore database;
    TextView businessname;
    RecyclerView recyclerView, recommendationRecyclerView;
    RecyclerView.Adapter recycleradaptor;
    RecyclerView.LayoutManager layoutManager;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    JsonObject jsonObject;
    SharedPreferences sharedPreferences;
    CardView recommendationCard;

    ArrayList<String> recommendedbusiness, star;

    public LinearLayout linearLayout_technology, linearLayout_music, linearLayout_art,
            linearLayout_advertising, linearLayout_designing, linearLayout_household, linearLayout_fashion;
    private static final String TAG = "HomeFragment";


    Integer count=0;

    private int[] images = new int[]{
            R.drawable.categorypage1,R.drawable.categorypage2, R.drawable.categorypage1, R.drawable.categorypage2};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        String finaldata;

        //recommendation recycler view

        recommendationRecyclerView = root.findViewById(R.id.recommendationRecyclerView);
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        businessname = root.findViewById(R.id.business_name);
        database = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.recycler_view_newlyadded);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        // handling multiple category clicks in one method
        linearLayout_technology = root.findViewById(R.id.technology_category);
        linearLayout_technology.setOnClickListener(this);
        linearLayout_art = root.findViewById(R.id.art_category);
        linearLayout_art.setOnClickListener(this);
        linearLayout_advertising = root.findViewById(R.id.advertising_category);
        linearLayout_advertising.setOnClickListener(this);
        linearLayout_designing = root.findViewById(R.id.design_category);
        linearLayout_designing.setOnClickListener(this);
        linearLayout_household = root.findViewById(R.id.household_category);
        linearLayout_household.setOnClickListener(this);
        linearLayout_music = root.findViewById(R.id.music_category);
        linearLayout_music.setOnClickListener(this);
        linearLayout_fashion = root.findViewById(R.id.fashion_category);
        linearLayout_fashion.setOnClickListener(this);



        // one over-rided click listener for all


        ArrayList biz_name = new ArrayList<>();
        ArrayList biz_img = new ArrayList<>();
        List<String> docIds = new ArrayList<>();



            database.collection("business").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

    //                Log.d("docs", queryDocumentSnapshots.getDocuments()+"");


                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {


                        Map data = new HashMap();

                        data = documentSnapshot.getData();
                        String data2 = documentSnapshot.getId();
    //                    Log.d(TAG, ""+documentSnapshot.getId());

                        docIds.add(data2);

                        JsonObject jsonObject = new JsonObject();

                        data.forEach((key, value) -> {

                           jsonObject.addProperty(key.toString(),value.toString());

                        });


                        biz_name.add(jsonObject.get("company_name"));

                        biz_img.add(jsonObject.get("image_path").toString());


                    }
                    recycleradaptor = new HomeAdaptor(getContext(), biz_name, biz_name, docIds);

                    recyclerView.setAdapter(recycleradaptor);


                }
            });
//            }
//        });







        CarouselView carouselView = root.findViewById(R.id.carouselview);

        carouselView.setPageCount(images.length);


        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {


                imageView.setImageResource(images[position]);


            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

                Toast.makeText(getContext(), images[position],Toast.LENGTH_LONG).show();

            }
        });

        sharedPreferences = getActivity().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("emailId","Default");
        validateIfUserIsBusiness(email);

        settingrecommender();



        return root;

    }

    // getting the recommended counts and then sending to call those businesses
    private void settingrecommender() {

        recommendedbusiness = new ArrayList<>();
        star = new ArrayList<>();

        database.collection("business").orderBy("count", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

//                        Log.d("docslength",queryDocumentSnapshots.getDocuments().size()+"");
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();
                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(),value.toString());

                            });

                            recommendedbusiness.add(jsonObject.get("company_name").toString());


                            }

                        Log.d("recmproj", recommendedbusiness.toString());

                        HomeRecommendationAdapter homeRecommendationAdapter = new HomeRecommendationAdapter(recommendedbusiness);

                        recommendationRecyclerView.setAdapter(homeRecommendationAdapter);




                    }

                });

    }

    //validating if the logged in user owns a business
    private void validateIfUserIsBusiness(String email) {


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
                database.collection("business").whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Log.d("ifbiz", queryDocumentSnapshots.getDocuments() + "");

                        if (queryDocumentSnapshots.getDocuments().size() >= 1) {

                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                                Map data = new HashMap();
                                data = document.getData();
                                jsonObject = new JsonObject();
                                data.forEach((key, value) -> {

                                    jsonObject.addProperty(key.toString(), value.toString());

                                });
                            }
                            Log.d("company_name", jsonObject.get("company_name").toString());

                            sharedPreferences.edit().putString("userhascompany", jsonObject.get("company_name").toString()).apply();

                        }

                    }
                });
//            }
//        });

    }

    // handling all the button clicks in here to identify different cateories
    @Override
    public void onClick(View view) {


        String cat_name="";
        switch (view.getId()){

            case R.id.technology_category:
                cat_name = "Technology";
                break;
            case R.id.advertising_category:
                cat_name = "Advertising";
                break;
            case R.id.art_category:
                cat_name = "Art";
                break;
            case R.id.design_category:
                cat_name = "Design";
                break;
            case R.id.fashion_category:
                cat_name = "Fashion";
                break;
            case R.id.household_category:
                cat_name = "Household";
                break;
            case R.id.music_category:
                cat_name = "Music";
                break;
            default:
                break;

        }




        intent = new Intent(getContext(), category_page.class);
        intent.putExtra("category-name", cat_name+"");


        startActivity(intent);


    }
}