package com.crazy4web.myapplication.ui.cardDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.Login.Email_login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class WriteReview extends AppCompatActivity {

    RatingBar write_a_review_rating;
    Button btnn;
    TextView ratingView;
    ImageView crossReview;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    SharedPreferences sp;
    FirebaseFirestore database;
    Map review_obj;
    TextInputLayout commentSection;
    private static final String TAG = "WriteReview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_a_review);

        database = FirebaseFirestore.getInstance();
        sp = getApplicationContext().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        Log.d(TAG,sp.getString("bid","Def"));

        crossReview = findViewById(R.id.crossReview);
        write_a_review_rating = findViewById(R.id.write_a_review_rating);
        btnn = findViewById(R.id.btnn);
        commentSection = findViewById(R.id.commentSection);
//        ratingView = findViewById(R.id.ratingView);

        crossReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String googleEmailId = sp.getString("googleEmailId","");
        String fbEmailId = sp.getString("fbEmailId","");
        String emailId = sp.getString("emailId","");
        String googleName = sp.getString("googleName","");
        String fbName = sp.getString("fbName","");
        String uName = sp.getString("emailName","");
        String bid = sp.getString("bid","");

//        Log.d(TAG, googleEmailId+fbEmailId+emailId+uName);

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noofstars = write_a_review_rating.getNumStars();
                float getrating = write_a_review_rating.getRating();
                String getComment = commentSection.getEditText().getText().toString();

                review_obj = new HashMap();
                review_obj.put("bid",sp.getString("bid",""));
                review_obj.put("rating",getrating);
                review_obj.put("comment",getComment);

                if(!googleEmailId.equals("") && !googleName.equals("")) {
                    review_obj.put("userEmail", googleEmailId);
                    review_obj.put("userName", googleName);
                }else if(!fbEmailId.equals("") && !fbName.equals("")){
                    review_obj.put("userEmail", fbEmailId);
                    review_obj.put("userName", fbName);
                }else if(!emailId.equals("") && !uName.equals("")){
                    review_obj.put("userEmail", emailId);
                    review_obj.put("userName", uName);
                }

                database.collection("reviews").add(review_obj)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                            sp.edit().putString("googleEmailId","").apply();
//                            sp.edit().putString("fbEmailId","").apply();
//                            sp.edit().putString("emailId","").apply();
//                            sp.edit().putString("googleName","").apply();
//                            sp.edit().putString("fbName","").apply();
//                            sp.edit().putString("emailName","").apply();
                            Toast.makeText(getApplicationContext(),"Thank you for the review", Toast.LENGTH_LONG).show();
//                            finish();

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Error while uplaoding reviews. Pleas etry again after sometime", Toast.LENGTH_LONG).show();
                        }
                    });

            }
        });

    }
}
