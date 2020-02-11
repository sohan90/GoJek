package com.gojek.application.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gojek.application.base.BaseViewModel;
import com.gojek.application.model.Repository;
import com.gojek.application.model.TrendingResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {

    private final Repository repository;

    private MutableLiveData<List<TrendingResponse>> listMutableLiveData;

    private MutableLiveData<String> errorStateLiveData;


    public HomeViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<TrendingResponse>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
            //getTrendingGitHub();
        }
        return listMutableLiveData;
    }


    public LiveData<String> getErrorStateLiveData() {
        if (errorStateLiveData == null) {
            errorStateLiveData = new MutableLiveData<>();
        }
        return errorStateLiveData;
    }

    public void getTrendingGitHub() {
        addSubscribe(repository.getTrendingGitHub()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingResponses -> {
                            if (trendingResponses != null && trendingResponses.size() > 0) {
                                listMutableLiveData.setValue(trendingResponses);
                            } else {
                                errorStateLiveData.setValue(null);
                            }
                        }, e -> {
                            e.printStackTrace();
                            errorStateLiveData.setValue(e.getMessage());
                        }
                )
        );

    }

}
