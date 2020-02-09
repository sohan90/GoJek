package com.gojek.application.model;

import com.gojek.application.retrofit.Api;

import java.util.List;

import io.reactivex.Single;

public class Repository {

    private final Api api;

    public Repository(Api api) {
        this.api = api;
    }

    public Single<List<TrendingResponse>> getTrendingGitHub() {
        return api.getTrendingGitHub();
    }
}
