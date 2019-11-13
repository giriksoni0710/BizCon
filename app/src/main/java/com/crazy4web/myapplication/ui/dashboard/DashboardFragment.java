package com.crazy4web.myapplication.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
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
    TextView textView12;
    private static final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
                View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listViewDashboard = root.findViewById(R.id.listViewDashboard);
//        recyclerView = root.findViewById(R.id.dashboardRecyclerItems);

        textView12 = root.findViewById(R.id.textView12);

        sp = getActivity().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
//                .requestServerAuthCode(getString(R.string.server_client_id))
//                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(root.getContext(), gso);

        String facebookName = sp.getString("fbName","Default");
        String googleName = sp.getString("googleName","Default");
        String emailName = sp.getString("emailName", "Default");
        Log.d(TAG, "name: "+emailName);
        Log.d(TAG, "fb name: "+facebookName);
        Log.d(TAG, "google name: "+googleName);

        if(facebookName != "Default"){
            textView12.setText(facebookName);
        }else if(emailName != "Default"){
            textView12.setText(emailName);
            sp.edit().putString("fbName","Default").apply();
            sp.edit().putString("googleName","Default").apply();
        }else if(googleName != "Default"){
            textView12.setText(googleName);
            sp.edit().putString("emailName","Default").apply();
            sp.edit().putString("fbName","Default").apply();
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


        ArrayAdapter listAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, menuItems);
        listViewDashboard.setAdapter(listAdapter);
        listViewDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "tapped"+i);
//                Toast.makeText(getContext(), "clicked item: "+i, Toast.LENGTH_LONG).show();
//                if(i == 2){
//
//                }
                switch (i) {

                    case 2:
                        intent = new Intent(getContext(), dashboardNotificationOptions.class);
                        startActivity(intent);
                        break;

                    case 6:

                        if(googleName != "Default" && facebookName == "Default" && emailName == "Default"){
                            signOut();
                            revokeAccess();
                            Log.d(TAG, "Logged out from google");
                            Toast.makeText(getContext(),"User is logged out",Toast.LENGTH_LONG).show();

                        }else if(googleName == "Default" && facebookName != "Default" && emailName == "Default"){
                            LoginManager.getInstance().logOut();
                            Log.d(TAG, "Logged out from facebook");

                        }else if(googleName == "Default" && facebookName == "Default" && emailName != "Default"){
                            Log.d(TAG, "logged out from email");
                            Toast.makeText(getContext(),"User is logged out",Toast.LENGTH_LONG).show();
                        }

                        sp.edit().putString("emailName","Default").apply();
                        sp.edit().putString("googleName","Default").apply();
                        sp.edit().putString("fbName","Default").apply();
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