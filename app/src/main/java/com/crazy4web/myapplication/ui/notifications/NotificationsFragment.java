package com.crazy4web.myapplication.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationsFragment extends Fragment {

    SharedPreferences sp;
    private static final String TAG = "NotificationsFragment";
    private NotificationsViewModel notificationsViewModel;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Set<String> likedIds;
    List<String> likedId;
    Context mcon;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sp = getActivity().getSharedPreferences("prefFile", Context.MODE_PRIVATE);
        likedIds = sp.getStringSet("hashSet", new HashSet<>());
//        Log.d(TAG, "hash_set values:-> "+likedIds);
        likedId = new ArrayList<>(likedIds);

        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.rv);

        recyclerAdapter = new RecyclerAdapter(mcon, likedId);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
//        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        return root;

    }
}