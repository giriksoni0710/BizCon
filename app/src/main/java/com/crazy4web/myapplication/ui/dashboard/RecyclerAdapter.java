package com.crazy4web.myapplication.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecyclerAdapter";

    List<String> menuItems;


    public RecyclerAdapter(List<String>  menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_dashboard_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: "+position);
        holder.dashboardMenuItems.setText(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView dashboardMenuItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dashboardMenuItems = itemView.findViewById(R.id.dashboardMenuItems);
        }
    }
}
