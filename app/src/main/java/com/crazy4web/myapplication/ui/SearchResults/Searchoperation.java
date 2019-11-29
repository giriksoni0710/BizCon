package com.crazy4web.myapplication.ui.SearchResults;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.categoryview.CategoryAdaptor;
import com.crazy4web.myapplication.ui.home.HomeAdaptor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Searchoperation extends AppCompatActivity {

    String searchedtext;
    FirebaseFirestore database;
    FirebaseStorage firebaseStorage;
    String cat;
    ArrayList<String> categories, biz_name, tagline = new ArrayList<>();
    private static final String TAG = "Searchoperation";
    JsonObject jsonObject;
    List<String> docIds;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mlayoutmanager;
    RecyclerView.Adapter madaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchoperation);

        recyclerView = findViewById(R.id.recycler_view_category);

        mlayoutmanager = new LinearLayoutManager(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbarCategoryList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Results");
        toolbar.setElevation(0);

        // destroying the current intent on back button press to
        // go to the place where the intent was issued from
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        database = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        searchedtext = bundle.getString("searchvalue");

//        Log.d("searchvalue", searchedtext);

        String[] list = searchedtext.split("\\s+");

        String[] db_keys = new String[2];

        db_keys[0] = "services";
        db_keys[1] = "category";

        biz_name = new ArrayList();
        tagline = new ArrayList();
        docIds = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();



        for (String value: db_keys) {

        for(int i=0;i<list.length;i++) {


                if(list[i].length()>4){



            database.collection("business").orderBy(value).startAt(list[i].toLowerCase()).endAt(list[i].toLowerCase() + "\uf8ff").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {


                                docIds.add(documentSnapshot.getId());
                                Map data = new HashMap();

                                data = documentSnapshot.getData();

                                jsonObject = new JsonObject();

                                data.forEach((key, value) -> {

                                    jsonObject.addProperty(key.toString(), value.toString());

                                });

                                biz_name.add(jsonObject.get("company_name").toString());

                                tagline.add(jsonObject.get("tagline").toString());
                                madaptor = new SearchAdaptor(getApplicationContext(), biz_name, tagline, docIds);
                                recyclerView.setLayoutManager(mlayoutmanager);

                                recyclerView.setAdapter(madaptor);

                            }


                        }

                    });

            }}
        }


        // Ankurs code

//        categories.add("Design");
//        categories.add("Technology");
//        categories.add("Advertising");
//        categories.add("Art");
//        categories.add("Fashion");
//        categories.add("Household");
//        categories.add("Music");
//
//        categories.forEach(category->{
//
//            for(int i=0;i<list.length;i++) {
//                boolean b = Pattern.matches("(?i)"+category, list[i]);
//                if(b){
////                    Log.d(TAG, ""+category+s);
//                    cat = category;
//                }
//            }
//        });
//
//        firebaseStorage = FirebaseStorage.getInstance();
//        database.collection("business").orderBy("category").startAt(cat).endAt(cat+"\uf8ff").get().addOnCompleteListener(task->{
//            if(task.isSuccessful() && task.getResult().size() > 0) {
//                Log.d(TAG, ""+task.getResult().getDocuments());
//
//
//
//
//            }
//        });

    }
}
