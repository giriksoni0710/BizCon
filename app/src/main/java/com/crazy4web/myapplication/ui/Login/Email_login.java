package com.crazy4web.myapplication.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.signup.SignUpOptions;

public class Email_login extends AppCompatActivity {

    TextView general_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        general_signup = findViewById(R.id.general_signup);
        general_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpOptions.class);
//                i.putExtra("signUp","singup");
                startActivity(i);
            }
        });

    }
}
