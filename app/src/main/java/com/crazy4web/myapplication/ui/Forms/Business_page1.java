package com.crazy4web.myapplication.ui.Forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Business_page1 extends AppCompatActivity {

    TextInputLayout category, company_name, website_url;
    Button first_button;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver mfirstpageReceiver;

    FirebaseFirestore database;
    HashMap business1 = new HashMap();
    final String prefFile = "com.crazy4web.myapplication.userdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page1);

       final String pref_file = "com.crazy4web.myapplication.userdata";

        category = findViewById(R.id.category);
        company_name = findViewById(R.id.company_name);
        website_url = findViewById(R.id.url);

        database = FirebaseFirestore.getInstance();
        SharedPreferences sp2 = getSharedPreferences("prefFile", Context.MODE_PRIVATE);
        String googleEmailId = sp2.getString("googleEmailId","");
        String fbEmailId= sp2.getString("fbEmailId","");
        String emailId = sp2.getString("emailId", "");

        first_button = findViewById(R.id.first_btn);


        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("values", category.getEditText().getText().toString() +
                        "" + company_name.getEditText().getText().toString() + "" + website_url.getEditText().getText().toString());


                SharedPreferences sp = getSharedPreferences(pref_file ,Context.MODE_PRIVATE);
                sp.edit().putString("category",category.getEditText().getText().toString()).commit();
                sp.edit().putString("company_name",company_name.getEditText().getText().toString()).commit();
                sp.edit().putString("website_url",website_url.getEditText().getText().toString()).commit();


                Intent i = new Intent(getApplicationContext(), Business_page2.class);
                i.setAction("message");
                i.putExtra("googleEmailId",googleEmailId);
                i.putExtra("fbEmailId",fbEmailId);
                i.putExtra("emailId",emailId);
                startActivity(i);


            }
        });


    }

}