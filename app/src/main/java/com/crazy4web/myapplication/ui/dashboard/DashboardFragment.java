package com.crazy4web.myapplication.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.Forms.Business_page1;
import com.crazy4web.myapplication.ui.Login.Email_login;
import com.crazy4web.myapplication.ui.dashboard.dashboardNotificationOptions.dashboardNotificationOptions;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    GoogleSignInClient mGoogleSignInClient;
    ListView listViewDashboard;
    List<String> menuItems = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Intent intent;
    SharedPreferences sp;
    TextView textView12, dashboardMenuItems;
    ImageView pic;
    private static final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listViewDashboard = root.findViewById(R.id.listViewDashboard);

        textView12 = root.findViewById(R.id.textView12);
        pic = root.findViewById(R.id.pic);

        sp = getActivity().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
//                .requestServerAuthCode(getString(R.string.server_client_id))
//                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(root.getContext(), gso);

        String googleEmailId = sp.getString("googleEmailId","");
        String fbEmailId= sp.getString("fbEmailId","");
        String emailId = sp.getString("emailId", "");

        Log.d(TAG, "fbid: "+fbEmailId);
        Log.d(TAG, "gogoleid: "+googleEmailId);
        Log.d(TAG, "emailid: "+emailId);


        String facebookName = sp.getString("fbName","");
        String googleName = sp.getString("googleName","");
        String emailName = sp.getString("emailName", "");
        Log.d(TAG, "name: "+emailName);
        Log.d(TAG, "fb name: "+facebookName);
        Log.d(TAG, "google name: "+googleName);

        if(facebookName != ""){
            textView12.setText(facebookName);
            Glide.with(getContext()).load(String.valueOf(sp.getString("fbImage","Default"))).into(pic);
        }else if(emailName != ""){
            textView12.setText(emailName);
//            sp.edit().putString("fbName","Default").apply();
//            sp.edit().putString("googleName","Default").apply();
        }else if(googleName != ""){
            textView12.setText(googleName);
            Glide.with(getContext()).load(String.valueOf(sp.getString("googlePic","Default"))).into(pic);
//            sp.edit().putString("emailName","Default").apply();
//            sp.edit().putString("fbName","Default").apply();
        }else{
            textView12.setText("");
        }

        menuItems.add("Account");
        menuItems.add("Favourites");
        menuItems.add("Notifications");
        menuItems.add("Start a business..!!");
        menuItems.add("Support");
        menuItems.add("Terms of Serivice");
        menuItems.add("Logout");

//        recyclerAdapter = new RecyclerAdapter(menuItems);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(recyclerAdapter);
//
//        recyclerView.addItemDecoration(new DividerItemDecoration(git recyclerView.getContext(), DividerItemDecoration.VERTICAL));


        ArrayAdapter listAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, menuItems){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position,convertView,parent);
                dashboardMenuItems = view.findViewById(android.R.id.text1);
                dashboardMenuItems.setTextColor(Color.parseColor("#fe9b18"));
                dashboardMenuItems.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

                return view;
            }
        };
        listViewDashboard.setAdapter(listAdapter);
        listViewDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 2:
                        intent = new Intent(getContext(), dashboardNotificationOptions.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(getContext(), Business_page1.class);
//                        intent.putExtra("googleEmailId",googleEmailId);
                        startActivity(intent);
                        break;

                    case 6:

                        if(googleName != "" && facebookName == "" && emailName == ""){
                            signOut();
                            revokeAccess();
                            Log.d(TAG, "Logged out from google");
                            Toast.makeText(getContext(),"User is logged out",Toast.LENGTH_SHORT).show();

                        }else if(googleName == "" && facebookName != "" && emailName == ""){
                            LoginManager.getInstance().logOut();
                            Log.d(TAG, "Logged out from facebook");
                            Toast.makeText(getContext(),"User is logged out",Toast.LENGTH_SHORT).show();

                        }else if(googleName == "" && facebookName == "" && emailName != ""){
                            Log.d(TAG, "logged out from email");
                            Toast.makeText(getContext(),"User is logged out",Toast.LENGTH_SHORT).show();
                        }

                        sp.edit().putString("emailName","").apply();
                        sp.edit().putString("googleName","").apply();
                        sp.edit().putString("fbName","").apply();
                        sp.edit().putString("googleEmailId","").apply();
                        sp.edit().putString("fbEmailId","").apply();
                        sp.edit().putString("emailId","").apply();
                        sp.edit().putString("emailName","").apply();
                        sp.edit().putString("userhascompany","").apply();
                        sp.edit().putString("googleName","").apply();
                        sp.edit().putString("fbName","").apply();
//                        sp.edit().putString("userhascompany","").apply();
                        Intent in = new Intent(getContext(), Email_login.class);
                        startActivity(in);
                        break;

                }
            }
        });
        return root;
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Completed logout");
            }
        });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Access revoked");
                    }
                });
    }

}