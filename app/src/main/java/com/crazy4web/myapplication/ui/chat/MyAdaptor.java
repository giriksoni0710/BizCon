package com.crazy4web.myapplication.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;

import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    private Context mcon;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout constraintLayout;


        public MyViewHolder(View v) {

            super(v);

            constraintLayout = v.findViewById(R.id.chatrow);
        }
    }


    public MyAdaptor(Context con ) {


        mcon = con;

    }






    @Override
    public MyAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {




        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // here I have to call the individual chats of the user on click
                // I will leave it like this for now
                Log.d("click","works");
//               Intent i = new Intent(mcon, MainActivity.class);




            }
        });



    }


    public int getItemCount() {

        return 40;

    }}
