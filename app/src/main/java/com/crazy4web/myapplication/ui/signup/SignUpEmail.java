package com.crazy4web.myapplication.ui.signup;

import android.content.Intent;
import android.os.Bundle;

import com.crazy4web.myapplication.ui.Login.Email_login;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crazy4web.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpEmail extends AppCompatActivity {

    private static final String TAG = "SignUpEmail";
    String fname, lname, uemail, upassword, uphone, confirmpassword;
    TextInputEditText firstName, lastName, email,cpassword, password, phone;
    Button btn;
    Map signup_obj;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseFirestore.getInstance();

        btn = findViewById(R.id.email_signup);

        firstName = findViewById(R.id.fname);
        lastName = findViewById(R.id.lname);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pass);
        phone = findViewById(R.id.user_phone);
        cpassword = findViewById(R.id.cpassword);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    fname = firstName.getText().toString();
                    lname = lastName.getText().toString();
                    uemail = email.getText().toString();
                    upassword = password.getText().toString();
                    uphone = phone.getText().toString();
                    confirmpassword = cpassword.getText().toString();

                    signup_obj = new HashMap();

                    signup_obj.put("fname", fname);
                    signup_obj.put("lname", lname);
                    signup_obj.put("email", uemail);
                    signup_obj.put("password", upassword);
                    signup_obj.put("phone", uphone);


            if(fname!=null && lname!=null && verifyemail() && confirmpassword.length()==upassword.length() && uphone.length()==10) {
                database.collection("users").whereEqualTo("email", uemail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {


                            Log.d("succesfully validated","done");
                            database.collection("users")
                                    .add(signup_obj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                            Intent i = new Intent(getApplicationContext(), Email_login.class);
                                            Toast.makeText(getApplicationContext(), "please login to continue", Toast.LENGTH_LONG).show();
                                            startActivity(i);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });


                        } else {

                            Toast.makeText(getApplicationContext(), "email already exists", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else {

                Log.d("len", uphone.length()+"");
                if(upassword.length()!=confirmpassword.length()) {
                    Toast.makeText(getApplicationContext(), "passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else if(uphone.length()!=10){

                    Toast.makeText(getApplicationContext(), "please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Please fill out all the fields", Toast.LENGTH_SHORT).show();


                }
            }




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


    public boolean verifyemail() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(!uemail.toString().trim().matches(emailPattern)){

            Toast.makeText(getApplicationContext(),"Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }






}
