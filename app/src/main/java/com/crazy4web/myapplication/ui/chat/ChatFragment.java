package com.crazy4web.myapplication.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.dashboard.DashboardViewModel;
import com.crazy4web.myapplication.ui.notifications.RecyclerAdapter;

public class ChatFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager layoutManager;



    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_chat_fragment, container, false);

        recyclerView = root.findViewById(R.id.recyclerview_chat);

        layoutManager = new LinearLayoutManager(getContext());

        mAdaptor = new MyAdaptor(getContext());
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdaptor);



        return root;
    }


}
