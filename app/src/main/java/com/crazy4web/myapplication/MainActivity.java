package com.crazy4web.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.crazy4web.myapplication.ui.SearchResults.Searchoperation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    String name, fbImage;
    final String prefFile = "com.crazy4web.myapplication.userdata";
    private static final String TAG = "MainActivity";
    ImageView speechtotext;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        SharedPreferences sharedPreferences = getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        //firebase messaging token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task ->{

            if (!task.isSuccessful()){
                Log.d("error","instance id not found");
            }

            String token = task.getResult().getToken();
            Log.d("token", token);

        });

        speechtotext = findViewById(R.id.sppechtotext);

        speechtotext.setVisibility(View.INVISIBLE);

        searchView = findViewById(R.id.searchView);


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechtotext.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                speechtotext.setVisibility(View.INVISIBLE);

                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
        //the search submit is handled here.
            public boolean onQueryTextSubmit(String s) {

                Intent intent = new Intent(getApplicationContext(), Searchoperation.class);
                intent.putExtra("searchvalue", s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });





        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
//                .requestServerAuthCode(getString(R.string.server_client_id))
//                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_Chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        // I commented it to avoid nullpoint exception as I was changing the theme to Noactionbar
        // please uncomment if you would like to use actionbar

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
//            Log.d(TAG, "on success: "+personName.toUpperCase().charAt(0)+personName.substring(1,personName.length()));
//            finish();
            String googleName = personName.toUpperCase().charAt(0)+personName.substring(1,personName.length());
//            Log.d(TAG, "gogole name:: "+googleName);
            sharedPreferences.edit().putString("googleName", googleName).apply();
            sharedPreferences.edit().putString("googlePic", String.valueOf(personPhoto)).apply();

        }


        Intent intent = getIntent();
        if(intent != null){

            name = intent.getStringExtra("name");
            fbImage = intent.getStringExtra("fbImage");
            Log.d(TAG, "onCreate: fbname"+name);
            Log.d(TAG, "onCreate: "+fbImage);
            sharedPreferences.edit().putString("fbName", name).apply();
            sharedPreferences.edit().putString("fbImage", fbImage).apply();

        }

    }

    public void SpeachToText(View view) {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());



        if(intent.resolveActivity(getPackageManager())!=null){

            startActivityForResult(intent, 10);


        }

        else {

            Toast.makeText(getApplicationContext(),"Your Device deosn't Support Speech Recognition", Toast.LENGTH_LONG).show();

        }



    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        switch (requestCode) {

            case 10:
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

//                    Log.d("Result", result.get(0));
                    searchView.setQuery(result.get(0), true);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {



                            Intent intent = new Intent(getApplicationContext(), Searchoperation.class);
                            intent.putExtra("searchvalue", s);
                            startActivity(intent);
                            return false;

                        }

                        @Override
                        public boolean onQueryTextChange(String s) {

                            return false;
                        }
                    });



                }

                break;
        }
        }

    }
