package com.crazy4web.myapplication.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {


    private MutableLiveData<String> mText;


    public ChatViewModel() {


        mText = new MutableLiveData();

        mText.setValue("This is the Chat Fragment");

    }


    public LiveData<String> getText() {
        return mText;
    }
}
