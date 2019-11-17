package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
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

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;

public class chat_screen extends AppCompatActivity {

    Toolbar toolbar;
    ImageView send_img;
    EditText message;
    Drawable drawable;
    Context context;
    String token, companyname, emailname;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        toolbar = findViewById(R.id.toolbar);
        send_img = findViewById(R.id.send_img);
        message = findViewById(R.id.type_message);
        setSupportActionBar(toolbar);

        SharedPreferences sp = getSharedPreferences("prefFile", Context.MODE_PRIVATE);

              companyname = sp.getString("companyName","Default");

                emailname = sp.getString("emailName","Default");

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


        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",emailname).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {

                                Log.d("docs", document.getData()+"");


                        }

                    }
                }
        );

    }
}
