package com.crazy4web.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
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
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.crazy4web.myapplication.ui.categoryview.category_page;
import com.crazy4web.myapplication.ui.chat.MyAdaptor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomeFragment extends Fragment {

    ImageView categoryImage;
    private HomeViewModel homeViewModel;

    CardView cardView;
    Intent i;
    Intent intent;
    FirebaseFirestore database;
    TextView businessname;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycleradaptor;
    RecyclerView.LayoutManager layoutManager;


    Integer count=0;

    private int[] images = new int[]{
            R.drawable.categorypage1,R.drawable.categorypage2, R.drawable.categorypage1, R.drawable.categorypage2};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        String finaldata;

        businessname = root.findViewById(R.id.business_name);
        database = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.recycler_view_newlyadded);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

//        recycleradaptor = new HomeAdaptor(getContext(), jsonObject.get("company_name").toString(), jsonObject.get("image_url").toString());

        ArrayList biz_name = new ArrayList<>();
        ArrayList biz_img = new ArrayList<>();


        database.collection("business").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

//                Log.d("docs", queryDocumentSnapshots.getDocuments()+"");


                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {


                    Map data = new HashMap();

                    data = documentSnapshot.getData();

//                    Log.d("data",data.toString());

                    JsonObject jsonObject = new JsonObject();

                    data.forEach((key, value) -> {

                       jsonObject.addProperty(key.toString(),value.toString());


                    });

                    Log.d("data", jsonObject.get("company_name").toString());

                    biz_name.add(jsonObject.get("company_name").toString());

                    biz_img.add(jsonObject.get("image_path").toString());


                }


                recycleradaptor = new HomeAdaptor(getContext(), biz_name, biz_name);

                recyclerView.setAdapter(recycleradaptor);


            }
        });



        categoryImage = root.findViewById(R.id.design);
        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getContext(), category_page.class);

                startActivity(intent);

            }
        });

        // search



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

        return root;

    }


}