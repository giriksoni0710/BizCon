package com.crazy4web.myapplication.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.dashboard.DashboardViewModel;

public class ChatFragment extends Fragment {


    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.activity_chat_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_chat);
        chatViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}
