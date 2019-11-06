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

import com.crazy4web.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class Business_page1 extends AppCompatActivity {

    TextInputLayout category, company_name, website_url;
    Button first_button;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver mfirstpageReceiver;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page1);

        category = findViewById(R.id.category);
        company_name = findViewById(R.id.company_name);
        website_url = findViewById(R.id.url);


        first_button = findViewById(R.id.first_btn);


        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("values",category.getEditText().getText().toString()+
//                        ""+company_name.getEditText().getText().toString()+""+website_url.getEditText().getText().toString());

                Intent i = new Intent(getApplicationContext(),Business_page2.class);
                i.setAction("message");


                i.putExtra("category",category.getEditText().getText().toString());
                i.putExtra("company_name",company_name.getEditText().getText().toString());
                i.putExtra("url",website_url.getEditText().getText().toString());



                startActivity(i);


            }
        });



    }
}
