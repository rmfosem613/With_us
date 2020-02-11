package com.example.with_us.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class eventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public eventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is event");
    }

    public LiveData<String> getText() {
        return mText;
    }
}