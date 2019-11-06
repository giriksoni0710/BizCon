package com.crazy4web.myapplication.ui.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;

public class Business_page3 extends AppCompatActivity {

    String website_url, category, company_name;
    Button button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page3);


        button = findViewById(R.id.third_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(business_form1, new IntentFilter("message"));
    }

    public BroadcastReceiver business_form1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            website_url = intent.getStringExtra("url").toString();

            Log.d("url",""+website_url);

        }
    };


    }



