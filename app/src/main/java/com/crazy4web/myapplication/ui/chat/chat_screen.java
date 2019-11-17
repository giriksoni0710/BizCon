package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.crazy4web.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.io.InputStream;

public class chat_screen extends AppCompatActivity {

    Toolbar toolbar;
    ImageView send_img;
    EditText message;
    Drawable drawable;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        toolbar = findViewById(R.id.toolbar);
        send_img = findViewById(R.id.send_img);
        message = findViewById(R.id.type_message);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        send_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                firebaseFirestore.collection("messages").add(new ChatMessage(message.getText().toString()
                        ,""));



            }
        });


    }
}
