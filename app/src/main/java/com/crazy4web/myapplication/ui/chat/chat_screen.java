package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crazy4web.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public class chat_screen extends AppCompatActivity {

    Toolbar toolbar;
    ImageView send_img;
    EditText message;
    Drawable drawable;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager layoutManager;
    int count =0;
    ImageView imageView, img3;

    String token, companyname, emailname;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    Bundle bundle;
    String bizname,userhascompany;

    Set<String> mysentmsgs, bizsentmsg;
    ArrayList<String > rcvd, sent;

    JsonObject jsonObject;
    int countnew=0;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView msg2, business_name_toolbar;


    TextView msg, msg3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        imageView = findViewById(R.id.receiver_image);
        msg2 = findViewById(R.id.sent_msg);
        msg = findViewById(R.id.received_msg);
        img3 = findViewById(R.id.img5);
        msg3 = findViewById(R.id.msg3);



        imageView.setVisibility(View.GONE);
        msg.setVisibility(View.GONE);
        msg2.setVisibility(View.GONE);
        msg3.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);



        toolbar = findViewById(R.id.toolbar);
        send_img = findViewById(R.id.send_img);
        message = findViewById(R.id.type_message);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        business_name_toolbar = findViewById(R.id.business_name_toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences sp = getSharedPreferences("prefFile", Context.MODE_PRIVATE);

                Intent i = getIntent();
                bizname = i.getStringExtra("bizName");
                String businessname=i.getStringExtra("businessname");
                companyname = sp.getString("companyName","Default");

                String googlename = sp.getString("googleName", "Default");
                String fbname = sp.getString("fbName", "Default");



        if(bizname!=null){
                  companyname = bizname;
                    Log.d("companyname", companyname);
              }
                userhascompany= sp.getString("userhascompany","").replaceAll("\"","");

              //emailid->email
                emailname = sp.getString("emailName","Default");

                // check the length of all and set the loggedin user

                if(googlename.length()>emailname.length()){
                    emailname=googlename;
                }else if(fbname.length()>emailname.length()){
                    emailname=fbname;
                }

                if(!userhascompany.equals("")){
                    emailname=userhascompany;
                    companyname=bizname;
                }

                Log.d("values",bizname+" "+companyname+" "+emailname);

             getmessages();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
              toolbar.setTitle("");
              toolbar.setSubtitle("");


        //firebase messaging token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task ->{

            if (!task.isSuccessful()){
                Log.d("error","instance id not found");
            }

            token = task.getResult().getToken();
//            Log.d("token", token);

        });


        send_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                firebaseFirestore.collection("messages").add(new ChatMessage(message.getText().toString()
                        ,emailname,companyname)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        message.setText("");

                            getmessages();


                    }
                });



            }
        });



    }

    private void getmessages() {
        sent = new ArrayList<>();
        rcvd = new ArrayList<>();


        //sent messages
        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",companyname).whereEqualTo("messageUser",emailname).limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        mysentmsgs = new HashSet<>();
                        Log.d("companyname:"+companyname,"emailname"+emailname);
                        business_name_toolbar.setText(companyname);
                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {

                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();

                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(),value.toString());

                                mysentmsgs.add(jsonObject.get("messageText").toString());
                            });


                            Log.d("I sent", mysentmsgs+"");
                        }
                for (String msgs:
                        mysentmsgs) {

                    sent.add(msgs);
                }

                countnew++;

                Log.d("sent",sent.toString());


                    }
                }
        );


        //Received messages
        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",emailname).whereEqualTo("messageUser",companyname).limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            bizsentmsg = new HashSet<>();

                            for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {


                                Log.d("docs",document.getData().toString());


                                Map data = new HashMap();
                                data = document.getData();
                                jsonObject = new JsonObject();
                                data.forEach((key, value) -> {

                                    jsonObject.addProperty(key.toString(),value.toString());
//                                    Log.d("values", ""+value);
                                    bizsentmsg.add(jsonObject.get("messageText").toString());

                                });

                                count++;
                            }

                Log.d("I received", bizsentmsg+"");

                for (String msgs:
                        bizsentmsg) {

                    rcvd.add(msgs);
                }


                Log.d("The arrays are sent: "+sent,"rcvd: "+rcvd);



                if(sent!=null && sent.size()>=1){

                    Log.d("sent messages",sent+"");
                    msg2.setText(sent.get(0).replaceAll("\"",""));
                    msg2.setVisibility(View.VISIBLE);

                }

                if(rcvd!=null && rcvd.size()>=1){

                    Log.d("rcvd messages",rcvd+"");


                    if(!userhascompany.equals("")){

                        msg.setText(rcvd.get(0).replaceAll("\"",""));
                        imageView.setVisibility(View.VISIBLE);
                        msg.setVisibility(View.VISIBLE);

                        Glide.with(getApplicationContext())
                                .load(R.drawable.profile_pic_default)
                                .apply(RequestOptions.circleCropTransform())
                                .into(imageView);

                    }
                    else{

                        img3.setVisibility(View.VISIBLE);
                        msg3.setText(rcvd.get(0).replaceAll("\"",""));
                        msg3.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);

                        Glide.with(getApplicationContext())
                                .load(R.drawable.profile_pic_default)
                                .apply(RequestOptions.circleCropTransform())
                                .into(img3);

                    }


                }




            }


            }





        );





    }
}
