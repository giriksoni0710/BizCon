package com.crazy4web.myapplication.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<String> likedId;
    FirebaseFirestore database;
    FirebaseStorage firebaseStorage;
    Context mcon;

    public RecyclerAdapter(Context mcon, List likedId) {
        this.likedId = likedId;
        this.mcon = mcon;
    }

    private static final String TAG = "RecyclerAdapter";

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
        database = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        String id = likedId.get(position);

        database.collection("business").document(id).get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                task.getResult().getData().forEach((key, value)->{
                    switch (key){
                        case "company_name":
                            holder.like_companyName.setText(value.toString());
                            break;

                        case "tagline":
                            holder.like_tagline_category.setText(value.toString());
                            break;

                        case "image_path":

                            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(value.toString());
                            storageReference.getDownloadUrl().addOnSuccessListener(url ->{
                                Glide.with(getApplicationContext()).load(url).into(holder.like_business_image_category);
                            });
                            break;
                    }

                });
            }
        });
        holder.likes_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailActivityFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("docId", id);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return likedId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

//        TextView textView10;
//        TextView textView11;
        CardView likes_list;
        ImageView like_business_image_category;
//        RatingBar like_rating;
        TextView like_companyName;
        TextView like_tagline_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            like_business_image_category = itemView.findViewById(R.id.like_business_image_category);
            likes_list = itemView.findViewById(R.id.likes_list);
            like_companyName = itemView.findViewById(R.id.like_companyName);
            like_tagline_category = itemView.findViewById(R.id.like_tagline_category);
        }
    }
}

