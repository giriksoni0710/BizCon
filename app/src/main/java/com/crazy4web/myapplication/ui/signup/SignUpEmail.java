package com.crazy4web.myapplication.ui.signup;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crazy4web.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpEmail extends AppCompatActivity {

    private static final String TAG = "SignUpEmail";
    String fname, lname, uemail, upassword, uphone;
    TextInputEditText firstName, lastName, email, password, phone;
    Button btn;
    Map signup_obj;
    FirebaseDatabase database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();



        btn = findViewById(R.id.email_signup);

        firstName = findViewById(R.id.fname);
        lastName = findViewById(R.id.lname);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pass);
        phone = findViewById(R.id.user_phone);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = firstName.getText().toString();
                lname = lastName.getText().toString();
                uemail = email.getText().toString();

                upassword = password.getText().toString();
                uphone = phone.getText().toString();

                signup_obj = new HashMap();

                signup_obj.put("fname", fname);
                signup_obj.put("lname", lname);
                signup_obj.put("email", uemail);
                signup_obj.put("password", upassword);
                signup_obj.put("phone", uphone);

                DatabaseReference myRef = database.getReference("user").child("user_details");
                myRef.setValue(signup_obj);

                Log.d("details",""+fname+lname+uemail+upassword+uphone);
            }
        });


//        lastName = findViewById(R.id.)
//
//        email = findViewById(R.id.)
//
//        phone = findViewById(R.id.)



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("name", "name is :"+name);

                finish();
            }
        });



    }

}
