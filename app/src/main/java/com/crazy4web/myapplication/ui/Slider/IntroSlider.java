package com.crazy4web.myapplication.ui.Slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;

public class IntroSlider extends AppCompatActivity {

    SharedPreferences preferences = null;

    private ViewPager viewPager;
    private LinearLayout relativeLayout;
    private  SliderAdaptor sliderAdaptor;
    private TextView[] mdots;

    private Button btnnext, btnprev;

    // to see what page we are on
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        // Checking the first run of the application

        preferences = getSharedPreferences("com.crazy4web.myapplication",MODE_PRIVATE);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        relativeLayout = (LinearLayout) findViewById(R.id.dots);

        sliderAdaptor = new SliderAdaptor(this);

        btnnext = findViewById(R.id.nextbtn);
        btnprev = findViewById(R.id.prvsbtn);

        viewPager.setAdapter(sliderAdaptor);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewlistener);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(currentPage + 1);

                if(currentPage==2){

                    btnnext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent newactivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(newactivity);


                        }
                    });


                }

            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage - 1);
            }
        });


    }

    // Checking the first time launch in the onresume


    @Override
    protected void onResume() {
        super.onResume();
        if(preferences.getBoolean("firstrun", true)) {


            preferences.edit().putBoolean("firstrun", false).commit();

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }
        }


    public void addDotsIndicator(int position){

        mdots = new TextView[3];
        relativeLayout.removeAllViews();

        for(int i = 0; i<mdots.length; i++){

            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.design_default_color_primary_dark));

            relativeLayout.addView(mdots[i]);
        }

        if(mdots.length>0){

            mdots[position].setTextColor(getResources().getColor(R.color.dotsColor));
        }


    }


    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            currentPage = position;

            if(position == 0){

                btnnext.setEnabled(true);
                btnprev.setEnabled(false);
                btnprev.setVisibility(View.INVISIBLE);

                btnnext.setText("Next");
                btnprev.setText("");
            }

            else if(position == mdots.length-1) {

                btnnext.setEnabled(true);
                btnprev.setEnabled(true);
                btnprev.setVisibility(View.VISIBLE);

                btnnext.setText("Finish");
                btnprev.setText("Back");

            }
            else {

                btnnext.setEnabled(true);
                btnprev.setEnabled(true);
                btnprev.setVisibility(View.VISIBLE);

                btnnext.setText("Next");
                btnprev.setText("Back");

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {



        }
    };

}
