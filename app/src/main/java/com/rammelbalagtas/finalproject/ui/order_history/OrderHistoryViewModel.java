package com.rammelbalagtas.finalproject.ui.order_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}