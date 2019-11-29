package com.crazy4web.myapplication.ui.cardDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReviewsTab extends Fragment {

    private static final String TAG = "ReviewsTab";
    RecyclerView recyclerViewReviews;
//    RecyclerAdapterReviews recyclerAdapterReviews;
//    FirebaseFirestore database;
//    List<Map> ratingsList = new ArrayList<>();
//    SharedPreferences sp;
//    private String prefFile = "com.crazy4web.myapplication.userdata";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.reviews_tab, container, false);

//        sp = getApplicationContext().getSharedPreferences("prefFile", Context.MODE_PRIVATE);
//        database = FirebaseFirestore.getInstance();
//        database.collection("reviews").whereEqualTo("bid",sp.getString("bid","")).get()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
////                                        Log.d(TAG, task.getResult().getDocuments().toString());
//
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            ratingsList.add(document.getData());
//                        }
//                        Log.d(TAG, ""+ratingsList.size());
//                    }
//                });
//
//        recyclerViewReviews = root.findViewById(R.id.recyclerViewReviews);
//
//
//        recyclerAdapterReviews = new RecyclerAdapterReviews(ratingsList);
//        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerViewReviews.setAdapter(recyclerAdapterReviews);
        return root;
    }
}
