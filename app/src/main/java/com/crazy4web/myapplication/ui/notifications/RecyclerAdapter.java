package com.crazy4web.myapplication.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

//    List<String> list;
//    Context context;

//    public RecyclerAdapter(Context context, List<String> list) {
//        this.list = list;
//        this.context = context;
//    }
    private static final String TAG = "RecyclerAdapter";

//    public RecyclerAdapter(Context context) {
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.d(TAG, "onCreateViewHolder: called recyler adapter");
        
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView10;
        TextView textView11;
        ImageView imageView5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView10 = itemView.findViewById(R.id.textView10);
            textView11 = itemView.findViewById(R.id.textView11);
            imageView5 = itemView.findViewById(R.id.imageView5);
        }
    }
}

