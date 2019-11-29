package com.crazy4web.myapplication.ui.cardDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.crazy4web.myapplication.R;

public class WriteReview extends AppCompatActivity {

    RatingBar write_a_review_rating;
    Button btnn;
    TextView ratingView;
    ImageView crossReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_a_review);


        crossReview = findViewById(R.id.crossReview);
        write_a_review_rating = findViewById(R.id.write_a_review_rating);
        btnn = findViewById(R.id.btnn);
        ratingView = findViewById(R.id.ratingView);

        crossReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noofstars = write_a_review_rating.getNumStars();
                float getrating = write_a_review_rating.getRating();
                ratingView.setText("Rating: "+getrating+"/"+noofstars);
            }
        });

    }
}
