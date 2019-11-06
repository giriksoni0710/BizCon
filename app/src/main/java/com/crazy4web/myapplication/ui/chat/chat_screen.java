package com.crazy4web.myapplication.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.crazy4web.myapplication.R;

public class chat_screen extends AppCompatActivity {

    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        message = findViewById(R.id.type_message);




    }
}
