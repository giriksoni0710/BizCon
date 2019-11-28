package com.crazy4web.myapplication.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.oauth.Oauth_webLogin;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignUpOptions extends AppCompatActivity {

    int RC_SIGN_IN = 0;

    Button email_signup;
    LoginButton fb_signup;
    CallbackManager callbackManager;

    Button google_signup;
    GoogleSignInClient mGoogleSignInClient;
    private String url = "https://operator.ankurkaul.com/bizcon/authorize?token=";
//    private String url = "https://operator.ankurkaul.com/bizcon/test?token=";
    Boolean reply;
    ProgressBar progressBar;
    ImageView bizcon_logo;
    EditText input;

    private static final String TAG = "SignUpOptions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_options);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent2);
        }


//        bizcon_logo = findViewById(R.id.bizcon_logo);
//        bizcon_logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setMessage("How many Cards?");
//
//                input = new EditText(getApplicationContext());
//                builder.setView(input);
//
//
////          Set positive button
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //capture the input here
//                        String inp = input.getText().toString();
//                        Toast.makeText(getApplicationContext(),"input is: "+inp, Toast.LENGTH_LONG).show();
//
////                        webView.evaluateJavascript("addNode("+inp+")",value->{
////                            Log.d("WEBVIEW","returned from webview is -> "+value);
////                        });
//                    }
//                });
//
////          Set negative button
//                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
////          Create the actual dialog box
//                AlertDialog box = builder.create();
//
//
////          Alert dialog endss
//                box.show();
//            }
//        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbarSignupOptions);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        callbackManager = CallbackManager.Factory.create();
        fb_signup = findViewById(R.id.login_button);

        fb_signup.setPermissions(Arrays.asList("email","public_profile"));


        fb_signup.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "onSuccess: Successfull result"+ loginResult);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

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

        google_signup = findViewById(R.id.sign_in_button);
        google_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.sign_in_button:
                        progressBar.setVisibility(View.VISIBLE);
                        signIn();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

//        silent google sign in
        mGoogleSignInClient.silentSignIn()
            .addOnCompleteListener(
            this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        handleSignInResult(task);
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
//            Log.d(TAG, idToken);

            authorizeBizcon(idToken);

//            Intent i = new Intent(this,MainActivity.class);
//            startActivity(i);
        } catch (ApiException e) {
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){
                 //means user is logged out
                Toast.makeText(getApplicationContext(),"user is logged out",Toast.LENGTH_LONG).show();
            }else{
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    String email = object.getString("email");

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("name",firstName+" "+lastName);
                    i.putExtra("fbImage",image_url);
                    i.putExtra("fbEmailId", email);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        progressBar.setVisibility(View.GONE);

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void authorizeBizcon(String id_token){

//        JSONObject postparams = new JSONObject();
//        try {
//            postparams.put("token", id_token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url+id_token,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            Log.d(TAG,response.get("message").toString());
                            if(response.get("message").toString() == "true"){
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(),"Cannot authorize google user",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                        Log.d(TAG, error.toString());
                        Log.d(TAG, error.toString());
//                        reply = false;
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }

//    protected void setGoogleButtonText(SignInButton signInButton, String buttonText) {
//        // Find the TextView that is inside of the SignInButton and set its text
//        for (int i = 0; i < signInButton.getChildCount(); i++) {
//            View v = signInButton.getChildAt(i);
//
//            if (v instanceof TextView) {
//                TextView tv = (TextView) v;
//                tv.setText(buttonText);
//                return;
//            }
//        }
//    }


}
