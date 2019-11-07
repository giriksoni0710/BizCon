package com.crazy4web.myapplication.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crazy4web.myapplication.R;

public class SignUpOptions extends AppCompatActivity {

    Button email_signup;
    Button fb_signup;
    Button google_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_options);
        Toolbar toolbar = findViewById(R.id.toolbarSignupOptions);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fb_signup = findViewById(R.id.fb_signup);
        google_signup = findViewById(R.id.google_signup);

        fb_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Oauth_webLogin.class);
                view.getContext().startActivity(intent);
            }
        });

        google_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Oauth_webLogin.class);
                view.getContext().startActivity(intent);
            }
        });


        email_signup = findViewById(R.id.email_signup);
        email_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpEmail.class);
                startActivity(i);
            }
        });
    }
}
