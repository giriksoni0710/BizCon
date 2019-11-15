package com.crazy4web.myapplication.ui.SearchResults;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.home.HomeAdaptor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Searchoperation extends AppCompatActivity {

    String searchedtext;

    FirebaseFirestore database;
    FirebaseStorage firebaseStorage;
    String cat;
    ArrayList<String> categories = new ArrayList<>();
    private static final String TAG = "Searchoperation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchoperation);

        database = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        searchedtext = bundle.getString("searchvalue");

//        Log.d("searchvalue", searchedtext);

        String[] list = searchedtext.split("\\s+");

        categories.add("Design");
        categories.add("Technology");
        categories.add("Advertising");
        categories.add("Art");
        categories.add("Fashion");
        categories.add("Household");
        categories.add("Music");

        categories.forEach(category->{

            for(int i=0;i<list.length;i++) {
                boolean b = Pattern.matches("(?i)"+category, list[i]);
                if(b){
//                    Log.d(TAG, ""+category+s);
                    cat = category;
                }
            }
        });

        firebaseStorage = FirebaseStorage.getInstance();
        database.collection("business").orderBy("category").startAt(cat).endAt(cat+"\uf8ff").get().addOnCompleteListener(task->{
            if(task.isSuccessful() && task.getResult().size() > 0) {
                Log.d(TAG, ""+task.getResult().getDocuments());
            }
        });

    }
}
