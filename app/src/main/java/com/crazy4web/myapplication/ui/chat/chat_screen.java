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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    String bizname;

    ArrayList<String> my_message;

    ArrayList<String> last_message;

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
              //emailid->email
                emailname = sp.getString("emailName","Default");

                Log.d("values",bizname+" "+companyname+" "+emailname);

        getmessages();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //firebase messaging token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task ->{

            if (!task.isSuccessful()){
                Log.d("error","instance id not found");
            }

            token = task.getResult().getToken();
            Log.d("token", token);

        });


        send_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                firebaseFirestore.collection("messages").add(new ChatMessage(message.getText().toString()
                        ,emailname,companyname)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d("docssent", documentReference.getId());

                        message.setText("");

                        getmessages();
                    }
                });



            }
        });


    }

    private void getmessages() {


        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",companyname).whereEqualTo("messageUser",emailname).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {

                            Log.d("docs", document.getData()+"");

                            my_message= new ArrayList<>();


                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();
                            count = 0;
                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(),value.toString());
                                    my_message.add(jsonObject.get("messageText").toString());

                            });


//                            Log.d("I sent", my_message+"");
                        }

                    }
                }
        );

        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",emailname).whereEqualTo("messageUser",companyname).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {

                                Log.d("docs", document.getData()+"");

////                                last_message = new ArrayList<>();
////
////                                Map data = new HashMap();
////                                data = document.getData();
////                                jsonObject = new JsonObject();
////                                data.forEach((key, value) -> {
////
////                                    jsonObject.addProperty(key.toString(),value.toString());
////
////                                    last_message.add(jsonObject.get("messageText").toString());
////
////                                });
//
//
//
////                                Log.d("I received", last_message+"");
//
//
//                                Log.d("messages","my message is "+my_message+" The last message is"+last_message);
//
//
//                                mAdaptor = new ChatinnerAdaptor(getApplicationContext(), my_message, last_message);
//                                recyclerView.setLayoutManager(layoutManager);
//                                recyclerView.setAdapter(mAdaptor);

                            }


                    }

                }
        );





    }
}
