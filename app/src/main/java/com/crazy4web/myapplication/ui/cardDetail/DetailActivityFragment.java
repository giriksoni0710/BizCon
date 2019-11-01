package com.crazy4web.myapplication.ui.cardDetail;

import android.os.Bundle;

import com.crazy4web.myapplication.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.crazy4web.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class DetailActivityFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"fab clicked", Toast.LENGTH_LONG).show();
//            }
//        });

        final ViewPager viewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(swipeAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if(tab.getPosition() == 2){
//                    ratingBar.setRating(int );
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        viewPager.setOffscreenPageLimit(1); // how many fragments you want to load in the memory
//        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(swipeAdapter);
//        viewPager.setCurrentItem(0);//the first fragment
    }

}
