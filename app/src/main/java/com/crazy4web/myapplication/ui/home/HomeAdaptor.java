package com.crazy4web.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeAdaptor extends RecyclerView.Adapter<HomeAdaptor.MyViewHolder> {

    private Context mcon;
    ArrayList companyname, image;
    List<String> docIds;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView company_name;
        public ImageView imageView;


        public MyViewHolder(View v) {

            super(v);

            linearLayout = v.findViewById(R.id.newly_added_linearlayout);
            company_name = v.findViewById(R.id.business_name);
            imageView = v.findViewById(R.id.biz_img);
        }
    }


    public HomeAdaptor(Context con,ArrayList company_name, ArrayList company_image ,List<String> docIds) {


        mcon = con;
        companyname = company_name;
        image = company_image;
        this.docIds = docIds;

    }






    @Override
    public HomeAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newlyadded, parent, false);



        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {




        holder.company_name.setText(image.get(position).toString().replaceAll("\"",""));

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bizcon-17781.appspot.com/Images/Bizcon.jpg");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Log.d("uri",uri+"");

                Glide.with(getApplicationContext()).load(uri).into(holder.imageView);

            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("click","works");
                Intent i = new Intent(getApplicationContext(), DetailActivityFragment.class);
                i.putExtra("docId",docIds.get(position));
                mcon.startActivity(i);
            }
        });



    }

    public int getItemCount() {

        return companyname.size();

    }}
