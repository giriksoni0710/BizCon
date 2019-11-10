package com.crazy4web.myapplication.ui.Forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

public class Business_page3 extends AppCompatActivity {

    String website_url, category, company_name;
    Button button, choose_img;
    HashMap hashMap = new HashMap();
    ImageView embedded_image;
    Intent intent;
    TextInputLayout  disable_textinput, v_url;
    StorageReference mStorageRef;


    FirebaseFirestore database;
    HashMap business1 = new HashMap();

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page3);

        final String pref_file = "com.crazy4web.myapplication.userdata";

        mStorageRef = FirebaseStorage.getInstance().getReference();
        button = findViewById(R.id.third_button);
        v_url = findViewById(R.id.image_url);

        choose_img = findViewById(R.id.choose_img);

        embedded_image = findViewById(R.id.imageView5);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        database = FirebaseFirestore.getInstance();

        SharedPreferences sp = getSharedPreferences(pref_file ,Context.MODE_PRIVATE);

        String tagline = sp.getString("tagline", "Default");
        hashMap.put("tagline",tagline);


        String business = sp.getString("business_desc", "Default");
        hashMap.put("business_desc",business);


        String services = sp.getString("services", "Default");
        hashMap.put("services",services);


        String category = sp.getString("category", "Default");
        hashMap.put("category",category);


        String company_name = sp.getString("company_name", "Default");
        hashMap.put("company_name",company_name);


        String website_url = sp.getString("website_url", "Default");
        hashMap.put("website_url",website_url);





        choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploader();


                String video_url= v_url.getEditText().getText().toString();
                hashMap.put("video_url",video_url);




                database.collection("business")
                        .add(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("success", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("error", "Error adding document", e);
                            }
                        });


//                intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);

            }
        });

    }

    private String getExtention(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploader() {

        StorageReference reference = mStorageRef.child(System.currentTimeMillis()+"."+getExtention(uri));

        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content

                        Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    private void openFileChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

        uri = data.getData();
        embedded_image.setImageURI(uri);

        }
    }
}



