package com.crazy4web.myapplication.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.ArrayList;

public class ChatinnerAdaptor extends RecyclerView.Adapter<ChatinnerAdaptor.MyViewHolder>  {


        private Context mcon;

        ArrayList<String> sent_msg, last_msg = new ArrayList<>();


        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ConstraintLayout constraintLayout;
            public TextView sentmsg,received;



            public MyViewHolder(View v) {

                super(v);

                constraintLayout = v.findViewById(R.id.constrain_inner_chat);
                sentmsg = v.findViewById(R.id.sent_msg);
                received = v.findViewById(R.id.received_msg);
            }
        }


        public ChatinnerAdaptor(Context con, ArrayList biz_name, ArrayList last_msg ) {

            mcon = con;
            this.sent_msg = biz_name;
            this.last_msg = last_msg;
        }






        @Override
        public ChatinnerAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_inner_messages, parent, false);



            MyViewHolder vh = new MyViewHolder(v);
            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



            if(sent_msg.get(position)!=null && last_msg.get(position)!=null) {
                holder.sentmsg.setText(sent_msg.get(position).toString().replaceAll("\"", ""));
                holder.received.setText(last_msg.get(position).toString().replaceAll("\"", ""));

            }


        }


        public int getItemCount() {

                return last_msg.size();

            }
        }


