package com.crazy4web.myapplication.ui.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.ArrayList;

public class ChatinnerAdaptor extends RecyclerView.Adapter<ChatinnerAdaptor.MyViewHolder>  {

        private Context mcon;
        public int posn;
        ArrayList<String> sent_msg, last_msg;

        private static final String TAG = "ChatinnerAdaptor";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ConstraintLayout constraintLayout;
            public TextView sentmsg,received, text2;
            public ImageView imageView, senderimage;



            public MyViewHolder(View v) {

                super(v);

                constraintLayout = v.findViewById(R.id.constrain_inner_chat);
                received = v.findViewById(R.id.received_msg);
                sentmsg = v.findViewById(R.id.sent_msg);
                imageView = v.findViewById(R.id.business_image_category);
                senderimage = v.findViewById(R.id.sender_image);
                text2 = v.findViewById(R.id.text2);
            }


        }

        public ChatinnerAdaptor(Context con, ArrayList<String> last_msg ) {

            mcon = con;
            this.last_msg = last_msg;
        }

        public ChatinnerAdaptor(ArrayList<String> biz_name ) {

            this.sent_msg = biz_name;

        }





        @Override
        public ChatinnerAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            MyViewHolder vh;
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent, parent, false);
            vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Log.d("sent_msg"+sent_msg,"last_msg"+last_msg);
        posn = position;


        }

        public int getItemCount() {

        return sent_msg.size() + last_msg.size();
        }
        }


