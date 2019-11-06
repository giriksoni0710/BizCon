package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.crazy4web.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

public class chat_screen extends AppCompatActivity {

    Toolbar toolbar;
    ImageView send_img;
    Drawable drawable;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        toolbar = findViewById(R.id.toolbar);
        send_img = findViewById(R.id.send_img);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        try {
//
//            InputStream stream = context.getAssets().open("img/send.png");
//
//            drawable = Drawable.createFromStream(stream, null);
//
//            send_img.setImageDrawable(drawable);
//
//        } catch (IOException e) {
//
//            Log.d("exp", e+"");
//            e.printStackTrace();
//        }







    }
}
