package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.crazy4web.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;

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

    String token, companyname, emailname;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    Bundle bundle;
    String bizname,userhascompany;

    Set<String> mysentmsgs, bizsentmsg;
    ArrayList<String > rcvd, sent;

    JsonObject jsonObject;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        toolbar = findViewById(R.id.toolbar);
        send_img = findViewById(R.id.send_img);
        message = findViewById(R.id.type_message);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycler_inner_chat);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(false);



        SharedPreferences sp = getSharedPreferences("prefFile", Context.MODE_PRIVATE);

                Intent i = getIntent();
                bizname = i.getStringExtra("bizName");

              companyname = sp.getString("companyName","Default");

              if(bizname!=null){
                  companyname = bizname;
              }
                userhascompany= sp.getString("userhascompany","").replaceAll("\"","");

              //emailid->email
                emailname = sp.getString("emailName","Default");

                if(!userhascompany.equals("")){
                    emailname=userhascompany;
                    companyname=bizname;
                }
//                Log.d("values",bizname+" "+companyname+" "+emailname);

        getmessages();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",companyname).whereEqualTo("messageUser",emailname).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        mysentmsgs = new HashSet<>();
                        Log.d("companyname:"+companyname,"emailname"+emailname);

                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {

                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();
                            count = 0;
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

                    }
                }
        );

        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",emailname).whereEqualTo("messageUser",companyname).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                            }
                Log.d("I received", bizsentmsg+"");

                for (String msgs:
                        bizsentmsg) {

                    rcvd.add(msgs);
                }



                Log.d("The arrays are sent: "+sent,"rcvd: "+rcvd);
                mAdaptor = new ChatinnerAdaptor(getApplicationContext(), sent, rcvd);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdaptor);



            }

                }
        );





    }
}
