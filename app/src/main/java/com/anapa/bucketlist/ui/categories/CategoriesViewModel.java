package com.anapa.bucketlist.ui.categories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoriesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CategoriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is categories fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}