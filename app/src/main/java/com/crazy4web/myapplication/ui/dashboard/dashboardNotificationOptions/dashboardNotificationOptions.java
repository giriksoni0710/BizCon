package com.crazy4web.myapplication.ui.dashboard.dashboardNotificationOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crazy4web.myapplication.R;

import java.util.List;

public class dashboardNotificationOptions extends AppCompatActivity {

    ListView listViewDashboard;
    List<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_notification_options);
        Toolbar toolbar = findViewById(R.id.toolbarNotificationOptions);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
//        View root = layoutInflater.inflate(R.layout.fragment_dashboard,  , false);

//        listViewDashboard = findViewById(R.id.listViewDashboard);
//        listItems.add("Inbox Messages");
//        listItems.add("Who viewed your business");
//        listItems.add("Shares");
//
//
//        ArrayAdapter listAdapter = new ArrayAdapter(getApplicationContext() ,android.R.layout.simple_list_item_1, listItems);
//        listViewDashboard.setAdapter(listAdapter);
//
//        listViewDashboard.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Log.d(TAG, "tapped"+i);
//                Toast.makeText(getApplicationContext(), "clicked item: "+i, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
