package com.crazy4web.myapplication.ui.categoryview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.crazy4web.myapplication.R;

public class category_page extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mlayoutmanager;
    RecyclerView.Adapter madaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        Toolbar toolbar = findViewById(R.id.toolbarCategoryList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(false);

        madaptor = new CategoryAdaptor(this);

        mlayoutmanager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mlayoutmanager);

        recyclerView.setAdapter(madaptor);





    }
}
