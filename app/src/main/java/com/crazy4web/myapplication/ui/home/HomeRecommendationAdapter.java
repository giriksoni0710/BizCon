package com.crazy4web.myapplication.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.zip.Inflater;

public class HomeRecommendationAdapter extends RecyclerView.Adapter<HomeRecommendationAdapter.myViewHolder> {

    private static final String TAG = "HomeRecommendationAdapter";
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "called recommendation");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_recommendation, parent, false);

        myViewHolder myViewHolder = new myViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
