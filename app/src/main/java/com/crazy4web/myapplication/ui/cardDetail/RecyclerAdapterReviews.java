package com.crazy4web.myapplication.ui.cardDetail;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.List;
import java.util.Map;

public class RecyclerAdapterReviews extends RecyclerView.Adapter<RecyclerAdapterReviews.MyViewHolder>{

    private static final String TAG = "RecyclerAdapterReviews";

//    String companyName;
    List<Map> ratingsList;
    Map ratings;

    public RecyclerAdapterReviews(List<Map> ratingsList) {
//        this.companyName = companyName;
        this.ratingsList = ratingsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "called reviews");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reviews_tab_rows, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ratings = ratingsList.get(position);

        ratings.forEach((key,value) ->{
            if(key.equals("userName")){
                holder.uName.setText(value.toString());
            }
            if(key.equals("comment")){
                holder.comment.setText(value.toString());
            }
            if(key.equals("rating")){
//                Float val = getFloatVal(value.toString());
                Log.d(TAG, value.toString());

                switch (value.toString()){
                    case "0.0":
//                        return 0.0f;
                        holder.rating.setRating(0.0f);
                        break;

                    case "0.5":
                        holder.rating.setRating(0.5f);
                        break;

                    case "1.0":
                        holder.rating.setRating(1.0f);
                        break;

                    case "1.5":
                        holder.rating.setRating(1.5f);
                        break;

                    case "2.0":
                        holder.rating.setRating(2.0f);
                        break;

                    case "2.5":
                        holder.rating.setRating(2.5f);
                        break;

                    case "3.0":
                        holder.rating.setRating(3.0f);
                        break;

                    case "3.5":
                        holder.rating.setRating(3.5f);
                        break;

                    case "4.0":
                        holder.rating.setRating(4.0f);
                        break;

                    case "4.5":
                        holder.rating.setRating(4.5f);
                        break;

                    case "5.0":
                        holder.rating.setRating(5.0f);
                        break;
                }
//                holder.rating.setRating(val);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView uName, comment;
        RatingBar rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            uName = itemView.findViewById(R.id.uName);
            rating = itemView.findViewById(R.id.rating);
            comment = itemView.findViewById(R.id.comment);

        }
    }

}
