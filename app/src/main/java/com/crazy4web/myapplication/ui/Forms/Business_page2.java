package com.crazy4web.myapplication.ui.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.crazy4web.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.zip.Deflater;

public class Business_page2 extends AppCompatActivity {

    Button btn;
    TextInputEditText tagline, description, services;
    TextView addService;
    private Spinner spinner;
    private static final String[] paths = {"Choose ", "Website development", "UI/UX Design", "Graphic design", "Painting", "Electrical"};
    private static final String TAG = "Business_page2";

    String website_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page2);

        final String pref_file = "com.crazy4web.myapplication.userdata";
        addService = findViewById(R.id.addService);

        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
//                         Whatever you want to happen when the first item gets selected
                        Log.d(TAG, "abcd");
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
            }
        });

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
