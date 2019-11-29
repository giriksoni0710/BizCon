package com.crazy4web.myapplication.ui.SearchResults;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.crazy4web.myapplication.ui.chat.MyAdaptor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.MyViewHolder> {

    private Context mcon;
    ArrayList company_name, tagline;
    List<String> docIds;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView companyname_view;

        public TextView tagline_view;

        public ImageView business_image;


        public MyViewHolder(View v) {

            super(v);

            cardView = v.findViewById(R.id.business_card_list);
            companyname_view = v.findViewById(R.id.companyName);
            tagline_view = v.findViewById(R.id.tagline_category);
            business_image = v.findViewById(R.id.business_image_category);
        }
    }

    public SearchAdaptor(Context con, ArrayList biz_name, ArrayList tagline, List docIds) {

        mcon = con;
        this.tagline = tagline;
        this.company_name = biz_name;
        this.docIds = docIds;
    }

    @Override
    public SearchAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        holder.tagline_view.setText(tagline.get(position).toString().replaceAll("\"",""));
        holder.companyname_view.setText(company_name.get(position).toString().replaceAll("\"", ""));


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bizcon-17781.appspot.com/Images/"+company_name.get(position).toString().replaceAll("\"","").trim()+".jpg");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Log.d("uri",uri+"");

                Glide.with(getApplicationContext()).load(uri).into(holder.business_image);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mcon, DetailActivityFragment.class);
                i.putExtra("docId",docIds.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcon.startActivity(i);

            }
        });

    }


    public int getItemCount() {

        return company_name.size();

    }}



