package com.crazy4web.myapplication.ui.dashboard;

import android.content.Intent;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.MainActivity;
import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.Login.Email_login;
import com.crazy4web.myapplication.ui.dashboard.dashboardNotificationOptions.dashboardNotificationOptions;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

//    private static final String TAG = "DashboardFragment";
    ListView listViewDashboard;
    List<String> menuItems = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Intent intent;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
                View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listViewDashboard = root.findViewById(R.id.listViewDashboard);
//        recyclerView = root.findViewById(R.id.dashboardRecyclerItems);

        menuItems.add("Account");
        menuItems.add("Favourites");
        menuItems.add("Notifications");
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
                if(i == 2){
                    intent = new Intent(getContext(), dashboardNotificationOptions.class);
                    startActivity(intent);
                }
            }
        });
        return root;
    }
}