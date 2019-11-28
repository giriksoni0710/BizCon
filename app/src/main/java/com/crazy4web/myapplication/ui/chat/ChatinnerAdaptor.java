package com.crazy4web.myapplication.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crazy4web.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ChatinnerAdaptor extends RecyclerView.Adapter<ChatinnerAdaptor.MyViewHolder>  {

        private Context mcon;
        ArrayList<String> sent_msg, last_msg;

        private static final String TAG = "ChatinnerAdaptor";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ConstraintLayout constraintLayout;
            public TextView sentmsg,received;
            public ImageView imageView, senderimage;



            public MyViewHolder(View v) {

                super(v);

                constraintLayout = v.findViewById(R.id.constrain_inner_chat);
                sentmsg = v.findViewById(R.id.sent_msg);
                received = v.findViewById(R.id.received_msg);
                imageView = v.findViewById(R.id.business_image_category);
                senderimage = v.findViewById(R.id.sender_image);
            }
        }


        public ChatinnerAdaptor(Context con, ArrayList<String> biz_name, ArrayList<String> last_msg ) {

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

        Log.d("sentmsgs"+sent_msg.size(),"rcvd"+last_msg.size());


        if(sent_msg.size()>0)


                if(sent_msg.size()>=1 && sent_msg.size()-1>=position) {
                    holder.sentmsg.setText(sent_msg.get(position).replaceAll("\"", ""));
                }
                else if(sent_msg.size()-1<=position){
                    holder.sentmsg.setVisibility(View.GONE);
                }

            if(last_msg.size()>=1 && last_msg.size()-1 >= position) {
                  holder.received.setText(last_msg.get(position).replaceAll("\"", ""));
                Glide.with(mcon)
                        .load(R.drawable.categorypage1)
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.senderimage);

            }else if(last_msg.size()-1<= position){
                    holder.received.setTextSize(0);
                holder.received.setPadding(0,0,0,0);
                holder.senderimage.setImageResource(0);

                }
        }

        public int getItemCount() {

        if (sent_msg.size()>last_msg.size() || sent_msg.size()==last_msg.size())
            return sent_msg.size();

        else if(sent_msg.size()< last_msg.size())
            return last_msg.size();

        else return 0;
            }
        }


