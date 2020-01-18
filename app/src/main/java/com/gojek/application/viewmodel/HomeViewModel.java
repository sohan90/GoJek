package com.gojek.application.viewmodel;

import androidx.lifecycle.ViewModel;

import com.gojek.application.model.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {
    private final Repository repository;

    private CompositeDisposable disposable;

    public HomeViewModel(Repository repository){
        this.repository = repository;
        disposable = new CompositeDisposable();
    }
}
