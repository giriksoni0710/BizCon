package com.crazy4web.myapplication.ui.cardDetail;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    String service,id;
    FirebaseFirestore database;
    ArrayList<String> val = new ArrayList<>();

//    public RecyclerAdapter(String id) {
////        this.service = service;
//        this.id = id;
//    }
public RecyclerAdapter(ArrayList<String> val) {
        this.val = val;
//        this.id = id;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Log.d(TAG, "onCreateViewHolder: called recyler adapter");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.services_tab_rows, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        database = FirebaseFirestore.getInstance();
//        database.collection("business").document(id).get().addOnCompleteListener(task ->{
//            if(task.isSuccessful()){
//                Log.d(TAG, ""+task.getResult().getData());
//                task.getResult().getData().forEach((key, value)->{
////                    Log.d(TAG, key+" -> "+value);
////                    arr.add(value.toString());
//                    if(key.contains("services")){
////                        Log.d(TAG, value.toString());
//                        Log.d(TAG, "onBindViewHolder: ");
//
//                        val = (ArrayList<String>) value;
//
//
////                        String[] stringValues = (String[])value[position];
////                        JSONArray val = new JSONArray();
//
////                        val.put()
////                        String[] strings = (String[]) ((Object[])value)[position];
//
//
////                        try {
                            holder.serviceRow.setText((val.get(position)));
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                });
//            }
//        });

//        holder.serviceRow.setText(service);
//        Log.d(TAG, "from recycler adapter after service.setText is done to: "+service);
//        Log.d(TAG, "serv: "+service);
    }

    @Override
    public int getItemCount() {
        return val.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView serviceRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceRow = itemView.findViewById(R.id.serviceRow);
        }
    }
}
