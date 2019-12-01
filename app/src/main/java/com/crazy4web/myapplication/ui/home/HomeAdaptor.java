package com.crazy4web.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.cardDetail.DetailActivityFragment;
import com.crazy4web.myapplication.ui.categoryview.CategoryAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeAdaptor extends RecyclerView.Adapter<HomeAdaptor.MyViewHolder> {

    private Context mcon;
    ArrayList companyname, image, businesses;
    List<String> docIds;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    SharedPreferences sp;
    String loggedinuser;
    FirebaseStorage storage;
    FirebaseFirestore database;
    HashMap premium, recommender, name2;
    int once;
    JsonObject jsonObject;
    JsonObject jsoncount;
    String document_ID;

    int count;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView company_name;
        public ImageView imageView;
        CardView business_card;


        public MyViewHolder(View v) {

            super(v);

            linearLayout = v.findViewById(R.id.newly_added_linearlayout);
            company_name = v.findViewById(R.id.business_name);
            imageView = v.findViewById(R.id.biz_img);
            business_card = v.findViewById(R.id.business_card);
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

        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bizcon-17781.appspot.com/Images/"+companyname.get(position).toString().replaceAll("\"","").trim()+".jpg");

        Log.d("companyname",companyname.get(position).toString().trim());
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

                    sp = mcon.getSharedPreferences("prefFile",Context.MODE_PRIVATE);
                    String googleEmailId = sp.getString("googleEmailId","");
                    String fbEmailId = sp.getString("fbEmailId","");
                    String emailId = sp.getString("emailId","");


                    Log.d("google:fb:email", googleEmailId+":"+fbEmailId+":"+emailId);

                    if(emailId.length()>googleEmailId.length() && emailId.length()>fbEmailId.length()){
                        loggedinuser = emailId;
                    }else if(googleEmailId.length()>emailId.length() && googleEmailId.length()>fbEmailId.length()){
                    loggedinuser = googleEmailId;
                }else if(fbEmailId.length()>googleEmailId.length() && fbEmailId.length()>emailId.length()){
                    loggedinuser = fbEmailId;
                }

                database = FirebaseFirestore.getInstance();
                    // here is the loggedin user
                Log.d("loggedinuser",loggedinuser);
                    //this is the name of the company clicked
                Log.d("companyname",""+companyname.get(position));

                premium = new HashMap();
                recommender = new HashMap<>();

                // this is the hashmap we add to the collection
                premium.put("user", loggedinuser);
                premium.put("businesses",companyname.get(position).toString().replaceAll("\"",""));


//                // here we add to the premium collection
//                database.collection("Premium").add(premium).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//
//                        Log.d("inserted","inserted");
//
//                    }
//                });

//

                // retreiving data and adding one to count and updating

                database.collection("Recommender")
                        .whereEqualTo("businessname",companyname
                                .get(position).toString().replaceAll("\"",""))
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

//                Log.d("docs", queryDocumentSnapshots.getDocuments()+"");

                        // if there are no docs add it to the database
                        if (queryDocumentSnapshots.size() == 0) {
                            Log.d("no docs", "no docs");

                            count = 1;

                            recommender = new HashMap<>();
                            name2 = new HashMap<>();
                            recommender.put("count", count);
                            recommender.put("businessname", companyname.get(position).toString().replaceAll("\"", ""));
                            name2.put("count", count);
                            // here we add to the company count

                            database.collection("Recommender").add(recommender)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {

                                            Log.d("inserted", "inserted");

                                        }
                                    });


                            // updating count on business table

                            database.collection("business").document(docIds.get(position))
                                    .set(name2, SetOptions.merge());

                        }
                        // else update the previous one
                        else {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                                Map data = new HashMap();

                                data = documentSnapshot.getData();
                                String data2 = documentSnapshot.getId();

                                document_ID = documentSnapshot.getId();
                                docIds.add(data2);


                                data.forEach((key, value) -> {

                                    if (key.equals("count")) {

                                        count = Integer.parseInt(value.toString());
                                    }

                                });

                            }


                            Log.d("count", count + "id:" + document_ID);

                            // here we increment the count by 1 and update the same document

                            Map<String, Object> city = new HashMap<>();

                            Map<String, Object> name = new HashMap<>();
                            city.put("count", count + 1);
                            city.put("businessname", companyname
                                    .get(position).toString().replaceAll("\"", ""));
                            name.put("count", count + 1);

                            database.collection("Recommender").document(docIds.get(position))
                                    .set(city)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Log.d("updated", "count");
                                            }

                                        }
                                    });

                            // updating count on business table
                            database.collection("business").document(docIds.get(position))
                                    .set(name, SetOptions.merge());

                        }
                    }
                });





                // no. of times people viewed business
                database.collection("Premium").whereEqualTo("businesses", "sk plumbers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Log.d("docs",queryDocumentSnapshots.getDocuments().size()+"");

                    }
                });





                Intent i = new Intent(getApplicationContext(), DetailActivityFragment.class);
                i.putExtra("docId",docIds.get(position));
                mcon.startActivity(i);
            }
        });
//            holder.business_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Log.d("click","works");
//                Intent i = new Intent(getApplicationContext(), DetailActivityFragment.class);
//                i.putExtra("docId",docIds.get(position));
//                mcon.startActivity(i);
//            }
//        });



    }




    public int getItemCount() {

        return companyname.size();

    }}
