package com.anapa.bucketlist.ui.inspiration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InspirationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InspirationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is inspiration fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}