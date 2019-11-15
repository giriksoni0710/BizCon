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
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Searchoperation extends AppCompatActivity {

    String searchedtext;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchoperation);

        database = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        searchedtext = bundle.getString("searchvalue");

        Log.d("searchvalue", searchedtext);

        String[] split = searchedtext.split("\\s+");

        String[] searchitems = new String[3];

        for (int i=0; i<split.length;i++) {


            database.collection("business").orderBy("category").startAt(split[i]).endAt(split[i]+"\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Log.d("docs", queryDocumentSnapshots.getDocuments() + "");


                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {


                            Map data = new HashMap();

                            data = documentSnapshot.getData();

                            JsonObject jsonObject = new JsonObject();

                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(), value.toString());

                            });


                        }


                    }
                });


       }

    }
}
