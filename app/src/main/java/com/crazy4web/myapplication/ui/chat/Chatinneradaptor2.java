package com.crazy4web.myapplication.ui.chat;

import android.content.Context;
import android.text.TextUtils;
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

public class Chatinneradaptor2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mcon;
    ArrayList rcvd_msg,sent_msg;

    int sender=1;
    int receiver=2;

    public Chatinneradaptor2(Context con, ArrayList<String> received_msg ) {

        mcon = con;
        this.rcvd_msg = received_msg;
    }

    public Chatinneradaptor2(ArrayList<String> sent_msg ) {

        this.sent_msg = sent_msg;

    }

    // this viewholder stores the sender messages

    private static class Senderviewholder extends RecyclerView.ViewHolder {


        public ConstraintLayout constraintLayout;
        public TextView sentmsg, text2;
        public ImageView imageView;

        public Senderviewholder(View v) {
            super(v);
        // preparing the viewholder

            sentmsg = v.findViewById(R.id.sent_msg);
            imageView = v.findViewById(R.id.business_image_category);
            text2 = v.findViewById(R.id.text2);
        }

    }

    //this viewholder displays the sender messages

    private static class ReceiverViewHolder extends RecyclerView.ViewHolder {

        public TextView received_msg;
        public ImageView  receiver_image;

        public ReceiverViewHolder (@NonNull View v) {
            super(v);

            receiver_image = v.findViewById(R.id.receiver_image);

            received_msg = v.findViewById(R.id.received_msg);

        }
        // like the one above


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == sender) { // for call layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent, parent, false);
            return new Senderviewholder(view);

        } else { // for email layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (rcvd_msg!=null) {
            return receiver;
        } else {
            return sender;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == sender) {
            ((Senderviewholder) holder).sentmsg.setText(sent_msg.get(position).toString().replaceAll("\"",""));
        } else {
            ((ReceiverViewHolder) holder).received_msg.setText(rcvd_msg.get(position).toString().replaceAll("\"",""));
        }
    }

    @Override
    public int getItemCount() {
//
        if (sent_msg!=null)
            if(sent_msg!=null && rcvd_msg!=null)
            return 2;
            else if (sent_msg!=null && rcvd_msg==null)
                return sent_msg.size();
            else
                return 1;


        if (rcvd_msg!=null)
            if(sent_msg==null)
                return rcvd_msg.size();
            else if (sent_msg!=null)
                return 2;
            else return 0;

            return 2;
    }
}
