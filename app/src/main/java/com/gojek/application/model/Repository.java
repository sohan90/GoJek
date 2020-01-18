package com.gojek.application.model;

import com.gojek.application.retrofit.Api;

public class Repository {

    private final Api api;

    public Repository(Api api){
        this.api = api;
    }
}
