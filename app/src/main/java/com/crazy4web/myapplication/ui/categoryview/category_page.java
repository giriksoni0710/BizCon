package com.crazy4web.myapplication.ui.categoryview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.crazy4web.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class category_page extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mlayoutmanager;
    RecyclerView.Adapter madaptor;
    String Category_name="";

    FirebaseFirestore database;
    ArrayList biz_name, tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        Toolbar toolbar = findViewById(R.id.toolbarCategoryList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // destroying the current intent on back button press to
        // go to the place where the intent was issued from
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // setting recyclerview
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(false);

        mlayoutmanager = new LinearLayoutManager(getApplicationContext());

        // getting the category name sent from the Home fragment
        Bundle bundle = getIntent().getExtras();

        Category_name = bundle.getString("category-name");

        database = FirebaseFirestore.getInstance();

        Log.d("name", Category_name+"");

        // here we fetch data from firebase based on category

        fetchdata(Category_name);





    }


    public void fetchdata(String category){

        biz_name = new ArrayList();
        tagline = new ArrayList();


        database.collection("business").whereEqualTo("category", category.toLowerCase()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {



                    Map data = new HashMap();

                    data = documentSnapshot.getData();

                    Log.d("data", documentSnapshot.getData().toString());

                    JsonObject jsonObject = new JsonObject();

                    data.forEach((key, value) -> {

                        jsonObject.addProperty(key.toString(),value.toString());


                    });


                    biz_name.add(jsonObject.get("company_name"));

                    tagline.add(jsonObject.get("tagline").toString());


                    Log.d("Biz", biz_name+""+""+tagline);


                    madaptor = new CategoryAdaptor(getApplicationContext(), biz_name, tagline);
                    recyclerView.setLayoutManager(mlayoutmanager);

                    recyclerView.setAdapter(madaptor);



                }


                }
        });

    }
}
