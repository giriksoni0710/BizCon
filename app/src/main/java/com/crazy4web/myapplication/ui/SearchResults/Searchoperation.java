package com.crazy4web.myapplication.ui.SearchResults;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.crazy4web.myapplication.R;

public class Searchoperation extends AppCompatActivity {

    String searchedtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchoperation);

        Bundle bundle = getIntent().getExtras();
        searchedtext = bundle.getString("searchvalue");

        Log.d("searchvalue", searchedtext);
    }
}
