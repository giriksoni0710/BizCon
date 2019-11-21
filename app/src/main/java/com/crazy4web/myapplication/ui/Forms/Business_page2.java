package com.crazy4web.myapplication.ui.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crazy4web.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.zip.Deflater;

public class Business_page2 extends AppCompatActivity {

    Button btn;
    TextInputEditText tagline, description, services;

    String website_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page2);

        final String pref_file = "com.crazy4web.myapplication.userdata";

        tagline = findViewById(R.id.tagline);
        description = findViewById(R.id.biz_desc);

        services = findViewById(R.id.services);

        btn = findViewById(R.id.second_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sp = getSharedPreferences(pref_file ,Context.MODE_PRIVATE);
                sp.edit().putString("tagline",tagline.getText().toString()).commit();
                sp.edit().putString("business_desc",description.getText().toString()).commit();
                sp.edit().putString("services",services.getText().toString()).commit();

                Intent intent = getIntent();
                String googleEmailId = intent.getStringExtra("googleEmailId");
                String fbEmailId = intent.getStringExtra("fbEmailId");
                String emailId = intent.getStringExtra("emailId");



                Intent i = new Intent(getApplicationContext(), Business_page3.class);
                i.putExtra("googleEmailId", googleEmailId);
                i.putExtra("fbEmailId", fbEmailId);
                i.putExtra("emailId", emailId);
                startActivity(i);


            }
        });


    }

}
