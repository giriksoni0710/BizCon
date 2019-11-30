package com.crazy4web.myapplication.ui.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeRecommendationAdapter extends RecyclerView.Adapter<HomeRecommendationAdapter.myViewHolder> {

    private static final String TAG = "HomeRecommendationAdapter";
    ArrayList name;
    ArrayList rating;

    public HomeRecommendationAdapter(ArrayList name){
    this.name=name;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_recommendation, parent, false);

        myViewHolder myViewHolder = new myViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.businessname.setText(name.get(position).toString().replaceAll("\"",""));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bizcon-17781.appspot.com/Images/"+name.get(position).toString().replaceAll("\"","").trim()+".jpg");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Log.d("uri",uri+"");

                Glide.with(getApplicationContext()).load(uri).into(holder.imageView);

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{


        RatingBar ratingBar;
        TextView businessname;
        ImageView imageView;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);

            ratingBar = itemView.findViewById(R.id.recommender_rating);
            businessname = itemView.findViewById(R.id.recommender_name);
            imageView = itemView.findViewById(R.id.recommender_img);
        }
    }


}
