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
import com.google.firebase.iid.FirebaseInstanceId;
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
    String token;
    SharedPreferences sp;

    final String pref_file = "com.crazy4web.myapplication.userdata";


    FirebaseFirestore database;
    HashMap business1 = new HashMap();


    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page3);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task ->{

            if (!task.isSuccessful()){
                Log.d("error","instance id not found");
            }

            token = task.getResult().getToken();
            Log.d("token1", token);

        });


        // This is the shared preference file where user form data is progressively saved to be sent
        // to the server later


        // getting the instance of the storage
        mStorageRef = FirebaseStorage.getInstance().getReference();

        button = findViewById(R.id.third_button);
        v_url = findViewById(R.id.image_url);

        // the upload image button
        choose_img = findViewById(R.id.choose_img);

        // the image being shown next to the button when it is selected by the user
        embedded_image = findViewById(R.id.imageView5);

        // here we are having a reference to store images in the images folder of the firebase storage
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        // get the database instance
        database = FirebaseFirestore.getInstance();

        // saving all the user input data into preference file recieved from the previous pages
       sp = getSharedPreferences(pref_file ,Context.MODE_PRIVATE);

        String tagline = sp.getString("tagline", "Default");
        hashMap.put("tagline",tagline.toLowerCase());


        String business = sp.getString("business_desc", "Default");
        hashMap.put("business_desc",business.toLowerCase());


        String services = sp.getString("services", "Default");
        hashMap.put("services",services.toLowerCase());


        String category = sp.getString("category", "Default");
        hashMap.put("category",category.toLowerCase());


        company_name = sp.getString("company_name", "Default");
        hashMap.put("company_name",company_name.toLowerCase());


        String website_url = sp.getString("website_url", "Default");
        hashMap.put("website_url",website_url.toLowerCase());







        choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling openFilechooser to let the user select the image
                openFileChooser();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploader();

                String video_url= v_url.getEditText().getText().toString();
                hashMap.put("video_url",video_url.toLowerCase());

                database.collection("business")
                        .add(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("success", "DocumentSnapshot added with ID: " + documentReference.getId());
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
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

    //this is where we will upload the image to firebase database
    private void uploader() {

        // as each company name will be unique, we will be storing the image name as the company name
        StorageReference reference = mStorageRef.child(company_name.toLowerCase().trim()+"."+getExtention(uri));


        // I am also inserting the image_path for easier retrieval
        // this is where firebase stores the image in the

        String image_path= "gs://bizcon-17781.appspot.com/Images/"+company_name.toLowerCase().trim()+"."+getExtention(uri);
        hashMap.put("image_path",image_path);

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

    // opening the file chooser
    private void openFileChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(i, 1);
    }

    // here we get the data and embedd the image once the file has been successfully choosen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

        uri = data.getData();
        embedded_image.setImageURI(uri);

        }
    }
}



